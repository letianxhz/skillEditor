package model;

import config.ConfCondition;
import utils.MyUtils;

public class CondInfoOfComboBox {
	private final ConfCondition confCondition;
	
	private boolean warning = false;
	
	public CondInfoOfComboBox(ConfCondition confCondition) {
		this.warning = false;
		this.confCondition = confCondition;
	}

	public boolean isWarning() {
		return warning;
	}

	public ConfCondition getConfCondition() {
		return confCondition;
	}
	
	public void setWarn(boolean warning) {
		this.warning = warning;
	}

	@Override
	public String toString() {
		String showString = MyUtils.JOIN_SIGNAL_LEFT + confCondition.getId()
		+ MyUtils.JOIN_SIGNAL_RIGHT + confCondition.getDesc();
		
		if (warning) {
			return MyUtils.JOIN_SIGNAL_NOT_MATCH + showString;
		}
		
		return showString;
	}
	
}
