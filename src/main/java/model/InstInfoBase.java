package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InstInfoBase {
	protected StringProperty sn;
	protected StringProperty name;
	
	public InstInfoBase(String sn, String name) {
		this.sn = new SimpleStringProperty(sn);
		this.name = new SimpleStringProperty(name);
	}

	public StringProperty snProperty() {
		return this.sn;
	}
	
	public StringProperty nameProperty() {
		return this.name;
	}
	
	public void setSn(String sn) {
		this.sn.set(sn);
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getSn() {
		return this.sn.getValueSafe();
	}
	
	public String getName() {
		return this.name.getValueSafe();
	}
}
