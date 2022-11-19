package dialog;

import config.ConfManager;
import controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
public class MainDialog {
	private final Stage stage;
	
	private final MainController mainController;
	
	protected MainDialog(final Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainDialog.fxml"));
		Parent root = fxmlLoader.load();
		
		mainController = fxmlLoader.getController();
        
        stage.setTitle("被动技能编辑器");
        stage.setScene(new Scene(root));
        
        this.stage = stage;
        
        this.addListener(this.stage);
        
        // 创建就显示
        this.show();
	}
	
	public void show() {
		this.stage.show();
	}
	
	public void hide() {
		this.stage.hide();
	}
	
	public MainController getMainController() {
		return mainController;
	}
	
	private void addListener(Stage stage) {
		stage.setOnCloseRequest(event -> {

			if (ConfManager.existModifyFlag()) {
				Alert saveAlert = new Alert(AlertType.WARNING);
				saveAlert.setTitle("警告");
				saveAlert.setHeaderText("是否保存数据？");
				saveAlert.setContentText("点击“是”将保存数据，点击“否”不会保存数据。");

				ButtonType buttonTypeYes = new ButtonType("是");
				ButtonType buttonTypeNO = new ButtonType("否");
				saveAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNO);

				Optional<ButtonType> result = saveAlert.showAndWait();
				if (result.isPresent() && result.get() == buttonTypeYes) {
					ConfManager.saveCsv();
				}
			}

			Alert exitAlert = new Alert(AlertType.WARNING);
			exitAlert.setTitle("警告");
			exitAlert.setHeaderText("是否退出程序？");
			exitAlert.setContentText("点击“是”将立刻关闭应用程序，点击“否”将取消操作。");

			ButtonType buttonTypeYes = new ButtonType("是");
			ButtonType buttonTypeNO = new ButtonType("否");
			exitAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNO);

			Optional<ButtonType> result = exitAlert.showAndWait();
			if (result.isPresent() && result.get() == buttonTypeNO) {
				// 阻止窗口关闭
				event.consume();
			}
		});
		
	}

}
