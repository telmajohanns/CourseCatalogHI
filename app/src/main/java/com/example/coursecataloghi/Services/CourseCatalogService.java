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
    private static boolean csInitiated = false;
    private static CourseCatalogService INSTANCE;

    private static ArrayList<Course> filteredCatalog = new ArrayList<Course>();

    public static ArrayList<Course> allCourses = new ArrayList<Course>();
    public static HashMap<String, List<String>> expandableDetailListAll = new HashMap<String, List<String>>();
    private static final String[] headers = {"Acronym: ", "Title: ", "ECTS: ", "Semester: ",
            "Level: ", "Field: ", "Department: ",
            "Language: ", "Supervising teacher(s): ", "Teachers: ", "Year: ",
            "Is taught: ", "Course ID: ", "Mandatory Prerequisites: ", "Recommended Prerequisites: ",
            "Further details: "};

    private CourseCatalogService(InputStream coursedata) throws IOException {
        getData(coursedata);
        csInitiated = true;
    }

    public static HashMap<String, List<String>> getExpandableDetailListAll() {
        return expandableDetailListAll;
    }

    public static CourseCatalogService getInstance(InputStream coursedata) throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new CourseCatalogService(coursedata);
        }
        return INSTANCE;
    }

    public static void setFilteredCatalog(ArrayList<Course> filteredCatalogList) {
        System.out.println("Ætti að vera annað");
        //filteredCatalog = filteredCatalogList;
        for(Course course: filteredCatalog) {
            System.out.println(course.getAcronym());
        }
    }

    public static boolean isCsInitiated() {
        return csInitiated;
    }

    public static boolean isInitiated() {
        return INSTANCE != null;
    }


    public static ArrayList<Course> doFiltering(HashMap<String, ArrayList<String>> filterMap, InputStream coursedata) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        System.out.println("Byrja dofiltering fall");
        if (!allCourses.isEmpty()) {
            allCourses.clear();
        }

        getData(coursedata);

        ArrayList<Course> temp = allCourses;
        //filteredCatalog.clear();
        filteredCatalog = temp;
        for (Course course : allCourses) {
            System.out.println("ALL: " + course.getAcronym());
        }
        for(Map.Entry<String, ArrayList<String>> eachFilter: filterMap.entrySet()) {
            String key = eachFilter.getKey();
            ArrayList<String> value = eachFilter.getValue();
            System.out.println("value: " + value.get(0));
            if (key.equals("filterByText")) {
                filteredCatalog = filterByText(value);
            }
            if (key.equals("filterBySemester")) {
                filteredCatalog = filterBySemester(value);
            }
            if (key.equals("filterByEduLevel")) {
                filteredCatalog = filterByEduLevel(value);
            }
            if (key.equals("filterByField")) {
                filteredCatalog = filterByField(value);
            }
            if (key.equals("filterByDept")) {
                filteredCatalog = filterByDept(value);
            }
            if (key.equals("filterByLanguage")) {
                filteredCatalog = filterByLanguage(value);
            }
            /*Method method = CourseCatalogService.class.getMethod(key, ArrayList.class);
            try {
                System.out.println("Kalla á filter methods með invoke");
                method.invoke(INSTANCE, value);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
            }*/
        }
        return filteredCatalog;
    }

    public static HashMap<String, List<String>> getData(InputStream inputStream) throws IOException {

        //if(!allCourses.isEmpty()) {allCourses.clear();}
        System.out.println("getData fallið");
        //HashMap<String, List<String>> expandableDetailList = new HashMap<String, List<String>>();
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
                    //filteredCatalog.add(course);
                } else if(row[11].equals("Nei")) { //Annars er isTaught sett sem false
                    course = new Course(row[0], row[1],Double.parseDouble(row[2]),
                            row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10],
                            false,row[12],row[13],row[14],row[15]);
                    allCourses.add(course);
                    //filteredCatalog.add(course);
                }
                else{
                    course= null;
                }
                //expandableDetailList.put((course.getAcronym() + ": " + course.getTitle()), courseDetails);
                expandableDetailListAll.put((course.getAcronym() + ": " + course.getTitle()), courseDetails);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            inputStream.close();
        }
        //filteredCatalog = allCourses;
        return expandableDetailListAll;
    }

    public static HashMap<String, List<String>> getFilteredData() {
        System.out.println("Ætti að vera síðast - getFiltered Data fallið");

        HashMap<String, List<String>> expandableDetailList = new HashMap<String, List<String>>();

        for (Course course: filteredCatalog) {
            List<String> courseDetails = new ArrayList<>();
            String[] courseString = {
                    course.getAcronym(), course.getTitle(), course.getEcts().toString(),
                    course.getSemester(), course.getEduLevel(), course.getField(),
                    course.getDept(), course.getLanguage(), course.getMainTeachers(),
                    course.getTeachers(), course.getYear(), course.getTaught().toString(),
                    course.getCourseID(), course.getMandatoryPrereq(), course.getReccomPrereq(),
                    course.getHyperlink()
            };
            for (int i = 0; i < courseString.length; i++) {
                if (!courseString[i].equals("BLANK")) {
                    courseDetails.add(headers[i]+courseString[i]);
                }
            }

            expandableDetailList.put(course.getAcronym() + ": " + course.getTitle(), courseDetails);
        }
        return expandableDetailList;
    }
