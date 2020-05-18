package org.openjfx.persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class SortComparator implements Comparator {


    @Override
    public int compare(Object o1, Object o2) {
        ArrayList<String> list1 = new ArrayList<>();
        String[] stringList = o1.toString().split("\t");
        for(String s : stringList){
            list1.add(s);
        }
         String[] dateList = list1.get(4).split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateList[2]),Integer.parseInt(dateList[1]),Integer.parseInt(dateList[0]));

        //
        ArrayList<String> list2 = new ArrayList<>();
        String[] stringList2 = o1.toString().split("\t");
        for(String s : stringList2){
            list2.add(s);
        }
        String[] dateList2 = list2.get(4).split("-");
        LocalDate date2 = LocalDate.of(Integer.parseInt(dateList2[2]),Integer.parseInt(dateList2[1]),Integer.parseInt(dateList2[0]));
        return date.compareTo(date2);
    }
}
