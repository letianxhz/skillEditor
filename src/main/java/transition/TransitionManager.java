package transition;

import config.ConfLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TransitionManager {

    private static final TransitionManager instance = new TransitionManager();

    public Map<String, BattleTriggerConf> triggers = new HashMap<>();
    public Map<String, PassiveSkillConf> passiveSKills = new LinkedHashMap<>();


    private TransitionManager(){}

    public static TransitionManager getInstance() {
        return instance;
    }

    public void addTriggers(Map<String, BattleTriggerConf> ts) {
        triggers.clear();
        triggers.putAll(ts);
    }


    public void addPassiveSKills (Map<String, PassiveSkillConf> ps) {
        passiveSKills.clear();
        passiveSKills.putAll(ps);
    }

    /**
     * 迁移转换数据
     */
    public void transitionData() {
        Map<Integer, ConfLogic> infos = new LinkedHashMap<>();
        ArrayList<Integer> indexs = new ArrayList<>();
        Map<String, Integer> id2Index = new HashMap<>();
        for(PassiveSkillConf ps :  this.passiveSKills.values()) {
            ConfLogic logic = new ConfLogic();
            logic.setSn(ps.index);
            logic.setId(ps.id+"_editor");

            if (ps.triggerId != null && !ps.triggerId.equals("")) {
                BattleTriggerConf bt = this.triggers.get(ps.triggerId);
                assert bt != null;
                logic.setEventId(bt.event);
                logic.setRelation(bt.cond_relation);

                if (bt.condition1 != null && !"".equals(bt.condition1)) {
                    logic.setCond1Type(bt.condition1);
                    logic.setCond1Param(bt.cond_param1);

                }

                if (bt.condition2 != null && !"".equals(bt.condition2)) {
                    logic.setCond2Type(bt.condition2);
                    logic.setCond2Param(bt.cond_param2);
                }
            }

            if (ps.action_1 != null && !ps.action_1.equals("")) {
                logic.setAction1Type(ps.action_1);
                logic.setAction1Param(ps.action1_param);
            }
            if (ps.action_2 != null && !ps.action_2.equals("")) {
                logic.setAction2Type(ps.action_2);
                logic.setAction2Param(ps.action2_param);
            }

            if (ps.disable_trigger != null && !ps.disable_trigger.equals("")) {
                BattleTriggerConf dbt = this.triggers.get(ps.disable_trigger);

                logic.setDisableEventId(dbt.event);
                logic.setDisRelation(dbt.cond_relation);

                if (dbt.condition1 != null && !"".equals(dbt.condition1)) {
                    logic.setDisCond1Type(dbt.condition1);
                    logic.setDisCond1Param(dbt.cond_param1);
                }

                if (dbt.condition2 != null && !"".equals(dbt.condition2)) {
                    logic.setDisCond2Type(dbt.condition2);
                    logic.setDisCond2Param(dbt.cond_param2);
                }
            }

            if (ps.disable_action1 != null && !ps.disable_action1.equals("")) {
                logic.setDisAction1Type(ps.disable_action1);
                logic.setDisAction1Param(ps.disable_action1_param);
            }

            if (ps.disable_action2 != null && !ps.disable_action2.equals("")) {
                logic.setDisAction2Type(ps.disable_action2);
                logic.setDisAction2Param(ps.disable_action2_param);
            }

            if (logic.getEventId() != null && !"".equals(logic.getEventId()) && !"0".equals(logic.getEventId())) {
                indexs.add(ps.index);
                id2Index.put(ps.id, ps.index);
                infos.put(ps.index, logic);
            }
        }
        ConfLogic.infos.clear();
        ConfLogic.infos.putAll(infos);
        ConfLogic.id2Index.clear();
        ConfLogic.id2Index.putAll(id2Index);
    }

}
