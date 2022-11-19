package model;

import config.ConfEvent;
import utils.MyUtils;

public class EventInfoOfComboBox {
	private final ConfEvent confEvent;
	
	public EventInfoOfComboBox(ConfEvent confEvent) {
		this.confEvent = confEvent;
	}

	public ConfEvent getConfEvent() {
		return confEvent;
	}

	@Override
	public String toString() {
		return MyUtils.JOIN_SIGNAL_LEFT + confEvent.id + MyUtils.JOIN_SIGNAL_RIGHT + confEvent.desc;
	}
	
}
