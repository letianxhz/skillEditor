package config;

public class CondInfo {
    public int condType;
    public String condParam;

    public int getCondType() {
        return condType;
    }

    public void setCondType(int condType) {
        this.condType = condType;
    }

    public String getCondParam() {
        return condParam;
    }

    public void setCondParam(String condParam) {
        this.condParam = condParam;
    }

    public CondInfo() {
        this.condType = 0;
        this.condParam = "";
    }
}
