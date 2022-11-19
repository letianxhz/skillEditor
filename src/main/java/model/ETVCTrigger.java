package model;

public enum ETVCTrigger {
	SN				(ETVColumnValueType.Integer, "编号", "sn"),
	SCENE_SN		(ETVColumnValueType.String, "场景", "sceneSn"),
	AREA_ID			(ETVColumnValueType.Integer, "区域Id", "areaId"),
	CONDITION_TYPE	(ETVColumnValueType.String, "条件", "conditionType"),
	CONDITION_PARAM	(ETVColumnValueType.String, "条件参数", "conditionParam"),
	ACTION_TYPE		(ETVColumnValueType.String, "动作", "actionType"),
	ACTION_PARAM	(ETVColumnValueType.String, "动作参数", "actionParam"),
	EXT_TIMES		(ETVColumnValueType.Integer, "执行次数", "extTimes"),
	LEAVE_ACTION_TYPE(ETVColumnValueType.String, "离开动作", "leaveActionType"),
	LEAVE_ACTION_PARAM(ETVColumnValueType.String, "离开动作参数", "leaveActionParam"),
	;
	
	private ETVCTrigger(ETVColumnValueType type, String columnName, String propertyName) {
		this.type = type;
		this.columnName = columnName;
		this.propertyName = propertyName;
	}
	
	private ETVColumnValueType type;
	private String columnName;
	private String propertyName;
	
	public ETVColumnValueType getType() {
		return type;
	}
	public String getColumnName() {
		return columnName;
	}
	public String getPropertyName() {
		return propertyName;
	}
}
