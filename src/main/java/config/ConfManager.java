package config;

import transition.BattleTriggerConf;
import transition.PassiveSkillConf;
import transition.TransitionManager;
import utils.MyUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class ConfManager {

	/**
	 * 载入csv配置
	 */
	public static void loadCsv() throws IOException {
		String dataDirectoryPath = ConfProperties.getKeyValue(ConfProperties.CONF_DIRECTORY_PATH);
		String eventFileName = dataDirectoryPath + "battle_trigger_event.csv";
		ConfEvent.load(eventFileName);

		String conditionsFileName = dataDirectoryPath + "battle_trigger_condition.csv";
		ConfCondition.load(conditionsFileName);

		String actionFileName = dataDirectoryPath + "battle_trigger_action.csv";
		ConfAction.load(actionFileName);

		String passiveSkillFileName = dataDirectoryPath + "passive_skill_editor.csv";
		ConfLogic.load(passiveSkillFileName);

		//loadOldCsv();
	}


	public static void loadOldCsv() throws IOException {
		String dataDirectoryPath = ConfProperties.getKeyValue(ConfProperties.OLD_CONF_DIR_PATH);
		String passiveSkillFileName = dataDirectoryPath + "passive_skill.csv";
		PassiveSkillConf.load(passiveSkillFileName);

		String btFileName = dataDirectoryPath+ "battle_trigger.csv";
		BattleTriggerConf.load(btFileName);
		TransitionManager.getInstance().transitionData();
	}

	/**
	 * 保存
	 * @return
	 */
	public static boolean saveCsv() {
		return !ConfLogic.save2();
	}

	public static void oneApp() {
		try {
			// 防止程序重复启动
			ServerSocket server = new ServerSocket();
			server.bind(new InetSocketAddress("127.0.0.1", 22222));
		} catch (IOException e) {
			e.printStackTrace();
			MyUtils.alertDialogOk("错误", "重复启动编辑器", "您已经启动过编辑器！");
			System.exit(0);
		}
	}
	
	/**
	 * 存在修改
	 */
	public static boolean existModifyFlag() {
		return ConfLogic.isModifyFlag() || ConfLogicOrder.isModifyFlag();
	}

}
