package controller;

import config.ConfAction;
import config.ConfCondition;
import config.ConfEvent;
import config.ConfLogic;
import dialog.LogicDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.ActionInfoOfComboBox;
import model.CondInfoOfComboBox;
import model.EventInfoOfComboBox;
import utils.MyUtils;

import java.net.URL;
import java.util.*;

public class LogicController implements Initializable {

	@FXML
	public TextField execCount;//执行次数

	@FXML
	public TextField cdTime;
	@FXML
	public TextField desc;
	@FXML
	private TextField snLabel;

	@FXML
	private ComboBox<EventInfoOfComboBox> eventComboBox;

	@FXML
	private ChoiceBox<String> relationChoiceBox;

	@FXML
	private ComboBox<CondInfoOfComboBox> cond1ComboBox;

	@FXML
	private TextArea cond1TextArea;

	@FXML
	private Label cond1Label;

	@FXML
	private ComboBox<CondInfoOfComboBox> cond2ComboBox;

	@FXML
	private TextArea cond2TextArea;

	@FXML
	private Label cond2Label;

	@FXML
	private ComboBox<ActionInfoOfComboBox> action1ComboBox;

	@FXML
	private TextArea action1TextArea;

	@FXML
	private Label action1Label;

	@FXML
	private ComboBox<ActionInfoOfComboBox> action2ComboBox;

	@FXML
	private TextArea action2TextArea;

	@FXML
	private Label action2Label;

	@FXML
	private ComboBox<EventInfoOfComboBox> disEventComboBox;

	@FXML
	private ChoiceBox<String> disRelationChoiceBox;

	@FXML
	private ComboBox<CondInfoOfComboBox> disCond1ComboBox;

	@FXML
	private TextArea disCond1TextArea;

	@FXML
	private Label disCond1Label;

	@FXML
	private ComboBox<CondInfoOfComboBox> disCond2ComboBox;

	@FXML
	private TextArea disCond2TextArea;

	@FXML
	private Label disCond2Label;

	@FXML
	private ComboBox<ActionInfoOfComboBox> disAction1ComboBox;

	@FXML
	private TextArea disAction1TextArea;

	@FXML
	private Label disAction1Label;

	@FXML
	private ComboBox<ActionInfoOfComboBox> disAction2ComboBox;

	@FXML
	private TextArea disAction2TextArea;

	@FXML
	private Label disAction2Label;

	@FXML
	private Button deleteButton;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	private LogicDialog meDialog;

	private int logicSn = 0;

	private ConfLogic showConfLogic = null;

	public void initDialog(LogicDialog logicDialog) {
		this.meDialog = logicDialog;
	}

	static boolean b = false;

