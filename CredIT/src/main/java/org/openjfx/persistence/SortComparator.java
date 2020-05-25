package org.openjfx.persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class SortComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        ArrayList<String> list1 = new ArrayList<>();
        String[] stringList = o1.split("\t");
        for (String s : stringList) {
            list1.add(s);
        }
        String[] dateList = list1.get(list1.size()-1).split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateList[2]), Integer.parseInt(dateList[1]), Integer.parseInt(dateList[0]));

        ArrayList<String> list2 = new ArrayList<>();
        String[] stringList2 = o2.split("\t");
        for (String s : stringList2) {
            list2.add(s);
        }
        String[] dateList2 = list2.get(list1.size()-1).split("-");
        LocalDate date2 = LocalDate.of(Integer.parseInt(dateList2[2]), Integer.parseInt(dateList2[1]), Integer.parseInt(dateList2[0]));
        return -date.compareTo(date2);
    }
}
