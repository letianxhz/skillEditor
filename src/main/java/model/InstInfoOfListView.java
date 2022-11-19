package model;

import config.ConfScene;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.MyUtils;

public class InstInfoOfListView {

	private final ConfScene confInst;
	
	private final StringProperty name;
	
	public InstInfoOfListView(ConfScene confInst) {
		this.confInst = confInst;
		
		this.name = new SimpleStringProperty(confInst.getName());
	}
	
	public ConfScene getConfInst() {
		return confInst;
	}
	
	public String getSn() {
		return String.valueOf(confInst.getSn());
	}
	
	public String getName() {
		return this.name.get();
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}
	
	@Override
	public String toString() {
		return MyUtils.JOIN_SIGNAL_LEFT + this.confInst.getSn() + MyUtils.JOIN_SIGNAL_RIGHT + confInst.getName();
	}
}
