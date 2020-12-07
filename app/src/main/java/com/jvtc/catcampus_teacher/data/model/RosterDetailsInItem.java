package com.jvtc.catcampus_teacher.data.model;

public class RosterDetailsInItem {
    private String id;
    private String college;
    private String professional;
    private String grade;
    private String bj;
    private String studentid;
    private String name;
    private String sex;

    public RosterDetailsInItem(){

    }

    public RosterDetailsInItem(String id, String college, String professional, String grade, String bj, String studentid, String name, String sex) {
        this.id = id;
        this.college = college;
        this.professional = professional;
        this.grade = grade;
        this.bj = bj;
        this.studentid = studentid;
        this.name = name;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBj() {
        return bj;
    }

    public void setBj(String bj) {
        this.bj = bj;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
