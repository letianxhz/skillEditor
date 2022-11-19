package controller;

import config.*;
import dialog.DialogFactory;
import dialog.LogicDialog;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.ETVColumnValueType;
import model.LogicInfoOfTableView;
import model.SceneInfoOfTreeView;
import model.SkillLogic;
import utils.MyUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
	@FXML
	public TextField findText;
	@FXML
	public Button findButton;


	@FXML
	private TreeView<SceneInfoOfTreeView> sceneTreeView;
	
	@FXML
	private MenuItem menSpread;
	
	@FXML
	private MenuItem menuCombine;
	
	//--------------------------------------------------------
	// logic列表
	@FXML
	private TableView<LogicInfoOfTableView> logicTableView;
	
	@FXML
	private Button btnSetting;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private Button btnDel;
	
	@FXML
	private Button btnCopy;
	
	@FXML
	private Button btnPaste;
	
	@FXML
	private Button btnUp;

	@FXML
	private Button btnDown;
	
	//---------------------------------------------------------
	@FXML
	private Button btnAddTrigger;
	
	@FXML
	private Button btnDelTrigger;
	
	
	private Timeline timeline;
	
	/**
	 * 复制数据列表
	 */
	private List<Integer> copyList = new ArrayList<>();
	
	/**
	 * 逻辑窗口
	 */
	private LogicDialog logicDialog = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnSetting.setOnAction(event -> {
			try {
				reLoadInst();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		btnSave.setDisable(true);
		btnSave.setOnAction(event -> {

			if (ConfManager.existModifyFlag()) {
				ConfManager.saveCsv();
				btnSave.setDisable(true);
			}

			try {
				// 重新载入副本配置
				ConfManager.loadCsv();
				// 刷新副本treeView数据
				this.updateLeftScene();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		// 每0.1秒更新一次保存按钮的状态
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(200), AEADBadTagException -> {
			if (ConfManager.existModifyFlag()) {
				if (btnSave.isDisabled()) {
					btnSave.setDisable(false);
				}
			} else {
				if (!btnSave.isDisabled()) {
					btnSave.setDisable(true);
				}
			}
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
		// 给toolbar添加事件
		addLogicToolBarListener();
		
		// 添加trigger事件
		addTriggerToolBarListener();

		// 场景列表选中事件
		sceneTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			updateLogicTableView();
		});
		
		menSpread.setOnAction(event -> sceneTreeView.getSelectionModel().getSelectedItem().setExpanded(true));
		
		menuCombine.setOnAction(event -> sceneTreeView.getSelectionModel().getSelectedItem().setExpanded(false));
		
		// tableView支持多选
		logicTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// 双击点击
		logicTableView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				int selectedCount = logicTableView.getSelectionModel().getSelectedItems().size();
				if (selectedCount != 1) {
					return ;
				}

				if (null == logicTableView.getSelectionModel().getSelectedItem()) {
					return;
				}

				int logicSn = logicTableView.getSelectionModel().getSelectedItem().getConfLogic().getSn();

				LogicDialog logicDialog = getLogicDialog();
				logicDialog.getController().updateControls(logicSn);

				logicDialog.showAndWait();

				updateLogicTableView();
			}
		});

		// 显示数据
		this.updateLeftScene();
		
		// 更新表头
		this.updateTableViewHeader();
	}
	
	/**
	 * 重新载入副本数据
	 */
	private void reLoadInst() throws IOException {
		String instFilePath = ConfProperties.getKeyValue(ConfProperties.CONF_DIRECTORY_PATH);

		File file = DialogFactory.getInstance().openDirectoryChooser(instFilePath);
		if (null != file && file.exists() && file.isDirectory()) {
			// 保存配置路径
			String confDirectory = file.getAbsolutePath();
			ConfProperties.setAndSaveKeyValue(ConfProperties.CONF_DIRECTORY_PATH, confDirectory);

			System.out.println("conf directory:" + confDirectory);

			// 重新载入副本配置
			ConfManager.loadCsv();

			// 刷新副本treeView数据
			this.updateLeftScene();
		}
	}
	
	private void updateLeftScene() {
		// treeView显示场景列表
		Map<Integer, List<ConfScene>> groupScenes = new HashMap<>();
		for (ConfScene confScene : ConfScene.getDatas().values()) {
			List<ConfScene> confSceneList = groupScenes.get(confScene.getType());
			if (null == confSceneList) {
				List<ConfScene> newList = new ArrayList<>();
				newList.add(confScene);
				groupScenes.put(confScene.getType(), newList);
			} else {
				confSceneList.add(confScene);
			}
		}
		
		TreeItem<SceneInfoOfTreeView> rootTree =
				new TreeItem<>(new SceneInfoOfTreeView("配置列表"));
		rootTree.setExpanded(true);
		rootTree.getChildren().clear();
		
		for (Map.Entry<Integer, List<ConfScene>> entry : groupScenes.entrySet()) {
			String typeName = ConfScene.getSceneType(entry.getKey());
			TreeItem<SceneInfoOfTreeView> subRootTree =
					new TreeItem<>(new SceneInfoOfTreeView(typeName));
			subRootTree.setExpanded(true);
			
			for (ConfScene confScene : entry.getValue()) {
				subRootTree.getChildren().add(
						new TreeItem<>(new SceneInfoOfTreeView(confScene)));
			}
			
			rootTree.getChildren().add(subRootTree);
		}

		sceneTreeView.setRoot(rootTree);
	}
	
	private void updateLogicTableView() {
		logicTableView.getItems().clear();
		
		List<ConfLogic> showConfLogic = new ArrayList<>();
		
		int selectedSceneSn = getSelectedSceneSn();
		if (selectedSceneSn > 0) {
			final int filterSn = selectedSceneSn;
			ConfLogic.getDatas().values().forEach((logicInfo) -> {
				showConfLogic.add(logicInfo);
				if (filterSn == logicInfo.getSn()) {

				}
			});
		}
		
		showConfLogic.sort(Comparator.comparingInt(ConfLogic::getSn));
		
		List<ConfLogic> orderConfLogics = new ArrayList<>();
		
		ConfLogicOrder confLogicOrder = ConfLogicOrder.getOrCreate(selectedSceneSn);
		
		// 按照记录的顺序进行排序
		List<Integer> orders = confLogicOrder.getOrderList();
		for (Integer orderSn : orders) {
			ConfLogic removeConfLogic = null;
			for (int i = 0; i < showConfLogic.size(); ++i) {
				if (showConfLogic.get(i).getSn() == orderSn) {
					removeConfLogic = showConfLogic.remove(i);
					break;
				}
			}
			
			if (null != removeConfLogic) {
				orderConfLogics.add(removeConfLogic);
			}
		}

		orderConfLogics.addAll(showConfLogic);
		
		// 保存顺序
		List<Integer> saveOrders = new ArrayList<>();
		for (ConfLogic orderConfLogic : orderConfLogics) {
			if (null != orderConfLogic) {
				saveOrders.add(orderConfLogic.getSn());
			}
		}
		
		confLogicOrder.setOrderList(saveOrders);
		
		for (ConfLogic confLogic : orderConfLogics) {
			LogicInfoOfTableView stringLogicInfo = new LogicInfoOfTableView(confLogic);
			logicTableView.getItems().add(stringLogicInfo);
		}
	}
	

	private void updateTableViewHeader() {
		// logicTableView
		logicTableView.setManaged(true);
		logicTableView.getColumns().clear();
		
		for (SkillLogic eLogicTVC : SkillLogic.values()) {
			addLogicTVColumn(eLogicTVC);
		}
	}
	
	/**
	 * logicTableView添加列
	 */
	private void addLogicTVColumn(SkillLogic eLogicTVC) {
		if (eLogicTVC.getType() == ETVColumnValueType.String) {
			TableColumn<LogicInfoOfTableView, String> tableColumn = new TableColumn<>(eLogicTVC.getColumnName());
			tableColumn.setCellValueFactory(
					new PropertyValueFactory<>(eLogicTVC.getPropertyName()));
			
			tableColumn.setSortable(false);
			
			// 设置table表头
			logicTableView.getColumns().add(tableColumn);
		} else if (eLogicTVC.getType() == ETVColumnValueType.Integer) {
			TableColumn<LogicInfoOfTableView, Number> tableColumn = new TableColumn<>(eLogicTVC.getColumnName());
			tableColumn.setCellValueFactory(
					new PropertyValueFactory<>(eLogicTVC.getPropertyName()));
			
			tableColumn.setSortable(false);
			
			// 设置table表头
			logicTableView.getColumns().add(tableColumn);
		} else if (eLogicTVC.getType() == ETVColumnValueType.Boolean) {
			TableColumn<LogicInfoOfTableView, Boolean> tableColumn = new TableColumn<>(eLogicTVC.getColumnName());
			tableColumn.setCellValueFactory(
					new PropertyValueFactory<>(eLogicTVC.getPropertyName()));
			
			tableColumn.setSortable(false);
			
			// 设置table表头
			logicTableView.getColumns().add(tableColumn);
		}
	}
	

	/**
	 * 获取Logic窗口
	 * @return
	 */
	private LogicDialog getLogicDialog() {
		if (null == logicDialog) {
			logicDialog = DialogFactory.getInstance().createLogicDialog();
		}
		
		return logicDialog;
	}

	private void addLogicToolBarListener() {
		btnAdd.setOnAction(event -> {
			int selectedSceneSn = getSelectedSceneSn();
			if (selectedSceneSn == 0) {
				MyUtils.alertDialogOk("提示", "未选择归属场景", "请点击左侧场景列表，选择一个场景后再进行添加操作。");
				return ;
			}

			ConfLogic newConfLogic = ConfLogic.create();

			int selectedCount = logicTableView.getSelectionModel().getSelectedItems().size();
			if (selectedCount != 0) {
				int lastSelectedSn = 0;
				for (LogicInfoOfTableView logicInfoOfTableView : logicTableView.getSelectionModel().getSelectedItems()) {
					if (null != logicInfoOfTableView) {
						lastSelectedSn = logicInfoOfTableView.getConfLogic().getSn();
					}
				}

				ConfLogicOrder confLogicOrder = ConfLogicOrder.getOrCreate(selectedSceneSn);
				List<Integer> orders = confLogicOrder.getOrderList();

				for (int i=0; i<orders.size(); ++i) {
					if (orders.get(i).equals(lastSelectedSn)) {
						int appendIndex = i;
						orders.add(++appendIndex, newConfLogic.getSn());
						break;
					}
				}

				confLogicOrder.setOrderList(orders);
			}

			updateLogicTableView();

			// 选中新添加的条目
			for (LogicInfoOfTableView selectedItem : logicTableView.getItems()) {
				if (selectedItem.getConfLogic().getSn() == newConfLogic.getSn()) {
					logicTableView.getSelectionModel().select(selectedItem);
				}
			}
		});
		
		btnDel.setOnAction(event -> {
			int selectedCount = logicTableView.getSelectionModel().getSelectedItems().size();
			if (selectedCount == 0) {
				MyUtils.alertDialogOk("提示", "删除数据", "请选择数据后，再进行删除操作。");
				return ;
			}

			Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
			deleteAlert.setTitle("提示");
			deleteAlert.setHeaderText("删除数据");
			deleteAlert.setContentText("您选中了" + selectedCount + "条数据，确定删除？");
			Optional<ButtonType> result = deleteAlert.showAndWait();
			if (result.get() == ButtonType.OK) {
				// 删除ConfLogic数据
				for (LogicInfoOfTableView logicInfoOfTableView : logicTableView.getSelectionModel().getSelectedItems()) {
					if (null != logicInfoOfTableView) {
						int logicSn = logicInfoOfTableView.getConfLogic().getSn();
						ConfLogic.remove(logicSn);
					}
				}

				updateLogicTableView();
			}
		});
		
		
		btnCopy.setOnAction(event -> {
			int selectedCount = logicTableView.getSelectionModel().getSelectedItems().size();
			if (selectedCount == 0) {
				MyUtils.alertDialogOk("提示", "复制数据", "请选择数据后，再进行复制操作。");
				return ;
			}

			copyList.clear();

			for (LogicInfoOfTableView logicInfoOfTableView : logicTableView.getSelectionModel().getSelectedItems()) {
				if (null != logicInfoOfTableView) {
					int logicSn = logicInfoOfTableView.getConfLogic().getSn();
					copyList.add(logicSn);
				}
			}

			btnPaste.setDisable(false);
		});

		findButton.setOnAction(event -> {
			String selectedStr = findText.getText();
			if (selectedStr.isEmpty()) {
				MyUtils.alertDialogOk("提示", "搜索", "请填写搜素ID。");
				return;
			}
			int sn = ConfLogic.findSnById(selectedStr);
			if (sn <= 0) {
				MyUtils.alertDialogOk("提示", "搜索", "查找的【"+selectedStr+"】 不存在！！");
				return;
			}
			LogicDialog logicDialog = getLogicDialog();
			logicDialog.getController().updateControls(sn);

			logicDialog.showAndWait();

			updateLogicTableView();
		});

		btnPaste.setDisable(true);
		btnPaste.setOnAction(event -> {
			if (copyList.size() == 0) {
				MyUtils.alertDialogOk("提示", "粘贴数据", "没有复制数据，不能进行粘贴。");
				return ;
			}

			int selectedSceneSn = getSelectedSceneSn();
			if (0 == selectedSceneSn) {
				MyUtils.alertDialogOk("提示", "粘贴数据", "未选择场景(左侧场景列表中)，不能进行粘贴。");
				return ;
			}

			List<Integer> pasteLogicSn = new ArrayList<>();
			for (int logicSn : copyList) {
				ConfLogic confLogic = ConfLogic.find(logicSn);
				if (null != confLogic) {
					try {
						ConfLogic cloneConfLogic = confLogic.clone(selectedSceneSn);
						if (null != cloneConfLogic) {
							pasteLogicSn.add(cloneConfLogic.getSn());
						}
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			}

			int selectedCount = logicTableView.getSelectionModel().getSelectedItems().size();
			if (selectedCount != 0) {
				int lastSelectedSn = 0;

				for (LogicInfoOfTableView logicInfoOfTableView : logicTableView.getSelectionModel().getSelectedItems()) {
					if (null != logicInfoOfTableView) {
						lastSelectedSn = logicInfoOfTableView.getConfLogic().getSn();
					}
				}

				ConfLogicOrder confLogicOrder = ConfLogicOrder.getOrCreate(selectedSceneSn);
				List<Integer> orders = confLogicOrder.getOrderList();

				for (int i=0; i<orders.size(); ++i) {
					if (orders.get(i).equals(lastSelectedSn)) {
						int appendIndex = i;
						for (int logicSn : pasteLogicSn) {
							orders.add(++appendIndex, logicSn);
						}
						break;
					}
				}

				confLogicOrder.setOrderList(orders);
			}

			copyList.clear();

			btnPaste.setDisable(true);

			updateLogicTableView();

			// 选中新添加的条目
			for (int logicSn : pasteLogicSn) {
				for (LogicInfoOfTableView selectedItem : logicTableView.getItems()) {
					if (selectedItem.getConfLogic().getSn() == logicSn) {
						logicTableView.getSelectionModel().select(selectedItem);
					}
				}
			}

		});
	}
	
	private void addTriggerToolBarListener() {
	}
	
	private void moveItem(boolean up) {
		int selectedSceneSn = getSelectedSceneSn();
		if (0 == selectedSceneSn) {
			return ;
		}
		
		// 获取选中的条目
		int selectedCount = logicTableView.getSelectionModel().getSelectedItems().size();
		if (selectedCount == 0) {
			return ;
		}
		
		List<Integer> selectedLogicSn = new ArrayList<>();
		for (LogicInfoOfTableView logicInfoOfTableView : logicTableView.getSelectionModel().getSelectedItems()) {
			if (null != logicInfoOfTableView) {
				selectedLogicSn.add(logicInfoOfTableView.getConfLogic().getSn());
			}
		}
		
		if (selectedLogicSn.size() == 0) {
			System.out.println("************错误，这种情况不应该出现");
			return ;
		}
		
		// 获取排序
		ConfLogicOrder confLogicOrder = ConfLogicOrder.getOrCreate(selectedSceneSn);
		List<Integer> orders = confLogicOrder.getOrderList();

		int firstSelectedIndex = 0;
		int lastSelectedIndex = 0;
		for (int i=0; i<orders.size(); ++i) {
			if (orders.get(i).equals(selectedLogicSn.get(0))) {
				// 找到起始位置
				firstSelectedIndex = i;
			}
			
			if (orders.get(i).equals(selectedLogicSn.get(selectedLogicSn.size()-1))) {
				lastSelectedIndex = i;
			}
		}
		
		if (up) {
			if (firstSelectedIndex > 0) {
				int beforeLogicSn = orders.get(firstSelectedIndex - 1);
				orders.add(lastSelectedIndex + 1, beforeLogicSn);
				orders.remove(firstSelectedIndex -1);
			}
		} else {
			if (lastSelectedIndex + 1 < orders.size()) {
				int afterLogicSn = orders.remove(lastSelectedIndex + 1);
				orders.add(firstSelectedIndex, afterLogicSn);
			}
		}
		
		// 保存顺序
		confLogicOrder.setOrderList(orders);
		
		updateLogicTableView();
		
		// 把选中的项目还是选中
		for (int selectedSn : selectedLogicSn) {
			for (LogicInfoOfTableView selectedItem : logicTableView.getItems()) {
				if (selectedItem.getConfLogic().getSn() == selectedSn) {
					logicTableView.getSelectionModel().select(selectedItem);
				}
			}
		}
	}
	
	private int getSelectedSceneSn() {
		int selectedSceneSn = 0;
		// 获取选中的副本对象
		if (null != sceneTreeView.getSelectionModel().getSelectedItem()) {
			if (null != sceneTreeView.getSelectionModel().getSelectedItem().getValue().getConfScene()) {
				selectedSceneSn = sceneTreeView.getSelectionModel().getSelectedItem().getValue().getConfScene().getSn();
			}
		}

		return selectedSceneSn;
	}
}
