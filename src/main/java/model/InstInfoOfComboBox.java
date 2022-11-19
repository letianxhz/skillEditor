package model;

import config.ConfScene;
import utils.MyUtils;

public class InstInfoOfComboBox {
	private final ConfScene confInst;
	
	public InstInfoOfComboBox(ConfScene confInst) {
		this.confInst = confInst;
	}

	public ConfScene getConfInst() {
		return confInst;
	}

	@Override
	public String toString() {
		return MyUtils.JOIN_SIGNAL_LEFT + confInst.getSn() + MyUtils.JOIN_SIGNAL_RIGHT + confInst.getName();
	}
}
