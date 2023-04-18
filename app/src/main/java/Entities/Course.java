package Entities;

public class Course implements Comparable<Course>{

    // Tilviksbreytur
    private String acronym;
    private String title;
    private int ects;
    private String semester;
    private String eduLevel;
    private String field;
    private String dept;
    private String language;
    private String mainTeachers;
    private String teachers;
    private String year;
    private Boolean isTaught;
    private String courseID;
    private String hyperlink;
    private String mandatoryPrereq;
    private String reccomPrereq;

    // Getterar til að sækja eiginleika Course hlutar
    public String getAcronym() {
        return acronym;
    }

    public String getTitle() {
        return title;
    }

    public int getEcts() {
        return ects;
    }

    public String getSemester() {
        return semester;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public String getField() {
        return field;
    }

    public String getDept() {
        return dept;
    }

    public String getLanguage() {
        return language;
    }

    public String getMainTeachers() {
        return mainTeachers;
    }

    public String getTeachers() {
        return teachers;
    }

    public String getYear() {
        return year;
    }

    public Boolean getTaught() {
        return isTaught;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public String getMandatoryPrereq() {
        return mandatoryPrereq;
    }

    public String getReccomPrereq() {
        return reccomPrereq;
    }


    /**
     * Smiður sem býr til Course hlut
     * @param acronym
     * @param title
     * @param ects
     * @param semester
     * @param eduLevel
     * @param field
     * @param dept
     * @param language
     * @param mainTeachers
     * @param teachers
     * @param year
     * @param isTaught
     * @param courseID
     * @param mandatoryPrereq
     * @param reccomPrereq
     * @param hyperlink
     */
    public Course(String acronym, String title, int ects, String semester, String eduLevel,
                  String field, String dept, String language, String mainTeachers, String teachers,
                  String year, Boolean isTaught, String courseID, String mandatoryPrereq,
                  String reccomPrereq, String hyperlink) {
        this.acronym = acronym;
        this.title = title;
        this.ects = ects;
        this.semester = semester;
        this.eduLevel = eduLevel;
        this.field = field;
        this.dept = dept;
        this.language = language;
        this.mainTeachers = mainTeachers;
        this.teachers = teachers;
        this.year = year;
        this.isTaught = isTaught;
        this.courseID = courseID;
        this.mandatoryPrereq = mandatoryPrereq;
        this.reccomPrereq = reccomPrereq;
        this.hyperlink = hyperlink;
    }

    /**
     * Fall til að geta borið saman Course hluti.
     * Ber þá saman eftir fjölda ECTS
     * @param course
     * @return
     */
    @Override
    public int compareTo(Course course) {
        return Integer.compare(this.ects, course.getEcts());
    }
}