	public boolean isNew() {
		return showConfLogic.isNew();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("LogicController......");

		// 关系
		relationChoiceBox.getItems().add(MyUtils.RELATION_EMPTY);
		relationChoiceBox.getItems().add(MyUtils.RELATION_AND);
		relationChoiceBox.getItems().add(MyUtils.RELATION_OR);

		this.initComboBoxStyle();

		List<ConfEvent> events = new ArrayList<>(ConfEvent.getDatas().values());

		events.sort(Comparator.comparingInt(ConfEvent::getIndex));
		// 事件
		events.forEach((confEvent) -> eventComboBox.getItems().add(new EventInfoOfComboBox(confEvent)));
		// 切换事件列表框，自动更新conditon和action的显示
		eventComboBox.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {
					if (null == eventComboBox.getSelectionModel().getSelectedItem()) {
						cond1ComboBox.getItems().clear();
						cond2ComboBox.getItems().clear();
						action1ComboBox.getItems().clear();
						action2ComboBox.getItems().clear();
						return;
					}

					ConfEvent selectedConfEvent = eventComboBox.getSelectionModel().getSelectedItem().getConfEvent();

					String cond1 = "";
					if (null != cond1ComboBox.getSelectionModel().getSelectedItem()) {
						cond1 = cond1ComboBox.getSelectionModel().getSelectedItem().getConfCondition().getId();
					}

					String cond2 = "";
					if (null != cond2ComboBox.getSelectionModel().getSelectedItem()) {
						cond2 = cond2ComboBox.getSelectionModel().getSelectedItem().getConfCondition().getId();
					}

					String action1 = "";
					if (null != action1ComboBox.getSelectionModel().getSelectedItem()) {
						action1 = action1ComboBox.getSelectionModel().getSelectedItem().getConfAction().getId();
					}

					String action2 = "";
					if (null != action2ComboBox.getSelectionModel().getSelectedItem()) {
						action2 = action2ComboBox.getSelectionModel().getSelectedItem().getConfAction().getId();
					}


					cond1ComboBox.getItems().clear();
					cond2ComboBox.getItems().clear();

					action1ComboBox.getItems().clear();
					action2ComboBox.getItems().clear();

					// condition
					// 先按照索引排序
					List<ConfCondition> conditions = new ArrayList<>(ConfCondition.getDatas().values());

					conditions.sort(Comparator.comparingInt(ConfCondition::getIndex));

					conditions.forEach((confCondition) -> {
						CondInfoOfComboBox conditionInfoOfCombBox = new CondInfoOfComboBox(confCondition);
						if (!conditionInfoOfCombBox.getConfCondition().matchEventParam(selectedConfEvent)) {
							conditionInfoOfCombBox.setWarn(true);
						}

						cond1ComboBox.getItems().add(conditionInfoOfCombBox);
						cond2ComboBox.getItems().add(conditionInfoOfCombBox);
					});

					MyUtils.setSelectedCmbCondition(cond1ComboBox, cond1);
					MyUtils.setSelectedCmbCondition(cond2ComboBox, cond2);

					// action
					// 先按照索引排序
					List<ConfAction> actions = new ArrayList<>(ConfAction.getDatas().values());

					actions.sort(Comparator.comparingInt(ConfAction::getIndex));

					// action
					actions.forEach((confAction) -> {
						ActionInfoOfComboBox actionInfoOfComboBox = new ActionInfoOfComboBox(confAction);
						if (actionInfoOfComboBox.getConfAction().matchEventParam(selectedConfEvent)) {
							//actionInfoOfComboBox.setWarn(true);
							action1ComboBox.getItems().add(actionInfoOfComboBox);
							action2ComboBox.getItems().add(actionInfoOfComboBox);
						}
					});

					MyUtils.setSelectedCmbAction(action1ComboBox, action1);
					MyUtils.setSelectedCmbAction(action2ComboBox, action2);
				});

		// 先按照索引排序
		List<ConfCondition> conditions = new ArrayList<>(ConfCondition.getDatas().values());

		conditions.sort(Comparator.comparingInt(ConfCondition::getIndex));

		conditions.forEach((confCondition) -> {
			CondInfoOfComboBox conditionInfoOfCombBox = new CondInfoOfComboBox(confCondition);
			cond1ComboBox.getItems().add(conditionInfoOfCombBox);
			cond2ComboBox.getItems().add(conditionInfoOfCombBox);
		});

		// 先按照索引排序
		List<ConfAction> actions = new ArrayList<>(ConfAction.getDatas().values());

		actions.sort(Comparator.comparingInt(ConfAction::getIndex));
		// action
		actions.forEach((confAction) -> {
			ActionInfoOfComboBox actionInfoOfComboBox = new ActionInfoOfComboBox(confAction);
			action1ComboBox.getItems().add(actionInfoOfComboBox);
			action2ComboBox.getItems().add(actionInfoOfComboBox);
		});

		// 给condtion添加事件
		condComboBoxAddChangeListener(cond1ComboBox, cond1Label);
		condComboBoxAddChangeListener(cond2ComboBox, cond2Label);

		actionComboBoxAddChangeListener(action1ComboBox, action1Label);
		actionComboBoxAddChangeListener(action2ComboBox, action2Label);


