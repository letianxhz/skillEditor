package model;



public enum SkillLogic {
	ELOGIC_TVC_SN(ETVColumnValueType.Integer, "id", "id"),
	ELOGIC_TVC_EVENT(ETVColumnValueType.String, "生效事件", "event"),
	ELOGIC_TVC_RELATION(ETVColumnValueType.String, "生效关系", "relation"),
	ELOGIC_TVC_COND1_TYPE(ETVColumnValueType.String, "条件1类型", "cond1Type"),
	ELOGIC_TVC_COND1_PARAM(ETVColumnValueType.String, "条件1参数", "cond1Param"),
	ELOGIC_TVC_COND2_TYPE(ETVColumnValueType.String, "条件2类型", "cond2Type"),
	ELOGIC_TVC_COND2_PARAM(ETVColumnValueType.String, "条件2参数", "cond2Param"),
	ELOGIC_TVC_ACTION1_TYPE(ETVColumnValueType.String, "动作1类型", "action1Type"),
	ELOGIC_TVC_ACTION1_PARAM(ETVColumnValueType.String, "动作1参数", "action1Param"),
	ELOGIC_TVC_ACTION2_TYPE(ETVColumnValueType.String, "动作2类型", "action2Type"),
	ELOGIC_TVC_ACTION2_PARAM(ETVColumnValueType.String, "动作2参数", "action2Param"),
	ELOGIC_TVC_DISABLE_EVENT(ETVColumnValueType.Integer, "失效事件", "disableEvent"),
	ELOGIC_TVC_RELATION_DISABLE(ETVColumnValueType.String, "失效关系", "disRelation"),
	ELOGIC_TVC_COND1_TYPE_DISABLE(ETVColumnValueType.String, "失效条件1类型", "disCond1Type"),
	ELOGIC_TVC_COND1_PARAM_DISABLE(ETVColumnValueType.String, "失效条件1参数", "disCond1Param"),
	ELOGIC_TVC_COND2_TYPE_DISABLE(ETVColumnValueType.String, "失效条件2类型", "disCond2Type"),
	ELOGIC_TVC_COND2_PARAM_DISABLE(ETVColumnValueType.String, "失效条件2参数", "disCond2Param"),
	ELOGIC_TVC_DIS_ACTION1_TYPE(ETVColumnValueType.String, "失效动作1类型", "disAction1Type"),
	ELOGIC_TVC_DIS_ACTION1_PARAM(ETVColumnValueType.String, "失效动作1参数", "disAction1Param"),
	ELOGIC_TVC_DIS_ACTION2_TYPE(ETVColumnValueType.String, "失效动作2类型", "disAction2Type"),
	ELOGIC_TVC_DIS_ACTION2_PARAM(ETVColumnValueType.String, "失效动作2参数", "disAction2Param");

	SkillLogic(ETVColumnValueType type, String columnName, String propertyName) {
		this.type = type;
		this.columnName = columnName;
		this.propertyName = propertyName;
	}

	private final ETVColumnValueType type;
	private final String columnName;
	private final String propertyName;

	public String getColumnName() {
		return columnName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public ETVColumnValueType getType() {
		return type;
	}
}
