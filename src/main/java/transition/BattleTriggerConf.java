package transition;

import utils.CsvUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BattleTriggerConf {
    String id;
    String event;
    int cond_relation;
    String condition1;
    String cond_param1;
    String condition2;
    String cond_param2;


    public static boolean load(String fileName) throws IOException {
        File f = new File(fileName);
        InputStream inputStream = new FileInputStream(f);
        List<String[]> ret = CsvUtil.read(inputStream, "UTF-8");
        String[] header = new String[ret.get(0).length];
        for (int i = 0; i < ret.get(0).length; i++) {
            String[] tmp = ret.get(0)[i].split("@");
            header[i] = tmp[0];
        }

        Map<String, BattleTriggerConf> triggers = new HashMap<>();
        AtomicInteger index = new AtomicInteger();
        ret.forEach((e)->{
            index.getAndIncrement();
            if (index.get() <=2) {
                return;
            }


            BattleTriggerConf bt = new BattleTriggerConf();
            for (int j = 0; j < e.length; j++) {
                String tmpVal = e[j];
                switch (header[j]) {
                    case "id"-> bt.id = tmpVal;
                    case "event"->bt.event = tmpVal;
                    case "cond_relation"-> bt.cond_relation = tmpVal.equals("") ? 0 : Integer.parseInt(tmpVal);
                    case "condition1" ->bt.condition1 = tmpVal;
                    case "cond_param1" ->bt.cond_param1 = tmpVal;
                    case "condition2" -> bt.condition2 = tmpVal;
                    case  "cond_param2" -> bt.cond_param2 = tmpVal;
                }
            }
            triggers.put(bt.id, bt);
        });
        TransitionManager.getInstance().addTriggers(triggers);
        return true;
    }
}
