package Entities;

public class Course {

    private String acronym;
    private String title;
    private Double ects;
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
    private String mandatoryPrereq = "";
    private String reccomPrereq = "";

    public Course(String acronym, String title, Double ects, String semester, String eduLevel, String field, String dept, String language, String mainTeachers, String teachers, String year, Boolean isTaught, String courseID, String hyperlink, String mandatoryPrereq, String reccomPrereq) {
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
        this.hyperlink = hyperlink;
        this.mandatoryPrereq = mandatoryPrereq;
        this.reccomPrereq = reccomPrereq;
    }

    public Course(String acronym, String title, Double ects, String semester, String eduLevel, String field, String dept, String language, String mainTeachers, String teachers, String year, Boolean isTaught, String courseID, String hyperlink) {
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
        this.hyperlink = hyperlink;
    }
}
