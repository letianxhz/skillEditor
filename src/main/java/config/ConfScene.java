package config;


import java.util.HashMap;
import java.util.Map;

public class ConfScene {
	/**
	 * sn列索引
	 */
	public static final int COLUMN_INDEX_OF_SN = 0;
	
	private int sn;
	private String name;
	private int type;

	
	private static Map<Integer, ConfScene> datas = new HashMap<>();
	
	private static Map<Integer, String> sceneTypes = new HashMap<>();
	
	private ConfScene() {
		this.sn = 0;
		this.name = "";
		this.type = 0;
	}
	
	public ConfScene(int sn, String name, int type) {
		this.sn = sn;
		this.name = name;
		this.type = type;
	}
	
	public int getSn() {
		return sn;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	//------------------------------------------------------
	// static functions
	
	public static void initSceneTypes() {
		sceneTypes.clear();
		sceneTypes.put(1, "被动技能配置");
		sceneTypes.put(2, "被动技能逻辑");
	}
	
	public static String getSceneType(int type) {
		return sceneTypes.getOrDefault(type, String.valueOf(type));
	}
	
	public static boolean load(String fileName) {
		ConfScene instInfo = new ConfScene();
		instInfo.sn = 1;
		instInfo.name = "被动技能配置";
		instInfo.type = 1;
		datas.put(1, instInfo);

	/*	ConfScene instInfo2 = new ConfScene();
		instInfo2.sn = 1;
		instInfo2.name = "被动技能逻辑";
		instInfo2.type = 2;
		datas.put(2, instInfo2);*/
		// 初始化场景类型
		initSceneTypes();
		return true;
	}
	
	public static Map<Integer, ConfScene> getDatas() {
		return datas;
	}
	
	public static ConfScene find(int sn) {
		return datas.get(sn);
	}
	
	public static String getSceneName(int sn) {
		ConfScene confInst = find(sn);
		if (null == confInst) {
			return "";
		}
		return confInst.name;
	}

	
}
