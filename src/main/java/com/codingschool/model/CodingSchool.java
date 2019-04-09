package com.codingschool.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class CodingSchool {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String name;

    @OneToMany(mappedBy = "codingSchool",
        cascade = CascadeType.PERSIST)
    private Set<Course> courses = new HashSet<>();

    public CodingSchool() {
    }

    public CodingSchool(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ITCourse addITCourse(String name, String... technologies) {
        ITCourse itCourse = new ITCourse(name);
        itCourse.setTechnologies(Arrays.asList(technologies));
        itCourse.setCodingSchool(this);
        courses.add(itCourse);
        return itCourse;
    }

    public Course addCourse(String name) {
        Course course = new Course(name);
        courses.add(course);
        course.setCodingSchool(this);
        return course;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nSchool: ");
        stringBuilder.append(name);
        if (courses != null) {
            courses.forEach(stringBuilder::append);
        }
        return stringBuilder.toString();
    }

}
