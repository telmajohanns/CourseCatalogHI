package com.example.coursecataloghi;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entities.Course;


public class ExpandableListDataItems {
    public static ArrayList<Course> allCourses = new ArrayList<Course>();
    private static final String[] headers = {"Acronym: ", "Title: ", "ECTS: ", "Semester: ", "Level: ", "Field: ", "Department: ",
            "Language: ", "Supervising teacher(s): ", "Teachers: ", "Year: ",
            "Is taught: ", "Course ID: ", "Mandatory Prerequisites: ", "Recommended Prerequisites: ",
            "Further details: "};

    public static HashMap<String, List<String>> getData(InputStream inputStream) throws IOException {
        HashMap<String, List<String>> expandableDetailList = new HashMap<String, List<String>>();
        //Lesa gögnin úr csv skránni og bæta í Hashmap
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        try {
            while((line = reader.readLine()) != null) {
                Course course;
                List<String> courseDetails = new ArrayList<String>();
                String[] row = line.split(";");
                if (row[3].equals("V")) {
                    row[3] = "Vor";
                }
                else if (row[3].equals("H")) {
                    row[3] = "Haust";
                }
                else if (row[3].equals("S")) {
                    row[3] = "Sumar";
                }
                for (int i = 2; i<row.length; i++) {
                    if (!row[i].equals("BLANK")) {
                        courseDetails.add(headers[i]+row[i]);
                    }
                }
                //Ef áfangi er kenndur þá er isTaught sett sem true
                if (row[11].equals("Já")) {
                    course = new Course(row[0], row[1],Double.parseDouble(row[2]),
                            row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10],
                            true,row[12],row[13],row[14],row[15]);
                    allCourses.add(course);
                }
                else { //Annars er isTaught sett sem false
                    course = new Course(row[0], row[1],Double.parseDouble(row[2]),
                            row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10],
                            false,row[12],row[13],row[14],row[15]);
                    allCourses.add(course);
                }

                expandableDetailList.put((course.getAcronym() + ": " + course.getTitle()), courseDetails);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            inputStream.close();
        }
        return expandableDetailList;
    }
}
