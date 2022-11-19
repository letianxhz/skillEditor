package config;

import java.util.ArrayList;
import java.util.List;

public class TriggerInfo {
    public int eventId;
    public int relation;
    public List<CondInfo> conditions = new ArrayList<>(2);
    public List<ActionInfo> actions = new ArrayList<>(2);

    public TriggerInfo() {
        this.eventId = 0;
        this.relation = 0;
        for (int i = 0; i < 2; i++) {
            conditions.add(new CondInfo());
        }
        for (int i = 0; i < 2; i++) {
            actions.add(new ActionInfo());
        }
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public List<CondInfo> getCond() {
        return conditions;
    }

    public void setCond(List<CondInfo> cond) {
        this.conditions = cond;
    }

    public List<ActionInfo> getAction() {
        return actions;
    }

    public void setAction(List<ActionInfo> action) {
        this.actions = action;
    }

    public void tailor() {
        this.conditions.removeIf(info -> info.condType <= 0);
        this.actions.removeIf(info->info.actionType <= 0);
    }
}
