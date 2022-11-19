package config;

import utils.CsvUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfAction {
	private int index;
	private String id;
	private String name;
	private String desc;
	private String param;
	private String example;

	private final Map<String, String> eventParams = new HashMap<>();

	private static final Map<String, ConfAction> datas = new HashMap<>();
	
	private ConfAction() {
		this.index = 0;
		this.id = "";
		this.name = "";
		this.desc = "";
		this.param = "";
		this.example = "";
	}

	public int getIndex() {return index; }

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getInfo() {
		return desc;
	}

	public String getParam() {
		return param;
	}
	
	public String getExample() {
		return example;
	}

	/**
	 * 是否匹配event
	 * @param confEvent
	 * @return
	 */
	public boolean matchEventParam(ConfEvent confEvent) {
		return !confEvent.repellentActions.contains(Integer.valueOf(this.id));
		/*for (Map.Entry<String, String> param : eventParams.entrySet()) {
			if (!confEvent.containEventParam(param.getKey(), param.getValue())) {
				return true;
			}
		}
		return false;*/
	}

	public static Map<String, ConfAction> getDatas() {
		return datas;
	}
	
	public static ConfAction find(String sn) {
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
			ConfAction action = new ConfAction();
			int columnIndex = 0;
			action.index = index.get();
			action.id = e[columnIndex++];
			action.name = e[columnIndex++];
			action.desc = e[columnIndex++];
			action.param = e[columnIndex++];
			action.example = e[columnIndex];
			datas.put(action.id, action);

		});
		System.out.println("event data  "+datas.toString());
		return true;
	}
	
	public static String getActionShowString(String sn) {
		ConfAction confAction = find(sn);
		if (null == confAction) {
			return "";
		}
		
		return confAction.desc;
	}

	public void save() {
	}

	/**
	 *
	 * @Title: updateCSV
	 * @Description: 实现对csv文件的读取并修改指定行列的内容
	 * @param inFile  读取文件路径
	 * @param outFile 写入文件路径
	 * @param rowNum  目标行号
	 * @param colNum  目标列号
	 * @param target  目标内容
	 */
	public static void updateCSV(String inFile, String outFile, int rowNum, int colNum, String target) {
		String inString = "";
		List<String[]> list = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(inFile)));
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)));
			while ((inString = reader.readLine()) != null) {
				list.add(inString.split(","));
			}
			reader.close();
			for (int i = 0; i < list.size(); i++) {
				if (i > rowNum) {
					list.get(i)[colNum] = target;
				}
			}
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.get(i).length; j++) {
					writer.write(list.get(i)[j].toString() + ",");
					if (j == list.get(i).length - 1) {
						writer.newLine();
					}
				}
			}
			writer.close();
		} catch (FileNotFoundException ex) {
			System.out.println("文件哪去了");
		} catch (IOException ex) {
			System.out.println("读写出错了!");
		}
	}

}
