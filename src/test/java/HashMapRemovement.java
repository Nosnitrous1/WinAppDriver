import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.*;

public class HashMapRemovement {
    // Let's say we have a HashMap<String, Integer>. 
    // We need to write a method which returns a modified hashmap by removing all the entries 
    // which values are negative.

    @Test
    public void BeginHM(){
        HashMap<String,Integer> start = new HashMap<>();
        start.put("Petr", -3);
        start.put("ggg", 2);
        start.put("Entry", 1);
        start.put("Entry2", 2);
        start.put("Entry3", -2);

        System.out.println(start);
        modiHM(start);
        System.out.println("-----------------");

        System.out.println(start);


    }


    public HashMap<String,Integer> modiHM (HashMap<String,Integer> start){
        List<String> keysRemoveList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : start.entrySet()) {
            if (entry.getValue() < 0) {
                keysRemoveList.add(entry.getKey());
            }
        }
        System.out.println("List = "+keysRemoveList);
        for (int i=0;i<keysRemoveList.size();i++) {
            start.remove(keysRemoveList.get(i));
        }
        return start;
    }
}
