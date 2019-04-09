package com.codingschool.model;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class ITCourse extends Course {

    public ITCourse(String name) {
        super(name);
    }

    public ITCourse() {
    }

    private boolean actual;

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    @ElementCollection
    private List<String> technologies;

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }

    @Override
    public String toString() {
        return super.toString() +
                " technologies: " +
                technologies.stream().collect(Collectors.joining(", "));
    }
}