		// 失效关系
		disRelationChoiceBox.getItems().add(MyUtils.RELATION_EMPTY);
		disRelationChoiceBox.getItems().add(MyUtils.RELATION_AND);
		disRelationChoiceBox.getItems().add(MyUtils.RELATION_OR);

		// 失效事件
		events.forEach((confEvent) -> {
			disEventComboBox.getItems().add(new EventInfoOfComboBox(confEvent));
		});

		disEventComboBox.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {
					if (null == disEventComboBox.getSelectionModel().getSelectedItem()) {
						return;
					}
					ConfEvent selectedDisConfEvent = disEventComboBox.getSelectionModel().getSelectedItem().getConfEvent();
					String cond1 = "";
					if (null != disCond1ComboBox.getSelectionModel().getSelectedItem()) {
						cond1 = disCond1ComboBox.getSelectionModel().getSelectedItem().getConfCondition().getId();
					}

					String cond2 = "";
					if (null != disCond2ComboBox.getSelectionModel().getSelectedItem()) {
						cond2 = disCond2ComboBox.getSelectionModel().getSelectedItem().getConfCondition().getId();
					}

					String action1 = "";
					if (null != disAction1ComboBox.getSelectionModel().getSelectedItem()) {
						action1 = disAction1ComboBox.getSelectionModel().getSelectedItem().getConfAction().getId();
					}

					String action2 = "";
					if (null != disAction2ComboBox.getSelectionModel().getSelectedItem()) {
						action2 = disAction2ComboBox.getSelectionModel().getSelectedItem().getConfAction().getId();
					}

					disCond1ComboBox.getItems().clear();
					disCond2ComboBox.getItems().clear();

					disAction1ComboBox.getItems().clear();
					disAction2ComboBox.getItems().clear();


					conditions.forEach((confCondition) -> {
						CondInfoOfComboBox conditionInfoOfCombBox = new CondInfoOfComboBox(confCondition);
						if (!conditionInfoOfCombBox.getConfCondition().matchEventParam(selectedDisConfEvent)) {
							conditionInfoOfCombBox.setWarn(true);
						}

						disCond1ComboBox.getItems().add(conditionInfoOfCombBox);
						disCond2ComboBox.getItems().add(conditionInfoOfCombBox);
					});

					MyUtils.setSelectedCmbCondition(disCond1ComboBox, cond1);
					MyUtils.setSelectedCmbCondition(disCond2ComboBox, cond2);

					// action
					actions.forEach((confAction) -> {
						ActionInfoOfComboBox actionInfoOfComboBox = new ActionInfoOfComboBox(confAction);
						if (actionInfoOfComboBox.getConfAction().matchEventParam(selectedDisConfEvent)) {
							//actionInfoOfComboBox.setWarn(true);
							disAction1ComboBox.getItems().add(actionInfoOfComboBox);
							disAction2ComboBox.getItems().add(actionInfoOfComboBox);
						}
					});

					MyUtils.setSelectedCmbAction(disAction1ComboBox, action1);
					MyUtils.setSelectedCmbAction(disAction2ComboBox, action2);
				}
		);

		conditions.forEach((confCondition) -> {
			CondInfoOfComboBox conditionInfoOfCombBox = new CondInfoOfComboBox(confCondition);
			disCond1ComboBox.getItems().add(conditionInfoOfCombBox);
			disCond2ComboBox.getItems().add(conditionInfoOfCombBox);
		});

		// action
		actions.forEach((confAction) -> {
			ActionInfoOfComboBox actionInfoOfComboBox = new ActionInfoOfComboBox(confAction);
			disAction1ComboBox.getItems().add(actionInfoOfComboBox);
			disAction2ComboBox.getItems().add(actionInfoOfComboBox);
		});

		// 给condtion添加事件
		condComboBoxAddChangeListener(disCond1ComboBox, disCond1Label);
		condComboBoxAddChangeListener(disCond2ComboBox, disCond2Label);

		actionComboBoxAddChangeListener(disAction1ComboBox, disAction1Label);
		actionComboBoxAddChangeListener(disAction2ComboBox, disAction2Label);

		// 取消
		cancelButton.setOnAction(event -> meDialog.hide());

		saveButton.setOnAction(event -> {
			// 先保存数据
			if (saveEdit()) {
				meDialog.hide();
			}
		});

		deleteButton.setOnAction(event -> {
			// 先删除数据
			Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
			deleteAlert.setTitle("提示");
			deleteAlert.setHeaderText("删除数据");
			deleteAlert.setContentText("确定删除？");
			Optional<ButtonType> result = deleteAlert.showAndWait();
			if (result.get() == ButtonType.OK) {
				// 删除数据，并关闭当前窗口
				ConfLogic.remove(showConfLogic.getSn());
				meDialog.hide();
			}
		});
	}

	private void condComboBoxAddChangeListener(ComboBox<CondInfoOfComboBox> condComboBox, Label condLabel) {
		condComboBox.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {

					if (null != condComboBox.getSelectionModel().getSelectedItem()) {
						ConfCondition confCondition = condComboBox.getSelectionModel().getSelectedItem().getConfCondition();
						String text = "参数格式：";
						text += confCondition.getParam();
						text += "\n例子：";
						text += confCondition.getExample();

						condLabel.setText(text);
					} else {
						condLabel.setText("");
					}
				});
	}

	private void actionComboBoxAddChangeListener(ComboBox<ActionInfoOfComboBox> actionComboBox, Label actionLabel) {
		actionComboBox.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {

					if (actionComboBox.getSelectionModel().getSelectedItem() != null) {
						ConfAction confAction = actionComboBox.getSelectionModel().getSelectedItem().getConfAction();
						String text = "参数格式：";
						text += confAction.getParam();
						text += "\n例子：";
						text += confAction.getExample();

						actionLabel.setText(text);
					} else {
						actionLabel.setText("");
					}
				});
	}

	public void updateControls(int sn) {
		this.logicSn = sn;

		showConfLogic = ConfLogic.find(this.logicSn);

		// 设置sn
		snLabel.setText(String.valueOf(showConfLogic.getId()));

		//设置描述
		desc.setText(String.valueOf(showConfLogic.getDesc()));

		cdTime.setText(String.valueOf(showConfLogic.getCdTime()));

		execCount.setText(String.valueOf(showConfLogic.getExecCount()));

		// 设置默认事件
		this.setEventComboBoxDefault(showConfLogic.getEventId());

		// 设置关系选择默认值
		this.setRelationChoiceBoxDefault(this.relationChoiceBox, showConfLogic.getRelation());

		// 设置默认conditionType
		MyUtils.setSelectedCmbCondition(cond1ComboBox, showConfLogic.getCond1Type());
		if (showConfLogic.getCond1Param().length() > 0) {
			cond1TextArea.setText(showConfLogic.getCond1Param());
		} else {
			cond1TextArea.setText("");
		}


		MyUtils.setSelectedCmbCondition(cond2ComboBox, showConfLogic.getCond2Type());
		if (null != showConfLogic.getCond2Param() && showConfLogic.getCond2Param().length() > 0) {
			cond2TextArea.setText(showConfLogic.getCond2Param());
		} else {
			cond2TextArea.setText("");
		}

		// 设置默认actionType
		MyUtils.setSelectedCmbAction(action1ComboBox, showConfLogic.getAction1Type());
		if (null != showConfLogic.getAction1Param() && showConfLogic.getAction1Param().length() > 0) {
			action1TextArea.setText(showConfLogic.getAction1Param());
		} else {
			action1TextArea.setText("");
		}

		MyUtils.setSelectedCmbAction(action2ComboBox, showConfLogic.getAction2Type());
		if (null != showConfLogic.getAction2Param() && showConfLogic.getAction2Param().length() > 0) {
			action2TextArea.setText(showConfLogic.getAction2Param());
		} else {
			action2TextArea.setText("");
		}

		this.setEventComboBoxValue(this.disEventComboBox, showConfLogic.getDisableEventId());
		// 设置关系选择默认值
		this.setRelationChoiceBoxDefault(this.disRelationChoiceBox, showConfLogic.getDisRelation());

		// 设置默认conditionType
		MyUtils.setSelectedCmbCondition(disCond1ComboBox, showConfLogic.getDisCond1Type());
		if (showConfLogic.getDisCond1Type().length() > 0 && !Objects.equals(showConfLogic.getDisCond1Type(), "0")) {
			disCond1TextArea.setText(showConfLogic.getDisCond1Param());
		} else {
			disCond1TextArea.setText("");
		}

		// 设置默认conditionType
		MyUtils.setSelectedCmbCondition(disCond2ComboBox, showConfLogic.getDisCond2Type());
		if (showConfLogic.getDisCond2Type().length() > 0 && !Objects.equals(showConfLogic.getDisCond2Type(), "0")) {
			disCond2TextArea.setText(showConfLogic.getDisCond2Param());
		} else {
			disCond2TextArea.setText("");
		}

		// 设置默认actionType
		MyUtils.setSelectedCmbAction(disAction1ComboBox, showConfLogic.getDisAction1Type());
		if (null != showConfLogic.getDisAction1Param() && showConfLogic.getDisAction1Param().length() > 0 && !"0".equals(showConfLogic.getDisAction1Type())) {
			disAction1TextArea.setText(showConfLogic.getDisAction1Param());
		} else {
			disAction1TextArea.setText("");
		}

		// 设置默认actionType
		MyUtils.setSelectedCmbAction(disAction2ComboBox, showConfLogic.getDisAction2Type());
		if (null != showConfLogic.getDisAction2Param() && showConfLogic.getDisAction2Param().length() > 0 && !"0".equals(showConfLogic.getDisAction2Type())) {
			disAction2TextArea.setText(showConfLogic.getDisAction2Param());
		} else {
			disAction2TextArea.setText("");
		}

	}

	private void setRelationChoiceBoxDefault(ChoiceBox<String> cb, int relation) {
		cb.getSelectionModel().select(MyUtils.relationToName(relation));
	}

	private void setEventComboBoxDefault(String eventSn) {
		if (eventSn == null || eventSn.isEmpty()) {
			eventComboBox.getSelectionModel().select(null);
			return;
		}

		for (EventInfoOfComboBox eventInfoOfComboBox : eventComboBox.getItems()) {
			if (Objects.equals(eventInfoOfComboBox.getConfEvent().id, eventSn)) {
				eventComboBox.getSelectionModel().select(eventInfoOfComboBox);
				break;
			}
		}
	}


	private void setEventComboBoxValue(ComboBox<EventInfoOfComboBox> cb, String eventSn) {
		if (null == eventSn || eventSn.isEmpty()) {
			cb.getSelectionModel().select(null);
			return;
		}
		for(EventInfoOfComboBox eob : cb.getItems()) {
			if (Objects.equals(eob.getConfEvent().id, eventSn)) {
				cb.getSelectionModel().select(eob);
				break;
			}
		}
	}

	/**
	 * 保存数据
	 */
	private boolean saveEdit() {
		if (null != eventComboBox.getSelectionModel().getSelectedItem()) {
			String event = eventComboBox.getSelectionModel().getSelectedItem().getConfEvent().getSn();
			showConfLogic.setEventId(event);
		}

		if (showConfLogic.isNew()) {
			String showId = snLabel.getText();
			if (!showId.isEmpty()) {
				showConfLogic.setId(showId);
			}
		}

		if (null != cdTime.getText()) {
			showConfLogic.setCdTime(cdTime.getText());
		}

		if (null != execCount.getText() && !"".equals(execCount.getText())) {
			showConfLogic.setExecCount(execCount.getText());
		}

		if (null != desc.getText() && !"".equals(desc.getText())) {
			showConfLogic.setDesc(desc.getText());
		}

		if (null != relationChoiceBox.getSelectionModel().getSelectedItem()) {
			int relation = MyUtils.relationToValue(relationChoiceBox.getSelectionModel().getSelectedItem());
			showConfLogic.setRelation(relation);
		}

		if (null != cond1ComboBox.getSelectionModel().getSelectedItem()) {
			showConfLogic.setCond1Type(cond1ComboBox.getSelectionModel().getSelectedItem().getConfCondition().getId());
		}

		if (null != cond2ComboBox.getSelectionModel().getSelectedItem()) {
			showConfLogic.setCond2Type(cond2ComboBox.getSelectionModel().getSelectedItem().getConfCondition().getId());
		}

		showConfLogic.setCond1Param(cond1TextArea.getText());
		showConfLogic.setCond2Param(cond2TextArea.getText());

		if (null != action1ComboBox.getSelectionModel().getSelectedItem()) {
			showConfLogic.setAction1Type(action1ComboBox.getSelectionModel().getSelectedItem().getConfAction().getId());
		}

		if (null != action2ComboBox.getSelectionModel().getSelectedItem()) {
			showConfLogic.setAction2Type(action2ComboBox.getSelectionModel().getSelectedItem().getConfAction().getId());
		}

		showConfLogic.setAction1Param(action1TextArea.getText());
		showConfLogic.setAction2Param(action2TextArea.getText());

		if (null != disEventComboBox.getSelectionModel().getSelectedItem()) {
			String diEvent = disEventComboBox.getSelectionModel().getSelectedItem().getConfEvent().getSn();
			showConfLogic.setDisableEventId(diEvent);
		}

		if (null != disRelationChoiceBox.getSelectionModel().getSelectedItem()) {
			int disRelation = MyUtils.relationToValue(disRelationChoiceBox.getSelectionModel().getSelectedItem());
			showConfLogic.setDisRelation(disRelation);
		}

		if (null != disCond1ComboBox.getSelectionModel().getSelectedItem()) {
			showConfLogic.setDisCond1Type(disCond1ComboBox.getSelectionModel().getSelectedItem().getConfCondition().getId());
		}

		if (null != disCond2ComboBox.getSelectionModel().getSelectedItem()) {
			showConfLogic.setDisCond2Type(disCond2ComboBox.getSelectionModel().getSelectedItem().getConfCondition().getId());
		}
		showConfLogic.setDisCond1Param(disCond1TextArea.getText());
		showConfLogic.setDisCond2Param(disCond2TextArea.getText());

		if (null != disAction1ComboBox.getSelectionModel().getSelectedItem()) {
			showConfLogic.setDisAction1Type(disAction1ComboBox.getSelectionModel().getSelectedItem().getConfAction().getId());
		}

		if (null != disAction2ComboBox.getSelectionModel().getSelectedItem()) {
			showConfLogic.setDisAction2Type(disAction2ComboBox.getSelectionModel().getSelectedItem().getConfAction().getId());
		}

		showConfLogic.setDisAction1Param(disAction1TextArea.getText());
		showConfLogic.setDisAction2Param(disAction2TextArea.getText());
		return true;
	}

	/**
	 * 修改Combox的默认绘制，增加提醒红色文字
	 */
	private void initComboBoxStyle() {
		cond1ComboBox.setCellFactory(new Callback<>() {
			@Override
			public ListCell<CondInfoOfComboBox> call(ListView<CondInfoOfComboBox> param) {
				return new ListCell<>() {
					@Override
					protected void updateItem(CondInfoOfComboBox item, boolean empty) {
						super.updateItem(item, empty);

						if (item != null) {
							setText(item.toString());

							if (item.isWarning()) {
								setTextFill(Color.RED);
							} else {
								setTextFill(Color.BLACK);
							}
						} else {
							setText(null);
						}
					}
				};
			}
		});

		cond2ComboBox.setCellFactory(new Callback<>() {
			@Override
			public ListCell<CondInfoOfComboBox> call(ListView<CondInfoOfComboBox> param) {
				return new ListCell<>() {
					@Override
					protected void updateItem(CondInfoOfComboBox item, boolean empty) {
						super.updateItem(item, empty);

						if (item != null) {
							setText(item.toString());

							if (item.isWarning()) {
								setTextFill(Color.RED);
							} else {
								setTextFill(Color.BLACK);
							}
						} else {
							setText(null);
						}
					}
				};
			}
		});

		action1ComboBox.setCellFactory(new Callback<>() {
			@Override
			public ListCell<ActionInfoOfComboBox> call(ListView<ActionInfoOfComboBox> param) {
				return new ListCell<>() {
					@Override
					protected void updateItem(ActionInfoOfComboBox item, boolean empty) {
						super.updateItem(item, empty);

						if (item != null) {
							setText(item.toString());

							if (item.isWarning()) {
								setTextFill(Color.RED);
							} else {
								setTextFill(Color.BLACK);
							}
						} else {
							setText(null);
						}
					}
				};
			}
		});

		action2ComboBox.setCellFactory(new Callback<>() {
			@Override
			public ListCell<ActionInfoOfComboBox> call(ListView<ActionInfoOfComboBox> param) {
				return new ListCell<>() {
					@Override
					protected void updateItem(ActionInfoOfComboBox item, boolean empty) {
						super.updateItem(item, empty);

						if (item != null) {
							setText(item.toString());

							if (item.isWarning()) {
								setTextFill(Color.RED);
							} else {
								setTextFill(Color.BLACK);
							}
						} else {
							setText(null);
						}
					}
				};
			}
		});

		disCond1ComboBox.setCellFactory(new Callback<>() {
			@Override
			public ListCell<CondInfoOfComboBox> call(ListView<CondInfoOfComboBox> condInfoOfComboBoxListView) {
				return new ListCell<>() {
					@Override
					protected void updateItem(CondInfoOfComboBox item, boolean b) {
						super.updateItem(item, b);
						if (item != null) {
							setText(item.toString());
							if (item.isWarning()) {
								setTextFill(Color.RED);
							} else {
								setTextFill(Color.BLACK);
							}
						} else {
							setText(null);
						}
					}
				};
			}
		});


		disCond2ComboBox.setCellFactory(new Callback<>() {
			@Override
			public ListCell<CondInfoOfComboBox> call(ListView<CondInfoOfComboBox> condInfoOfComboBoxListView) {
				return new ListCell<>() {
					@Override
					protected void updateItem(CondInfoOfComboBox item, boolean b) {
						super.updateItem(item, b);
						if (item != null) {
							setText(item.toString());
							if (item.isWarning()) {
								setTextFill(Color.RED);
							} else {
								setTextFill(Color.BLACK);
							}
						} else {
							setText(null);
						}
					}
				};
			}
		});


		disAction1ComboBox.setCellFactory(new Callback<>() {
			@Override
			public ListCell<ActionInfoOfComboBox> call(ListView<ActionInfoOfComboBox> param) {
				return new ListCell<>() {
					@Override
					protected void updateItem(ActionInfoOfComboBox item, boolean empty) {
						super.updateItem(item, empty);

						if (item != null) {
							setText(item.toString());

							if (item.isWarning()) {
								setTextFill(Color.RED);
							} else {
								setTextFill(Color.BLACK);
							}
						} else {
							setText(null);
						}
					}
				};
			}
		});

		disAction2ComboBox.setCellFactory(new Callback<>() {
			@Override
			public ListCell<ActionInfoOfComboBox> call(ListView<ActionInfoOfComboBox> param) {
				return new ListCell<>() {
					@Override
					protected void updateItem(ActionInfoOfComboBox item, boolean empty) {
						super.updateItem(item, empty);

						if (item != null) {
							setText(item.toString());

							if (item.isWarning()) {
								setTextFill(Color.RED);
							} else {
								setTextFill(Color.BLACK);
							}
						} else {
							setText(null);
						}
					}
				};
			}
		});
	}
}