//Debugga

    public static ArrayList<Course> filterByText(ArrayList<String> filterList) {
        String filter = filterList.get(0);
        if (filter.equals("Áfangi eða kennari")) {return filteredCatalog;}
        ArrayList<Course> temp = new ArrayList<>();

        for(Course course: filteredCatalog) {
            temp.add(course);
        }
        for (Course course: temp) {
            if (!course.getTitle().contains(filter) && !course.getAcronym().contains(filter) &&
                    !course.getTeachers().contains(filter) && !course.getMainTeachers().contains(filter)) {
                filteredCatalog.remove(course);
            }
        }
        return filteredCatalog;
    }

    public static ArrayList<Course> filterBySemester(ArrayList<String> filter) {
        ArrayList<Course> temp = new ArrayList<>();

        for(Course course: filteredCatalog) {
            temp.add(course);
        }

        for(Course course: temp) {
            boolean isSemester = false;
            for (String semester: filter) {
                if (course.getSemester().equals(semester)) {
                    isSemester = true;
                }
            }
            if (!isSemester) {
                filteredCatalog.remove(course);

            }

        }
        return filteredCatalog;
    }

    public static ArrayList<Course> filterByEduLevel(ArrayList<String> filterList) {
        String filter = filterList.get(0);
        if (filter.equals("Öll námstig")) {return filteredCatalog;}
        ArrayList<Course> temp = new ArrayList<>();

        for(Course course: filteredCatalog) {
            temp.add(course);
        }

        for(Course course: temp) {
            if (!course.getEduLevel().equals(filter)) {
                filteredCatalog.remove(course);
            }
        }
        return filteredCatalog;
    }
    public static ArrayList<Course> filterByField(ArrayList<String> filterList) {
        String filter = filterList.get(0);
        if (filter.equals("Öll svið")) {return filteredCatalog;}
        ArrayList<Course> temp = new ArrayList<>();

        for(Course course: filteredCatalog) {
            temp.add(course);
        }
        System.out.println(filter);
        for(Course course: temp) {
            if (!course.getField().equals(filter)) {
                filteredCatalog.remove(course);
            }
        }
        return filteredCatalog;
    }
    public static ArrayList<Course> filterByDept(ArrayList<String> filterList) {
        String filter = filterList.get(0);
        if (filter.equals("Allar deildir")) {return filteredCatalog;}
        ArrayList<Course> temp = new ArrayList<>();

        for(Course course: filteredCatalog) {
            temp.add(course);
        }

        for(Course course: temp) {
            if (!course.getDept().equals(filter)) {
                filteredCatalog.remove(course);
            }
        }
        return filteredCatalog;
    }
    public static ArrayList<Course> filterByLanguage(ArrayList<String> filter) {
        ArrayList<Course> temp = new ArrayList<>();

        for(Course course: filteredCatalog) {
            temp.add(course);
        }

        for(Course course: temp) {
            boolean isLanguage = false;
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
