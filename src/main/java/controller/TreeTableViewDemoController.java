package controller;

import config.ConfManager;
import config.ConfProperties;
import config.ConfScene;
import dialog.DialogFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import model.InstInfoOfTreeView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TreeTableViewDemoController implements Initializable {

	@FXML
	private TreeTableView<InstInfoOfTreeView> instTreeTableView;
	
	@FXML
	private MenuItem menuSetConfDir;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuSetConfDir.setOnAction(event -> {
			try {
				LoadInst();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		instTreeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			InstInfoOfTreeView treeInstInfo = instTreeTableView.getSelectionModel().getSelectedItem().getValue();
			System.out.println(instTreeTableView.getSelectionModel().getSelectedIndex() + ":" + treeInstInfo.getSn() + ":" + treeInstInfo.getName());
		});

		// 设置TreeTableView的Column
		TreeTableColumn<InstInfoOfTreeView, String> column = new TreeTableColumn<>("sn");
		column.setPrefWidth(100);
		column.setCellValueFactory((CellDataFeatures<InstInfoOfTreeView, String> p) -> new ReadOnlyStringWrapper(
				String.valueOf(p.getValue().getValue().getSn())));

		// Creating a column
		TreeTableColumn<InstInfoOfTreeView, String> columnName = new TreeTableColumn<>("name");
		columnName.setPrefWidth(130);

		// Defining cell content
		columnName.setCellValueFactory((CellDataFeatures<InstInfoOfTreeView, String> p) -> new ReadOnlyStringWrapper(
				p.getValue().getValue().getName()));

		instTreeTableView.getColumns().add(column);
		instTreeTableView.getColumns().add(columnName);
		instTreeTableView.setShowRoot(true);

		// 刷新副本treeView数据
		this.UpdateInstTreeView();
	}

	/**
	 * 载入副本数据
	 */
	private void LoadInst() throws IOException {
		String instFilePath = ConfProperties.getKeyValue(ConfProperties.CONF_DIRECTORY_PATH);

		File file = DialogFactory.getInstance().openDirectoryChooser(instFilePath);
		if (null != file && file.exists() && file.isDirectory()) {
			// 保存配置路径
			String confDirectory = file.getAbsolutePath();
			ConfProperties.setAndSaveKeyValue(ConfProperties.CONF_DIRECTORY_PATH, confDirectory);

			// 重新载入副本配置
			ConfManager.loadCsv();

			// 刷新副本treeView数据
			this.UpdateInstTreeView();
		}
	}

	private void UpdateInstTreeView() {
		// 载入副本数据
		TreeItem<InstInfoOfTreeView> root = new TreeItem<InstInfoOfTreeView>(new InstInfoOfTreeView("副本", ""));
		root.setExpanded(true);

		ConfScene.getDatas().values().forEach((instInfo) -> {
			InstInfoOfTreeView treeTableColumn = new InstInfoOfTreeView(String.valueOf(instInfo.getSn()), instInfo.getName());
			root.getChildren().add(new TreeItem<>(treeTableColumn));
		});

		instTreeTableView.setRoot(root);
	}

}

