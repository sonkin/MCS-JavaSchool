package com.codingschool.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name="findCoursesNoStudents",
        query="SELECT c.name " +
                "FROM Course c " +
                "WHERE c.students IS EMPTY")
public class Course {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String name;

    public Course() {
    }



    @ManyToOne
    private CodingSchool codingSchool;

    @OneToMany(mappedBy = "course",
            cascade = CascadeType.ALL)
    private List<Student> students;

    public Course(String name) {
        this.name = name;
    }
    public Student addStudent(String name) {
        if (students == null) {
            students = new ArrayList<>();
        }
        Student student = new Student(name);
        students.add(student);
        student.setCourse(this);
        return student;
    }

    public CodingSchool getCodingSchool() {
        return codingSchool;
    }

    public void setCodingSchool(CodingSchool codingSchool) {
        this.codingSchool = codingSchool;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n  Course: ");
        stringBuilder.append(name);
        stringBuilder.append("  id="+id);

        if (students != null) {
            students.forEach(stringBuilder::append);
        }
        return stringBuilder.toString();
    }
}
