package config;

public class LogicInfo {
    public TriggerInfo activeTrigger;
    public TriggerInfo disableTrigger;
    public double cdTime;

    public int execCount; //执行次数

    public int getExecCount() {
        return execCount;
    }

    public void setExecCount(int execCount) {
        this.execCount = execCount;
    }

    public double getCdTime() {
        return cdTime;
    }

    public void setCdTime(double cdTime) {
        this.cdTime = cdTime;
    }

    public TriggerInfo getActiveTrigger() {
        return activeTrigger;
    }

    public void setActiveTrigger(TriggerInfo active) {
        this.activeTrigger = active;
    }

    public TriggerInfo getDisableTrigger() {
        return disableTrigger;
    }

    public void setDisableTrigger(TriggerInfo disableTrigger) {
        this.disableTrigger = disableTrigger;
    }

    public LogicInfo() {
        this.activeTrigger = new TriggerInfo();
        this.disableTrigger = new TriggerInfo();
    }

    public void tailorTriggerInfo() {
        //如果策划不填cd时间默认就设置5s
        if (cdTime < 1.0) {
            cdTime = 5.0;
        }
        activeTrigger.tailor();
        disableTrigger.tailor();
    }

}


