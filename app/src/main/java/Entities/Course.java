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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getEcts() {
        return ects;
    }

    public void setEcts(Double ects) {
        this.ects = ects;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMainTeachers() {
        return mainTeachers;
    }

    public void setMainTeachers(String mainTeachers) {
        this.mainTeachers = mainTeachers;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Boolean getTaught() {
        return isTaught;
    }

    public void setTaught(Boolean taught) {
        isTaught = taught;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public String getMandatoryPrereq() {
        return mandatoryPrereq;
    }

    public void setMandatoryPrereq(String mandatoryPrereq) {
        this.mandatoryPrereq = mandatoryPrereq;
    }

    public String getReccomPrereq() {
        return reccomPrereq;
    }

    public void setReccomPrereq(String reccomPrereq) {
        this.reccomPrereq = reccomPrereq;
    }

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
