package com.codingschool.dao;

import com.codingschool.model.CodingSchool;
import com.codingschool.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseDao
        extends JpaRepository<Course, Integer> {

    Course findByName(String name);

    List<Course> findByCodingSchoolNameOrderByNameDesc(String name);

    List<Course> findByIdIsIn(int...ids);

    List<Course> findByNameLike(String s);

    @Query("select count(c) from Course c where c.name like :name")
    int findCoursesAmountLike(@Param("name") String name);

    @Query(value = "select count(*) from Course where name like ?1"
        ,nativeQuery = true)
    int findCoursesAmountLikeNative(String name);

    void removeAllByNameContainingAndCodingSchoolName(String name, String codingSchool);
}
