package com.codingschool.dao;

import com.codingschool.model.Course;
import com.codingschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface StudentsDao
        extends JpaRepository<Student, Integer> {

    Student findByName(@Param("name") String name);
}
