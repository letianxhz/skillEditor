package config;

import com.google.gson.Gson;
import utils.CsvUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfLogic implements Cloneable {
	private int sn;
	private String id;
	private boolean isNew;

	private String desc; //描述
	private LogicInfo logicInfo;


	public static final Map<Integer, ConfLogic> infos = new LinkedHashMap<>();
	private static final List<String[]> headers = new ArrayList<>();
	public static final Map<String, Integer> id2Index = new HashMap<>();
	public static  int index ;
	
	/**
	 * 修改标志
	 */
	private static boolean modifyFlag = false;

	public ConfLogic() {
		this.sn = 0;
		this.id = "";
		this.logicInfo = new LogicInfo();
	}

	public boolean isNew() {
		return isNew;
	}

	public int getSn() {
		return sn;
	}

	public String getId() {
		return id;
	}

	public String getEventId() {
		return String.valueOf(logicInfo.getActiveTrigger().getEventId());
	}

	public String getCdTime() {
		return String.valueOf(logicInfo.cdTime);
	}

	public String getExecCount() {
		return String.valueOf(logicInfo.execCount);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
		modifyFlag = true;
	}

	public int getRelation() {
		return logicInfo.getActiveTrigger().getRelation();
	}

	public String getCond1Type() {
		if (logicInfo.getActiveTrigger() == null) {
			return "0";
		}
		if (logicInfo.getDisableTrigger() == null || logicInfo.getActiveTrigger().getCond().isEmpty() || logicInfo.getActiveTrigger().getCond().get(0) == null) {
			return  "";
		}
		return String.valueOf(logicInfo.getActiveTrigger().getCond().get(0).condType);
	}

	public String getCond1Param() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getActiveTrigger().getCond().isEmpty() || logicInfo.getActiveTrigger().getCond().get(0) == null) {
			return  "";
		}
		return logicInfo.getActiveTrigger().getCond().get(0).condParam;
	}

	public String getCond2Type() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getActiveTrigger().getCond().size() < 2 || logicInfo.getActiveTrigger().getCond().get(1) == null) {
			return  "";
		}
		return String.valueOf(logicInfo.getActiveTrigger().getCond().get(1).condType);
	}

	public String getCond2Param() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getActiveTrigger().getCond().size() < 2 ||logicInfo.getActiveTrigger().getCond().get(1) == null) {
			return  "";
		}
		return logicInfo.getActiveTrigger().getCond().get(1).condParam;
	}

	public void setData2(LogicInfo info) {
		this.logicInfo = info;
	}

	public String getAction1Type() {
		if (logicInfo.getActiveTrigger().getAction() == null || logicInfo.getActiveTrigger().getAction().isEmpty() || logicInfo.getActiveTrigger().getAction().get(0) == null) {
			return "";
		}
		return String.valueOf(logicInfo.getActiveTrigger().getAction().get(0).getActionType());
	}

	public String getAction1Param() {
		if (logicInfo.getActiveTrigger().getAction() == null || logicInfo.getActiveTrigger().getAction().isEmpty() || logicInfo.getActiveTrigger().getAction().get(0) == null) {
			return "";
		}
		return logicInfo.getActiveTrigger().getAction().get(0).getActionParam();
	}

	public String getAction2Type() {
		if (logicInfo.getActiveTrigger().getAction() == null || logicInfo.getActiveTrigger().getAction().size() < 2 || logicInfo.getActiveTrigger().getAction().get(1) == null) {
			return "";
		}
		return String.valueOf(logicInfo.getActiveTrigger().getAction().get(1).getActionType());
	}

	public String getAction2Param() {
		if (logicInfo.getActiveTrigger().getAction() == null || logicInfo.getActiveTrigger().getAction().size() < 2 || logicInfo.getActiveTrigger().getAction().get(1) == null) {
			return "";
		}
		return logicInfo.getActiveTrigger().getAction().get(1).getActionParam();
	}

	public String getDisableEventId() {
		return String.valueOf(logicInfo.getDisableTrigger().getEventId());
	}

	public int getDisRelation() {
		return logicInfo.getDisableTrigger().getRelation();
	}

	public String getDisCond1Type() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getDisableTrigger().getCond().isEmpty() || logicInfo.getDisableTrigger().getCond().get(0) == null) {
			return  "";
		}
		return String.valueOf(logicInfo.getDisableTrigger().getCond().get(0).condType);
	}

	public String getDisCond1Param() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getDisableTrigger().getCond().isEmpty() || logicInfo.getDisableTrigger().getCond().get(0) == null) {
			return  "";
		}
		return logicInfo.getDisableTrigger().getCond().get(0).getCondParam();
	}

	public String getDisCond2Type() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getDisableTrigger().getCond().size() < 2  || logicInfo.getDisableTrigger().getCond().get(1) == null) {
			return  "";
		}
		return String.valueOf(logicInfo.getDisableTrigger().getCond().get(1).condType);
	}

	public String getDisCond2Param() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getDisableTrigger().getCond().size() < 2 || logicInfo.getDisableTrigger().getCond().get(1) == null) {
			return  "";
		}
		return logicInfo.getDisableTrigger().getCond().get(1).getCondParam();
	}

	public String getDisAction1Type() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getDisableTrigger().getAction().isEmpty() || logicInfo.getDisableTrigger().getAction().get(0) == null) {
			return  "";
		}
		return String.valueOf(logicInfo.getDisableTrigger().getAction().get(0).actionType);
	}

	public String getDisAction1Param() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getDisableTrigger().getAction().isEmpty() || logicInfo.getDisableTrigger().getAction().get(0) == null) {
			return  "";
		}
		return logicInfo.getDisableTrigger().getAction().get(0).getActionParam();
	}

	public String getDisAction2Type() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getDisableTrigger().getAction().size() < 2 || logicInfo.getDisableTrigger().getAction().get(1) == null) {
			return  "";
		}
		return String.valueOf(logicInfo.getDisableTrigger().getAction().get(1).actionType);
	}

	public String getDisAction2Param() {
		if (logicInfo.getDisableTrigger() == null || logicInfo.getDisableTrigger().getAction().size() < 2 || logicInfo.getDisableTrigger().getAction().get(1) == null) {
			return  "";
		}
		return  logicInfo.getDisableTrigger().getAction().get(1).getActionParam();
	}

	public void setSn(int sn) {
		this.sn = sn;
		modifyFlag = true;
	}

	public void setId(String id) {
		var old = this.id;
		this.id = id;
		this.isNew = false;
		modifyFlag = true;
		id2Index.remove(old);
		id2Index.put(id, this.sn);
	}

	public void setEventId(String eventId) {
		this.logicInfo.getActiveTrigger().setEventId(Integer.parseInt(eventId));
		modifyFlag = true;
	}

	public void setCdTime(String cdTime) {
		this.logicInfo.setCdTime(Double.parseDouble(cdTime));
		modifyFlag = true;
	}

	public void setExecCount(String execCount) {
		this.logicInfo.setExecCount(Integer.parseInt(execCount));
		modifyFlag = true;
	}

	public void setRelation(int relation) {
		this.logicInfo.getActiveTrigger().setRelation(relation);
		modifyFlag = true;
	}

	public void setCond1Type(String cond1Type) {
		var old = this.logicInfo.getActiveTrigger().getCond();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.isEmpty()) {
			old.add(new CondInfo());
		}
		old.get(0).setCondType(Integer.parseInt(cond1Type));
		this.logicInfo.getActiveTrigger().setCond(old);
		modifyFlag = true;
	}

	public void setCond1Param(String cond1Param) {
		var old = this.logicInfo.getActiveTrigger().getCond();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.isEmpty()) {
			old.add(new CondInfo());
			old.add(new CondInfo());
		}
		old.get(0).setCondParam(cond1Param);
		this.logicInfo.getActiveTrigger().setCond(old);
		modifyFlag = true;
	}

	public void setCond2Type(String cond2Type) {
		var old = this.logicInfo.getActiveTrigger().getCond();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.size() < 2) {
			old.add(1, new CondInfo());
		}
		old.get(1).setCondType(Integer.parseInt(cond2Type));
		this.logicInfo.getActiveTrigger().setCond(old);

		modifyFlag = true;
	}

	public void setCond2Param(String cond2Param) {
		var old = this.logicInfo.getActiveTrigger().getCond();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.size() < 2) {
			old.add(1, new CondInfo());
			old.add(1, new CondInfo());
		}
		old.get(1).setCondParam(cond2Param);
		this.logicInfo.getActiveTrigger().setCond(old);

		modifyFlag = true;
	}

	public void setAction1Type(String action1Type) {
		var old = this.logicInfo.getActiveTrigger().getAction();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.isEmpty()) {
			old.add(new ActionInfo());
		}
		old.get(0).setActionType(Integer.parseInt(action1Type));
		this.logicInfo.getActiveTrigger().setAction(old);
		modifyFlag = true;
	}

	public void setAction1Param(String action1Param) {
		var old = this.logicInfo.getActiveTrigger().getAction();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.isEmpty()) {
			old.add(new ActionInfo());
		}
		old.get(0).setActionParam(action1Param);
		this.logicInfo.getActiveTrigger().setAction(old);
		modifyFlag = true;
	}

	public void setAction2Type(String action2Type) {
		var old = this.logicInfo.getActiveTrigger().getAction();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.size() < 2) {
			old.add(1, new ActionInfo());
		}
		old.get(1).setActionType(Integer.parseInt(action2Type));
		this.logicInfo.getActiveTrigger().setAction(old);
		modifyFlag = true;
	}

	public void setAction2Param(String action2Param) {
		var old = this.logicInfo.getActiveTrigger().getAction();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.size() < 2) {
			old.add(1, new ActionInfo());
		}
		old.get(1).setActionParam(action2Param);
		this.logicInfo.getActiveTrigger().setAction(old);
		modifyFlag = true;
	}

	public void setDisableEventId(String disableEventId) {
		this.logicInfo.getDisableTrigger().setEventId(Integer.parseInt(disableEventId));

		modifyFlag = true;
	}

	public void setDisRelation(int disRelation) {
		this.logicInfo.getDisableTrigger().setRelation(disRelation);

		modifyFlag = true;
	}

	public void setDisCond1Type(String disCond1Type) {
		var old = this.logicInfo.getDisableTrigger().getCond();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.isEmpty()) {
			old.add(new CondInfo());
		}
		old.get(0).setCondType(Integer.parseInt(disCond1Type));
		this.logicInfo.getDisableTrigger().setCond(old);

		modifyFlag = true;
	}

	public void setDisCond1Param(String disCond1Param) {
		var old = this.logicInfo.getDisableTrigger().getCond();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.isEmpty()) {
			old.add(new CondInfo());
		}
		old.get(0).setCondParam(disCond1Param);
		this.logicInfo.getDisableTrigger().setCond(old);

		modifyFlag = true;
	}

	public void setDisCond2Type(String disCond2Type) {
		var old = this.logicInfo.getDisableTrigger().getCond();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.size() < 2) {
			old.add(1, new CondInfo());
		}
		old.get(1).setCondType(Integer.parseInt(disCond2Type));
		this.logicInfo.getDisableTrigger().setCond(old);

		modifyFlag = true;
	}

	public void setDisCond2Param(String disCond2Param) {
		var old = this.logicInfo.getDisableTrigger().getCond();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.size() < 2) {
			old.add(1, new CondInfo());
		}
		old.get(1).setCondParam(disCond2Param);
		this.logicInfo.getDisableTrigger().setCond(old);

		modifyFlag = true;
	}

	public void setDisAction1Type(String disAction1Type) {
		var old = this.logicInfo.getDisableTrigger().getAction();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.isEmpty()) {
			old.add(new ActionInfo());
		}
		old.get(0).setActionType(Integer.parseInt(disAction1Type));
		this.logicInfo.getDisableTrigger().setAction(old);
		modifyFlag = true;
	}

	public void setDisAction1Param(String disAction1Param) {
		var old = this.logicInfo.getDisableTrigger().getAction();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.isEmpty()) {
			old.add(new ActionInfo());
		}
		old.get(0).setActionParam(disAction1Param);
		this.logicInfo.getDisableTrigger().setAction(old);

		modifyFlag = true;
	}

	public void setDisAction2Type(String disAction2Type) {
		var old = this.logicInfo.getDisableTrigger().getAction();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.size() < 2) {
			old.add(1, new ActionInfo());
		}
		old.get(1).setActionType(Integer.parseInt(disAction2Type));
		this.logicInfo.getDisableTrigger().setAction(old);
		modifyFlag = true;
	}

	public void setDisAction2Param(String disAction2Param) {
		var old = this.logicInfo.getDisableTrigger().getAction();
		if (old == null) {
			old = new ArrayList<>(2);
		}
		if (old.size() < 2) {
			old.add(1, new ActionInfo());
		}
		old.get(1).setActionParam(disAction2Param);
		this.logicInfo.getDisableTrigger().setAction(old);

		modifyFlag = true;
	}
	
	//------------------------------------------------------
	// static function

	public static Map<Integer, ConfLogic> getDatas() {
		return infos;
	}
	
	public static boolean isModifyFlag() {
		return modifyFlag;
	}
	
	public static ConfLogic find(int sn) {
		return infos.get(sn);
	}

	public static int findSnById(String id) {
		if (!id2Index.containsKey(id)) {
			return -1;
		}
		return id2Index.get(id);
	}

	public static ConfLogic create() {
		ConfLogic confLogic = new ConfLogic();
		confLogic.isNew = true;
		confLogic.id = "";
		confLogic.sn = ++index;
		infos.put(confLogic.sn, confLogic);
		id2Index.put(confLogic.id, confLogic.sn);
		modifyFlag = true;
		return confLogic;
	}
	
	public ConfLogic clone(int sceneSn) throws CloneNotSupportedException {
		ConfLogic cloneConfLogic = (ConfLogic)super.clone();
		modifyFlag = true;
		return cloneConfLogic;
	}
	
	public static void remove(int sn) {
		var rm = infos.remove(sn);
		id2Index.remove(rm.id);
		modifyFlag = true;
	}

	public static boolean load(String fileName) throws IOException  {
		File f = new File(fileName);
		InputStream inputStream = new FileInputStream(f);
		List<String[]> ret = CsvUtil.read(inputStream, "UTF-8");

		String[] header = new String[ret.get(0).length];
		for (int i = 0; i < ret.get(0).length; i++) {
			String[] tmp = ret.get(0)[i].split("@");
			header[i] = tmp[0];
		}
		index = 0;
		infos.clear();
		id2Index.clear();
		headers.clear();

		ret.forEach((e)->{
			index++;
			if (index <= 2) {
				headers.add(e);
				return;
			}
			ConfLogic logic = new ConfLogic();
			logic.sn = index;
			for (int j = 0; j < e.length; j++) {
				String tmpVal = e[j];
				switch (header[j]) {
					case "index"->logic.sn = Integer.parseInt(tmpVal);
					case "id" ->logic.id = tmpVal;
					case "desc" -> logic.desc = tmpVal;
					case "data"-> {
						Gson gson = new Gson();
						var data = gson.fromJson(tmpVal, LogicInfo.class);
						logic.setData2(data);
					}
					default -> {
					}
				}
			}
			infos.put(logic.sn, logic);
			id2Index.put(logic.id, logic.sn);
		});
		return true;
	}

	/**
	 * 保存
	 * @return
	 */
	public static boolean save2() {
		String dataDirectoryPath = ConfProperties.getKeyValue(ConfProperties.CONF_DIRECTORY_PATH);
		String passiveSkillEditorFileName = dataDirectoryPath + "passive_skill_editor.csv";
		List<String[]> ret = new ArrayList<>(headers);

		for (ConfLogic info : infos.values()) {
			String[] save = new String[3];
			save[0] = info.id;
			save[1] = info.desc;
			Gson gson = new Gson();
			info.logicInfo.tailorTriggerInfo();
			var json = gson.toJson(info.logicInfo);
			save[2] = json;
			ret.add(save);
		}
		try {
			CsvUtil.write(ret, passiveSkillEditorFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
