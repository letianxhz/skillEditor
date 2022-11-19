package config;

import utils.CsvUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfCondition {
	private int index;
	private String id;
	private String name;
	private String desc;
	private String param;
	private String example;
	private String eventParam;
	

	private static Map<String, ConfCondition> datas = new HashMap<>();
	
	private ConfCondition() {
		this.index = 0;
		this.id = "";
		this.name = "";
		this.desc = "";
		this.param = "";
		this.example = "";
		this.eventParam = "";
	}
	public int getIndex() {return index; }
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDesc() {
		return desc;
	}
	public String getParam() {
		return param;
	}
	public String getExample() {
		return example;
	}

	/**
	 * 需要加个事件和条件的匹配检查
	 * @param confEvent
	 * @return
	 */
	public boolean matchEventParam(ConfEvent confEvent) {
	/*	for (Map.Entry<String, String> param : eventParams.entrySet()) {
			if (!confEvent.containEventParam(param.getKey(), param.getValue())) {
				return false;
			}
		}*/
		
		return true;
	}
	
	public static Map<String, ConfCondition> getDatas() {
		return datas;
	}
	
	public static ConfCondition find(String sn) {
		return datas.get(sn);
	}

	public static boolean load(String fileName) throws IOException {
		File f = new File(fileName);
		InputStream inputStream = new FileInputStream(f);
		List<String[]> ret = CsvUtil.read(inputStream, "UTF-8");
		datas.clear();
		AtomicInteger index = new AtomicInteger();
		ret.forEach((e)->{
			index.getAndIncrement();
			if (index.get() <= 2) {
				return;
			}
			ConfCondition condition = new ConfCondition();
			int columnIndex = 0;
			condition.index = index.get();
			condition.id = e[columnIndex++];
			condition.name = e[columnIndex++];
			condition.desc = e[columnIndex++];
			condition.param = e[columnIndex++];
			condition.example = e[columnIndex];
			datas.put(condition.id, condition);

		});
		System.out.println("condition data  "+datas.toString());
		return true;
	}


	public static String getConditionShowString(String sn) {
		ConfCondition confCondition = find(sn);
		if (null == confCondition) {
			return "";
		}
		
		return confCondition.desc;
	}
	
}
