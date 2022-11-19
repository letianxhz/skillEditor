package controller;

import config.ConfProperties;
import config.ConfScene;
import dialog.DialogFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class TreeViewDemoController implements Initializable {

	@FXML
	private TreeView<ConfScene> instTreeView;

	@FXML
	private Button btnLoadInst;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		instTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			ConfScene instInfo = instTreeView.getSelectionModel().getSelectedItem().getValue();
			System.out.println(instInfo.getSn() + ":" + instInfo.getName());

			System.out.println(instTreeView.getSelectionModel().getSelectedIndex());
		});

		btnLoadInst.setOnMouseClicked(event -> LoadInst());

		// 载入副本treeview数据
		this.UpdateInstTreeView();
	}

	/**
	 * 载入副本数据
	 */
	private void LoadInst() {
		String instFilePath = ConfProperties.getKeyValue(ConfProperties.CONF_DIRECTORY_PATH);

		File file = DialogFactory.getInstance().openDirectoryChooser(instFilePath);
		if (null != file && file.exists() && file.isDirectory()) {
			// 保存配置路径
			String confDirectory = file.getAbsolutePath();
			ConfProperties.setAndSaveKeyValue(ConfProperties.CONF_DIRECTORY_PATH, confDirectory);

			System.out.println("conf directory:" + confDirectory);

			// 重新载入副本treeView数据
			this.UpdateInstTreeView();
		}
	}

	private void UpdateInstTreeView() {
		// 载入被动技能相关配表数据
		ConfScene headInstInfo = new ConfScene(0, "配置表", 0);

		TreeItem<ConfScene> rootInstInfo = new TreeItem<ConfScene>(headInstInfo);
		rootInstInfo.setExpanded(true);
		rootInstInfo.getChildren().clear();

		for (ConfScene instInfo : ConfScene.getDatas().values()) {
			rootInstInfo.getChildren().add(new TreeItem<ConfScene>(instInfo));
		}

		instTreeView.setRoot(rootInstInfo);
	}

}
