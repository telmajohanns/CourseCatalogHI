package com.example.coursecataloghi.Services;

import com.example.coursecataloghi.Activities.CourseCatalogActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.Course;

public class CourseCatalogService {
    // boolean check sem segir til um hvort CourseCatalogService sé til (Singleton)
    private static boolean csInitiated = false;
    // Singleton Instance af CourseCatalogService
    private static CourseCatalogService INSTANCE;
    // ArrayList sem er notaður við síun
    private static ArrayList<Course> filteredCatalog = new ArrayList<Course>();
    // ArrayList sem inniheldur alla áfanga í kennsluskránni
    public static ArrayList<Course> allCourses = new ArrayList<Course>();
    // Hashmap með öllum áföngum á réttu sniði fyrir ExpandableListView
    public static HashMap<String, List<String>> expandableDetailListAll = new HashMap<String, List<String>>();
    // Tímabundin lausn til að birta skýringu á eiginleikum áfanga í ExpandableListView
    private static final String[] headers = {"Áfangi: ", "Áfangaheiti: ", "ECTS: ", "Önn: ",
            "Námstig: ", "Svið: ", "Deild: ",
            "Tungumál: ", "Umsjón: ", "Kennarar: ", "Kennsluár: ",
            "Kennt: ", "Námskeiðsnúmer: ", "Nauðsynlegar forkröfur: ", "Æskilegar forkröfur: ",
            "Frekari upplýsingar um áfangann: "};

    public static ArrayList<Course> getAllCourses() {
        return allCourses;
    }

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
     * @throws IOException
     */
    public static ArrayList<Course> doFiltering(HashMap<String, ArrayList<String>> filterMap, InputStream coursedata) throws IOException {
        // Endurstilla listann
        if (!allCourses.isEmpty()) { allCourses.clear(); }
        getData(coursedata);
        ArrayList<Course> temp = allCourses;
        filteredCatalog = temp;

        // Lúppum í gegnum Hashmap-ið og filterum eftir öllu sem var sett inn
        // Key er fallakallið, value eru síurnar sjálfar
        for(Map.Entry<String, ArrayList<String>> eachFilter: filterMap.entrySet()) {
            String key = eachFilter.getKey();
            ArrayList<String> value = eachFilter.getValue();
            if (key.equals("filterByFavorites")) {filteredCatalog = filterByFavorites(value); }
            else {
                // Kalla á rétt föll eftir því hvaða sía var sett á
                if (key.equals("filterByText")) { filteredCatalog = filterByText(value); }
                if (key.equals("filterBySemester")) { filteredCatalog = filterBySemester(value); }
                if (key.equals("filterByEduLevel")) { filteredCatalog = filterByEduLevel(value); }
                if (key.equals("filterByField")) { filteredCatalog = filterByField(value); }
                if (key.equals("filterByDept")) { filteredCatalog = filterByDept(value); }
                if (key.equals("filterByLanguage")) { filteredCatalog = filterByLanguage(value);}

            }
            if (key.equals("sortByECTS")) { filteredCatalog = sortByECTS(value); }
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
                    course = new Course(row[0], row[1],Integer.parseInt(row[2]),
                            row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10],
                            true,row[12],row[13],row[14],row[15]);
                    allCourses.add(course);
                }
                else if(row[11].equals("Nei")) { //Annars er isTaught sett sem false
                    course = new Course(row[0], row[1],Integer.parseInt(row[2]),
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
            if (course.getTaught()) {
                String[] courseString = {
                        course.getAcronym(), course.getTitle(), String.valueOf(course.getEcts()),
                        course.getSemester(), course.getEduLevel(), course.getField(),
                        course.getDept(), course.getLanguage(), course.getMainTeachers(),
                        course.getTeachers(), course.getYear(), "Já",
                        course.getCourseID(), course.getMandatoryPrereq(), course.getReccomPrereq(),
                        course.getHyperlink()
                };
                for (int i = 2; i < courseString.length; i++) {
                    if (!courseString[i].equals("BLANK")) {
                        courseDetails.add(headers[i]+courseString[i]);
                    }
                }
            }
            else {
                String[] courseString = {
                        course.getAcronym(), course.getTitle(), String.valueOf(course.getEcts()),
                        course.getSemester(), course.getEduLevel(), course.getField(),
                        course.getDept(), course.getLanguage(), course.getMainTeachers(),
                        course.getTeachers(), course.getYear(), "Nei",
                        course.getCourseID(), course.getMandatoryPrereq(), course.getReccomPrereq(),
                        course.getHyperlink()
                };
                for (int i = 2; i < courseString.length; i++) {
                    if (!courseString[i].equals("BLANK")) {
                        courseDetails.add(headers[i]+courseString[i]);
                    }
                }
            }

            expandableDetailList.put(course.getAcronym() + ": " + course.getTitle(), courseDetails);
        }
        return expandableDetailList;
    }

    public static ArrayList<Course> filterByFavorites(ArrayList<String> favoritesList) {
        // Bý til tóman lista til að bæta áföngum í sem passa við filtera
        ArrayList<Course> temp = new ArrayList<>();
        for(Course course: filteredCatalog) {
            for (String acro: favoritesList) {
                if (course.getAcronym().equals(acro)) { temp.add(course); }
            }
        }
        return temp;
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
        // Bý til tóman lista til að bæta áföngum í sem passa við filtera
        ArrayList<Course> temp = new ArrayList<>();
        for(Course course: filteredCatalog) {
            for (String semester: filter) {
                if (course.getSemester().equals(semester)) { temp.add(course); }
            }
        }
        return temp;
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
     * Fall sem raðar listanum eftir fjölda ECTS, annað hvort í hækkandi eða lækkandi röð
     * eftir hvað notandinn valdi
     * @param sortList Val notandans, hvort það væri í hækkandi eða lækkandi röð
     * @return
     */
    public static ArrayList<Course> sortByECTS(ArrayList<String> sortList) {
        String sort = sortList.get(0);
        if (sort.equals("Í hækkandi röð")) { Collections.sort(filteredCatalog); }
        else if (sort.equals("Í lækkandi röð")) { filteredCatalog.sort(Comparator.reverseOrder()); }

        return filteredCatalog;

    }

    /**
     * Fall sem síar listann eftir tungumáli
     * @param filter Sía, valin tungumála checkbox
     * @return Síuðum lista
     */
    public static ArrayList<Course> filterByLanguage(ArrayList<String> filter) {
        // Bý til tóman lista til að bæta áföngum í sem passa við filtera
        ArrayList<Course> temp = new ArrayList<>();
        for(Course course: filteredCatalog) {
            for (String language: filter) {
                if (course.getLanguage().equals(language)) { temp.add(course); }
            }
        }
        return temp;
    }

}
