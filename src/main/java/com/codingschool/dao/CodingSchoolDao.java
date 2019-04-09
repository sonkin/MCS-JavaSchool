package com.codingschool.dao;

import com.codingschool.model.CodingSchool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodingSchoolDao extends
        JpaRepository<CodingSchool, Integer> {
}
