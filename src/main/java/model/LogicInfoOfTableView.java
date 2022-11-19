package model;

import config.ConfAction;
import config.ConfCondition;
import config.ConfEvent;
import config.ConfLogic;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.MyUtils;

public class LogicInfoOfTableView {
	
	private final ConfLogic confLogic;

	private final StringProperty id;

	private final StringProperty event;
	
	private final StringProperty relation;
	
	private final StringProperty cond1Type;
	
	private final StringProperty cond1Param;
	
	private final StringProperty cond2Type;
	
	private final StringProperty cond2Param;
	
	private final StringProperty action1Type;
	
	private final StringProperty action1Param;
	
	private final StringProperty action2Type;
	
	private final StringProperty action2Param;

	private final StringProperty disableEvent;

	private final StringProperty disRelation;

	private final StringProperty disCond1Type;

	private final StringProperty disCond1Param;

	private final StringProperty disCond2Type;

	private final StringProperty disCond2Param;

	private final StringProperty disAction1Type;

	private final StringProperty disAction1Param;

	private final StringProperty disAction2Type;

	private final StringProperty disAction2Param;

	private final StringProperty cdTime;

	private final StringProperty desc;

	private final StringProperty execCount;



	public LogicInfoOfTableView(ConfLogic confLogic) {
		this.confLogic = confLogic;
		
		this.id = new SimpleStringProperty(confLogic.getId());
		this.event = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getEventId()), ConfEvent.getEventShowString(confLogic.getEventId())));
		this.relation = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getRelation()), MyUtils.relationToName(confLogic.getRelation())));
		this.cond1Type = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getCond1Type()), ConfCondition.getConditionShowString(confLogic.getCond1Type())));
		this.cond1Param = new SimpleStringProperty(String.valueOf(confLogic.getCond1Param()));
		this.cond2Type = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getCond2Type()), ConfCondition.getConditionShowString(confLogic.getCond2Type())));
		this.cond2Param = new SimpleStringProperty(String.valueOf(confLogic.getCond2Param()));
		this.action1Type = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getAction1Type()), ConfAction.getActionShowString(confLogic.getAction1Type())));
		this.action1Param = new SimpleStringProperty(String.valueOf(confLogic.getAction1Param()));
		this.action2Type = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getAction2Type()), ConfAction.getActionShowString(confLogic.getAction2Type())));
		this.action2Param = new SimpleStringProperty(String.valueOf(confLogic.getAction2Param()));
		this.disableEvent = new SimpleStringProperty(String.valueOf(confLogic.getDisableEventId()));
		this.disRelation = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getDisRelation()), MyUtils.relationToName(confLogic.getDisRelation())));
		this.disCond1Type = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getDisCond1Type()), ConfCondition.getConditionShowString(confLogic.getDisCond1Type())));
		this.disCond1Param = new SimpleStringProperty(String.valueOf(confLogic.getDisCond1Param()));
		this.disCond2Type = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getDisCond2Type()), ConfCondition.getConditionShowString(confLogic.getDisCond2Type())));
		this.disCond2Param = new SimpleStringProperty(String.valueOf(confLogic.getDisCond2Param()));
		this.disAction1Type = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getDisAction1Type()), ConfAction.getActionShowString(confLogic.getDisAction1Type())));
		this.disAction1Param = new SimpleStringProperty(String.valueOf(confLogic.getDisAction1Param()));
		this.disAction2Type = new SimpleStringProperty(MyUtils.formatColumnValue(String.valueOf(confLogic.getDisAction2Type()), ConfAction.getActionShowString(confLogic.getDisAction2Type())));
		this.disAction2Param = new SimpleStringProperty(String.valueOf(confLogic.getDisAction2Param()));
		this.cdTime = new SimpleStringProperty(String.valueOf(confLogic.getCdTime()));
		this.desc = new SimpleStringProperty(String.valueOf(confLogic.getDesc()));
		this.execCount = new SimpleStringProperty(String.valueOf(confLogic.getExecCount()));
	}
	
	public ConfLogic getConfLogic() {
		return confLogic;
	}
	

	//----------------------------------
	//javaBean
	public StringProperty idProperty() {
		return this.id;
	}
	public StringProperty eventProperty() {
		return this.event;
	}
	public StringProperty relationProperty() {
		return this.relation;
	}
	public StringProperty cond1TypeProperty() {
		return this.cond1Type;
	}
	public StringProperty cond1ParamProperty() {
		return this.cond1Param;
	}
	public StringProperty cond2TypeProperty() {
		return this.cond2Type;
	}
	public StringProperty cond2ParamProperty() {
		return this.cond2Param;
	}
	public StringProperty action1TypeProperty() {
		return this.action1Type;
	}
	public StringProperty action1ParamProperty() {
		return this.action1Param;
	}
	public StringProperty action2TypeProperty() {
		return this.action2Type;
	}
	public StringProperty action2ParamProperty() {
		return this.action2Param;
	}
	public StringProperty disableEventProperty() {
		return this.disableEvent;
	}
	public StringProperty disRelationProperty() {
		return this.disRelation;
	}
	public StringProperty disCond1TypeProperty() {
		return this.disCond1Type;
	}
	public StringProperty disCond1ParamProperty() {
		return this.disCond1Param;
	}
	public StringProperty disCond2TypeProperty() {
		return this.disCond2Type;
	}
	public StringProperty disCond2ParamProperty() {
		return this.disCond2Param;
	}
	public StringProperty disAction1TypeProperty() {
		return this.disAction1Type;
	}
	public StringProperty disAction1ParamProperty() {
		return this.disAction1Param;
	}
	public StringProperty disAction2TypeProperty() {
		return this.disAction2Type;
	}
	public StringProperty disAction2ParamProperty() {
		return this.disAction2Param;
	}
	public StringProperty cdTimeProperty() {
		return this.cdTime;
	}
	public StringProperty descProperty() {
		return this.desc;
	}
	public StringProperty execCountProperty(){return this.execCount;}
}
