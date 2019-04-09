package com.codingschool.model;

import javax.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Version
    private Integer version;

    private String name;

    @ManyToOne
    private Course course;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "\n    Student: "+name;
    }
}
