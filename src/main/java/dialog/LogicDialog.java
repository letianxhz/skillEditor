package dialog;

import controller.LogicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LogicDialog {
	private final Stage stage;
	
	private final LogicController logicController;
	
	public LogicDialog(Stage mainStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogicDialog.fxml"));
		Parent root = fxmlLoader.load();
		
		logicController = fxmlLoader.getController();
		logicController.initDialog(this);
		
		Stage stage = new Stage();
		stage.initOwner(mainStage);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UTILITY);
		stage.setResizable(false);
		
        stage.setTitle("被动技能编辑");
        stage.setScene(new Scene(root));
        
        this.stage = stage;
	}
	
	public void show() {
		this.stage.show();
	}
	
	public void showAndWait() {
		this.stage.showAndWait();
	}
	
	public void hide() {
		this.stage.hide();
	}
	
	public LogicController getController() {
		return logicController;
	}
}
