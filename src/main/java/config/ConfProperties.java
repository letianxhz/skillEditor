package config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfProperties {

	/**
	 * 配置文件名称
	 */
	private static final String CONF_FILE_PATH = "conf.properties";
	
	/**
	 * 数据文件目录
	 */
	public static final String CONF_DIRECTORY_PATH = "confDirectoryPath";

	public static final String CONF_DIR_TMP_PATH= "confTmpDirectoryPath";

	public static final String OLD_CONF_DIR_PATH = "oldConfDirectoryPath";

	/**
	 * 数据
	 */
	private static final Properties prop = new Properties();
	
	private ConfProperties() {
		
	}

	/**
	 * 载入配置数据
	 */
	public static void Load() {
		try {
			FileInputStream in = new FileInputStream(CONF_FILE_PATH);
			prop.load(in);
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 保存配置数据
	 */
	public static void Save() {
		// 保存路径
		FileOutputStream oFile;
		try {
			oFile = new FileOutputStream(CONF_FILE_PATH);
			prop.store(oFile, "Comment");
			oFile.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 通过主键获取配置数据
	 * @param key
	 * @return
	 */
	public static String getKeyValue(String key) {
		return prop.getProperty(key);
	}
	
	/**
	 * 设置配置数据，不保存
	 * @param key
	 * @param value
	 */
	public static void setKeyValue(String key, String value) {
		prop.setProperty(key, value);
	}
	
	
	/**
	 * 设置配置数据并保存
	 * @param key
	 * @param value
	 */
	public static void setAndSaveKeyValue(String key, String value) {
		setKeyValue(key, value);
		Save();
	}

}
