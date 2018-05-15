package com.andy.player;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("ceshi");
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("5");
        for (String a : list) {
            System.out.println(a.toString());
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        Map map = new HashMap();
        map.put("a1", "1");
        map.put("a2", "2");
        map.put("a3", "3");
        map.put("a4", "4");
        map.put("a5", "5");
        Set set = map.entrySet();
        Iterator it2 = set.iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            System.out.println("key=" + entry.getKey());
            System.out.println("value=" + entry.getValue());
        }

    }
}
