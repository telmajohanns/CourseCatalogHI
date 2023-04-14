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

    /**
     * Private smiður sem er aðeins kallað í einu sinni því CourseCatalogService er Singleton
     * @param coursedata
     * @throws IOException
     */
    private CourseCatalogService(InputStream coursedata) throws IOException {
        getData(coursedata);
        csInitiated = true;
    }

    public static HashMap<String, List<String>> getExpandableDetailListAll() { return expandableDetailListAll; }

    /**
     * Singleton smiður sem býr til CourseCatalogService eða skila INSTANCE ef hann var
     * þegar til
     * @param coursedata
     * @return INSTANCE
     * @throws IOException
     */
    public static CourseCatalogService getInstance(InputStream coursedata) throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new CourseCatalogService(coursedata);
        }
        return INSTANCE;
    }

    public static boolean isCsInitiated() { return csInitiated; }

    /**
     * Fall sem tekur við Hashmap af filteringum og kallar á öll föll sem þarf
     * @param filterMap Hashmap af fallaköllum og filteringum
     * @param coursedata
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static ArrayList<Course> doFiltering(HashMap<String, ArrayList<String>> filterMap, InputStream coursedata) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        // Endurstilla listann
        if (!allCourses.isEmpty()) { allCourses.clear(); }
        getData(coursedata);
        filteredCatalog = allCourses;

        // Lúppum í gegnum Hashmap-ið og filterum eftir öllu sem var sett inn
        // Key er fallakallið, value eru síurnar sjálfar
        for(Map.Entry<String, ArrayList<String>> eachFilter: filterMap.entrySet()) {
            String key = eachFilter.getKey();
            ArrayList<String> value = eachFilter.getValue();

            // Kalla á rétt föll eftir því hvaða sía var sett á
            if (key.equals("filterByText")) { filteredCatalog = filterByText(value); }
            if (key.equals("filterBySemester")) { filteredCatalog = filterBySemester(value); }
            if (key.equals("filterByEduLevel")) { filteredCatalog = filterByEduLevel(value); }
            if (key.equals("filterByField")) { filteredCatalog = filterByField(value); }
            if (key.equals("filterByDept")) { filteredCatalog = filterByDept(value); }
            if (key.equals("filterByLanguage")) { filteredCatalog = filterByLanguage(value); }
        }
        return filteredCatalog;
    }

    /**
     * Fall sem les kennsluskrárgögn úr course_data.csv skrá
     * @param inputStream
     * @return Hashmap af áföngum til að birta í ExpandableListView
     * @throws IOException
     */
    public static HashMap<String, List<String>> getData(InputStream inputStream) throws IOException {
        //Lesa gögnin úr csv skránni og bæta í Hashmap
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        try {
            while((line = reader.readLine()) != null) {
                Course course;
                List<String> courseDetails = new ArrayList<String>();
                String[] row = line.split(";");

                // Gögn sett í rétta framsetningu
                if (row[3].equals("V")) { row[3] = "Vor"; }
                else if (row[3].equals("H")) { row[3] = "Haust"; }
                else if (row[3].equals("S")) { row[3] = "Sumar"; }

                // Séð til þess að tómir reitir (BLANK) birtist ekki í lista
                for (int i = 2; i<row.length; i++) {
                    if (!row[i].equals("BLANK")) { courseDetails.add(headers[i]+row[i]); }
                }

                //Ef áfangi er kenndur þá er isTaught sett sem true í course hlutnum
                if (row[11].equals("Já")) {
                    course = new Course(row[0], row[1],Double.parseDouble(row[2]),
                            row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10],
                            true,row[12],row[13],row[14],row[15]);
                    allCourses.add(course);
                }
                else if(row[11].equals("Nei")) { //Annars er isTaught sett sem false
                    course = new Course(row[0], row[1],Double.parseDouble(row[2]),
                            row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10],
                            false,row[12],row[13],row[14],row[15]);
                    allCourses.add(course);
                }
                else{ course= null; }

                //Áföngum bætt í Hashmap á réttu sniði til að birta í ExpandableListView
                expandableDetailListAll.put((course.getAcronym() + ": " + course.getTitle()), courseDetails);

            }
        } catch (IOException e) { throw new RuntimeException(e); }
        finally { inputStream.close(); }

        return expandableDetailListAll;
    }

    /**
     * Fall sem skilar gögnunum úr filteredCatalog í Hashmapi
     * Til að stemma við kröfur frá ExpandableListView í Android
     * @return filteredCatalog sem Hashmap
     */
    public static HashMap<String, List<String>> getFilteredData() {

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

    /**
     * Fall sem síar listann á streng úr textareit
     * @param filterList Sía
     * @return Síuðum lista
     */
    public static ArrayList<Course> filterByText(ArrayList<String> filterList) {
        // Bý til temp lista til að geta loop-að í gegnum
        ArrayList<Course> temp = new ArrayList<>();
        for(Course course: filteredCatalog) {
            temp.add(course);
        }

        // Sæki gildið sem var í textareit og loop-a svo í gegnum temp lista
        String filter = filterList.get(0);
        for (Course course: temp) {
            if (!course.getTitle().contains(filter) && !course.getAcronym().contains(filter) &&
                    !course.getTeachers().contains(filter) && !course.getMainTeachers().contains(filter)) {
                // Fjarlægi áfanga ef filter textinn passar ekki við áfangaheiti eða kennara
                filteredCatalog.remove(course);
            }
        }
        return filteredCatalog;
    }

    /**
     * Fall sem síar listann eftir önn, Checkbox
     * @param filter Sía, valdar annir í checkbox
     * @return Síuðum lista
     */
    public static ArrayList<Course> filterBySemester(ArrayList<String> filter) {
        // Bý til temp lista til að geta loop-að í gegnum
        ArrayList<Course> temp = new ArrayList<>();
        for(Course course: filteredCatalog) {
            temp.add(course);
        }

        for(Course course: temp) {
            boolean isSemester = false;
            for (String semester: filter) {
                if (course.getSemester().equals(semester)) { isSemester = true; }
            }
            if (!isSemester) { filteredCatalog.remove(course); }

        }
        return filteredCatalog;
    }

    /**
     * Fall sem síar listann eftir námstigi
     * @param filterList Sía, valið námstig
     * @return Síuðum lista
     */
    public static ArrayList<Course> filterByEduLevel(ArrayList<String> filterList) {
        // Bý til temp lista til að geta loop-að í gegnum
        ArrayList<Course> temp = new ArrayList<>();
        for(Course course: filteredCatalog) {
            temp.add(course);
        }

        String filter = filterList.get(0);
        for(Course course: temp) {
            if (!course.getEduLevel().equals(filter)) { filteredCatalog.remove(course); }
        }
        return filteredCatalog;
    }

    /**
     * Fall sem síar listann eftir Sviði
     * @param filterList Sía, valið svið
     * @return Síuðum lista
     */
    public static ArrayList<Course> filterByField(ArrayList<String> filterList) {
        String filter = filterList.get(0);
        // Default val er öll svið og þá þarf ekki að lúppa í gegnum alla áfanga
        if (filter.equals("Öll svið")) {return filteredCatalog;}

        // Bý til temp lista til að geta loop-að í gegnum
        ArrayList<Course> temp = new ArrayList<>();
        for(Course course: filteredCatalog) {
            temp.add(course);
        }

        System.out.println(filter);
        for(Course course: temp) {
            if (!course.getField().equals(filter)) { filteredCatalog.remove(course); }
        }
        return filteredCatalog;
    }

    /**
     * Fall sem síar listann eftir deild
     * @param filterList Sía, valin deild
     * @return Síuðum lista
     */
    public static ArrayList<Course> filterByDept(ArrayList<String> filterList) {
        String filter = filterList.get(0);
        // Default val er allar deildir og þá þarf ekki að lúppa í gegnum alla áfanga
        if (filter.equals("Allar deildir")) {return filteredCatalog;}

        // Bý til temp lista til að geta loop-að í gegnum
        ArrayList<Course> temp = new ArrayList<>();
        for(Course course: filteredCatalog) {
            temp.add(course);
        }

        for(Course course: temp) {
            if (!course.getDept().equals(filter)) { filteredCatalog.remove(course); }
        }
        return filteredCatalog;
    }

    /**
     * Fall sem síar listann eftir tungumáli
     * @param filter Sía, valin tungumála checkbox
     * @return Síuðum lista
     */
    public static ArrayList<Course> filterByLanguage(ArrayList<String> filter) {
        // Bý til temp lista til að geta loop-að í gegnum
        ArrayList<Course> temp = new ArrayList<>();
        for(Course course: filteredCatalog) {
            temp.add(course);
        }

        for(Course course: temp) {
            boolean isLanguage = false;
            for (String language: filter) {
                if (course.getLanguage().equals(language)) { isLanguage = true; }
            }
            if (!isLanguage) { filteredCatalog.remove(course);}
        }
        return filteredCatalog;
    }

}
