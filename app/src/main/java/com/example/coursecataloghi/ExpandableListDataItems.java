package com.example.coursecataloghi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entities.Course;

public class ExpandableListDataItems {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableDetailList = new HashMap<String, List<String>>();

        for (int i = 0; i < 3; i++) {
            List<String> course = new ArrayList<String>();
            Course coursex = new Course("TÖL" + String.valueOf(i),
                    "Tölvunarfræði " + String.valueOf(i), 6.0,
                    "Vor", "Grunnnám", "VON", "IVT",
                    "IS", "Ebba Þóra", "Steinunn María",
                    "2022-2023", true, "1234", "linkur");
            course.add(coursex.getEcts().toString());
            course.add(coursex.getSemester());
            course.add(coursex.getField());
            course.add(coursex.getDept());
            course.add(coursex.getLanguage());
            course.add(coursex.getMainTeachers());
            course.add(coursex.getTeachers());
            course.add(coursex.getYear());
            course.add(coursex.getHyperlink());
            expandableDetailList.put(coursex.getTitle(), course);
        }

        return expandableDetailList;
    }
}
