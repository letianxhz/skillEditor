package config;

public class ActionInfo {
    public int actionType;
    public String actionParam;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getActionParam() {
        return actionParam;
    }

    public void setActionParam(String actionParam) {
        if (null == actionParam) {
            return;
        }
        this.actionParam = actionParam.replaceAll("\\s*|\r|\n|\t","");
    }

    public ActionInfo() {
    }
}


