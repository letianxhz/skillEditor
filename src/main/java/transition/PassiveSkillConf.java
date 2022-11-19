package transition;

import utils.CsvUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PassiveSkillConf {
    String id;

    int index;

    String triggerId;
    String action_1;
    String action1_param;
    String action_2;
    String action2_param;

    String disable_trigger;
    String disable_action1;
    String disable_action1_param;
    String disable_action2;
    String disable_action2_param;

    public static boolean load(String fileName) throws IOException {
        File f = new File(fileName);
        InputStream inputStream = new FileInputStream(f);
        List<String[]> ret = CsvUtil.read(inputStream, "UTF-8");
        String[] header = new String[ret.get(0).length];
        for (int i = 0; i < ret.get(0).length; i++) {
            String[] tmp = ret.get(0)[i].split("@");
            header[i] = tmp[0];
        }

        Map<String, PassiveSkillConf> passiveSkillConfMap = new LinkedHashMap<>();
        final int[] tmpIndex = {0};
        AtomicInteger index = new AtomicInteger();
        ret.forEach((e)->{
            index.getAndIncrement();
            if (index.get() <=2) {
                return;
            }

            PassiveSkillConf ps = new PassiveSkillConf();
            ps.index = index.get();
            for (int j = 0; j < e.length; j++) {
                String tmpVal = e[j];
                switch (header[j]) {
                    case "id"-> ps.id = tmpVal;
                    case "trigger"-> ps.triggerId = tmpVal;
                    case "action_1"->ps.action_1 = tmpVal;
                    case "action1_param"->ps.action1_param = tmpVal;
                    case "action_2"-> ps.action_2 = tmpVal;
                    case "action2_param"-> ps.action2_param = tmpVal;
                    case "disable_trigger"-> ps.disable_trigger = tmpVal;
                    case "disable_action_1"-> ps.disable_action1 = tmpVal;
                    case "disable_param_1"-> ps.disable_action1_param = tmpVal;
                    case "disable_action_2"-> ps.disable_action2 = tmpVal;
                    case "disable_param_2"-> ps.disable_action2_param = tmpVal;
                }
            }
            if (!ps.id.isEmpty()) {
                passiveSkillConfMap.put(ps.id, ps);
            }
        });
        TransitionManager.getInstance().addPassiveSKills(passiveSkillConfMap);
        return true;
    }
}
