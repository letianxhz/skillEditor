package editor;

import config.ConfManager;
import config.ConfProperties;
import config.ConfScene;
import dialog.DialogFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class SkillLogicEditor extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ConfProperties.Load();
        ConfManager.loadCsv();
        ConfScene.load("");
        DialogFactory.getInstance().createMainDialog(stage);
    }

    public static void main(String[] args) {
        Application.launch(SkillLogicEditor.class, args);
    }
}
