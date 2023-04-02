package com.example.coursecataloghi.Services;

import com.example.coursecataloghi.Activities.CourseCatalogActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.Course;

public class CourseCatalogService {
    private static CourseCatalogService INSTANCE;

    // singleton pattern?
    private static ArrayList<Course> filteredCatalog = new ArrayList<Course>();

    public static ArrayList<Course> allCourses = new ArrayList<Course>();
    private static final String[] headers = {"Acronym: ", "Title: ", "ECTS: ", "Semester: ",
            "Level: ", "Field: ", "Department: ",
            "Language: ", "Supervising teacher(s): ", "Teachers: ", "Year: ",
            "Is taught: ", "Course ID: ", "Mandatory Prerequisites: ", "Recommended Prerequisites: ",
            "Further details: "};

    private CourseCatalogService(InputStream coursedata) throws IOException {
        getData(coursedata);
    }

    public static CourseCatalogService getInstance(InputStream coursedata) throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new CourseCatalogService(coursedata);
        }
        return INSTANCE;
    }
    //aðal fall sem sér um að applya filteringunum
    // þar kalla á föllin fyrir neðan
    //switch td
    //catalog serviceinu
    //hashmap
    //dofiltering, tekur inn hashmap af filteringum sem á að framkvæma
    //lykill er nafnið, filterbyfield, valueið er filteringin sjálf, VON
    // sækja gildið og applya filteringunni
    //value væri strengur eða arraylisti af strengjum
    // reseta listann í byrjun á fallinu

    // SEtja hann í catalogservice

    //Spurja Sigga: hashmap, dofiltering fallið
    /*public static ArrayList<Course> doFiltering(HashMap<String, ArrayList<String>> filterMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        filteredCatalog = allCourses;
        for(Map.Entry<String, ArrayList<String>> eachFilter: filterMap.entrySet()) {
            String key = eachFilter.getKey();
            ArrayList<String> value = eachFilter.getValue();
            //Object obj = new Object();
            //Method method = INSTANCE.getClass().getMethod(key, ArrayList<String>);
            //method.invoke(method, value);
            Method method = CourseCatalogService.class.getMethod(key, ArrayList.class);
            method.invoke(INSTANCE, value);
            //method(value);
            //Fyrirlestur 7
            //switch, lykill er key úr hashmap

        }

        //reseta filteredCatalog listann
        //for lykkju til að forlúppa í gegnum fallið
        // fyrsta hlutinn í array listanum
        return filteredCatalog;
    }*/

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
                    filteredCatalog.add(course);

                }
                else { //Annars er isTaught sett sem false
                    course = new Course(row[0], row[1],Double.parseDouble(row[2]),
                            row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10],
                            false,row[12],row[13],row[14],row[15]);
                    allCourses.add(course);
                    filteredCatalog.add(course);
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


    public ArrayList<Course> filterByText(String filter) {
        for (Course course: filteredCatalog) {
            if (!course.getTitle().contains(filter) && !course.getAcronym().contains(filter) &&
                !course.getTeachers().contains(filter) && !course.getMainTeachers().contains(filter)) {
                filteredCatalog.remove(course);
            }
        }
        return filteredCatalog;
    }
    public ArrayList<Course> filterBySemester(ArrayList<String> filter) {
        boolean isSemester = false;
        for(Course course: filteredCatalog) {
            for (String semester: filter) {
                if (course.getSemester().equals(semester)) {
                    isSemester = true;
                }
            }
            if (!isSemester) { filteredCatalog.remove(course);}

        }
        return filteredCatalog;
    }

    public ArrayList<Course> filterByEduLevel(String filter) {
        for(Course course: filteredCatalog) {
            if (!course.getEduLevel().equals(filter)) {
                filteredCatalog.remove(course);
            }
        }
        return filteredCatalog;
    }
    public ArrayList<Course> filterByField(String filter) {
        for(Course course: filteredCatalog) {
            if (!course.getField().equals(filter)) {
                filteredCatalog.remove(course);
            }
        }
        return filteredCatalog;
    }
    public ArrayList<Course> filterByDept(String filter) {
        for(Course course: filteredCatalog) {
            if (!course.getDept().equals(filter)) {
                filteredCatalog.remove(course);
            }
        }
        return filteredCatalog;
    }
    public ArrayList<Course> filterByLanguage(ArrayList<String> filter) {
        boolean isLanguage = false;
        for(Course course: filteredCatalog) {
            for (String language: filter) {
                if (course.getLanguage().equals(language)) {
                    isLanguage = true;
                }
            }
            if (!isLanguage) { filteredCatalog.remove(course);}

        }
        return filteredCatalog;
    }
}
