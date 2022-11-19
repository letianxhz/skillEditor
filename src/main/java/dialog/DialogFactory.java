package dialog;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class DialogFactory {
	private Stage mainStage;
	
	private MainDialog mainDialog = null;
	
	private static final DialogFactory GLOBAL_MANAGER = new DialogFactory();
	
	private DialogFactory() {
		
	}
	
	public static DialogFactory getInstance() {
		return GLOBAL_MANAGER;
	}
	
	/**
	 * 打开文件夹选择器窗口
	 * @param initDirectory
	 */
	public File openDirectoryChooser(String initDirectory) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("选择文件夹");
		
		if (null != initDirectory) {
			File initFile = new File(initDirectory);
			if (initFile.exists() && initFile.isDirectory()) {
				directoryChooser.setInitialDirectory(initFile);
			}
		}

		return directoryChooser.showDialog(this.mainStage);
	}

	public void createMainDialog(Stage stage) throws IOException {
		if (null == mainDialog) {
			this.mainStage = stage;
			mainDialog = new MainDialog(this.mainStage);
		}

	}
	
	/**
	 * 创建LogicDialog窗口
	 */
	public LogicDialog createLogicDialog() {
		LogicDialog logicDialog = null;
		try {
			logicDialog = new LogicDialog(mainStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return logicDialog;
	}
}
