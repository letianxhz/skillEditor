package model;

import config.ConfAction;
import utils.MyUtils;

public class ActionInfoOfComboBox {
	private final ConfAction confAction;
	
	private boolean warning = false;
	
	public ActionInfoOfComboBox(ConfAction confAction) {
		this.warning = false;
		this.confAction = confAction;
	}

	public ConfAction getConfAction() {
		return confAction;
	}
	
	public void setWarn(boolean warning) {
		this.warning = warning;
	}

	public boolean isWarning() {
		return warning;
	}

	@Override
	public String toString() {
		String show = MyUtils.JOIN_SIGNAL_LEFT + confAction.getId() + MyUtils.JOIN_SIGNAL_RIGHT + confAction.getInfo();
		
		if (warning) {
			return MyUtils.JOIN_SIGNAL_NOT_MATCH + show;
		}
		
		return show;
	}
	
}
