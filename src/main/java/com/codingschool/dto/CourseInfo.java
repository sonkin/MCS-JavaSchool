package com.codingschool.dto;

public class CourseInfo {
    private String name;
    private int coursesAmount;

    public CourseInfo(String name, int coursesAmount) {
        this.name = name;
        this.coursesAmount = coursesAmount;
    }

    public String getName() {
        return name;
    }

    public int getCoursesAmount() {
        return coursesAmount;
    }
}
