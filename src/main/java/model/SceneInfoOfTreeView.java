package model;

import config.ConfScene;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.MyUtils;

public class SceneInfoOfTreeView {

	private final ConfScene confScene;
	
	private final StringProperty itemValue;
	
	public SceneInfoOfTreeView(String itemValue) {
		this.confScene = null;
		
		this.itemValue = new SimpleStringProperty(itemValue);
	}
	
	public SceneInfoOfTreeView(ConfScene confScene) {
		this.confScene = confScene;
		
		this.itemValue = new SimpleStringProperty(MyUtils.JOIN_SIGNAL_LEFT 
				+ confScene.getSn() + MyUtils.JOIN_SIGNAL_RIGHT + confScene.getName());
	}
	
	@Override
	public String toString() {
		return this.getItemValue();
	}

	public StringProperty itemValueProperty() {
		return itemValue;
	}
	
	public String getItemValue() {
		return this.itemValue.get();
	}

	public void setItemValue(String itemValue) {
		this.itemValue.set(itemValue);
	}
	
	public int getType() {
		return this.confScene.getType();
	}

	public ConfScene getConfScene() {
		return confScene;
	}
}
