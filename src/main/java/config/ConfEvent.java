package config;

import utils.CsvUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfEvent {
	public int index;
	public String id;
	public String name;
	public String desc;
	public String parmas;
	public Set<Integer> repellentActions = new HashSet<>();

	private static final Map<String, ConfEvent> datas = new HashMap<>();
	
	private ConfEvent() {
		this.index = 0;
		this.id = "";
		this.name = "";
		this.desc = "";
		this.parmas = "";
	}
	public int getIndex() {return index;}
	public String getSn() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getInfo() {
		return desc;
	}
	public String getEventParam() {
		return parmas;
	}
	
	public boolean containEventParam(String key, String value) {
		return false;
	}
	
	
	public static Map<String, ConfEvent> getDatas() {
		return datas;
	}
	
	public static ConfEvent find(String sn) {
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
			ConfEvent confEvent = new ConfEvent();
			int columnIndex = 0;
			confEvent.index = index.get();
			confEvent.id = e[columnIndex++];
			confEvent.name = e[columnIndex++];
			confEvent.desc = e[columnIndex++];
			confEvent.parmas = e[columnIndex++];
			String[] str = e[columnIndex].split(",");
			for (String s : str) {
				if (!s.isEmpty()) {
					confEvent.repellentActions.add(Integer.valueOf(s));
				}
			}
			datas.put(confEvent.id, confEvent);

		});
		System.out.println("event data  "+datas.toString());
		return true;
	}
	
	public static String getEventShowString(String sn) {
		ConfEvent confEvent = find(sn);
		if (null == confEvent) {
			return "";
		}
		return confEvent.desc;
	}
	
}
