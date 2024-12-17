package model;

import java.sql.Timestamp;

public class Student {
    private int studentId;
    private String name;
    private String gender;
    private int age;
    private String grade;
    private String major;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 构造方法
    public Student(int studentId, String name, String gender, int age, String grade, String major, Timestamp createdAt, Timestamp updatedAt) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.grade = grade;
        this.major = major;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter 和 Setter 方法

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
