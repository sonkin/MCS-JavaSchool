package com.codingschool;

import com.codingschool.dto.CourseInfo;
import com.codingschool.model.CodingSchool;
import com.codingschool.model.Course;
import com.codingschool.model.Student;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;


@Repository
@Transactional
public class CodingSchool2DAO {

    @PersistenceContext
    EntityManager em;


    @Transactional(readOnly = true)
    public List<Student> findStudentsByCourse(String course) {
        return em.createQuery("select s from Student s " +
                "where s.course.name = ?1")
                .setParameter(1, course)
                .getResultList();
    }

    public List<Student> findStudentsByCourse1(String course) {
        return em.createQuery("select s from Student s " +
                "where s.course.name = :course")
                .setParameter("course", course)
                .getResultList();
    }

    public List<String> findStudentCourses(String student) {
        return em.createQuery(
                "select c.name from Course c " +
                "join c.students s " +
                "where s.name=?1")
                .setParameter(1, student)
                .getResultList();
    }


    public List<Object[]> findCourseStudents() {
        return em.createQuery("select c.name, c.students from Course c ")
                .getResultList();
    }


    public List<Object[]> findCourseStudentsAmount() {
        return em.createQuery(
                "select c.name, size(c.students) " +
                "from Course c " +
                "group by c.name ")
                .getResultList();
    }

    public List<CourseInfo>
        findCourseStudentsAmount2() {
        return em.createQuery(
        "select new com.codingschool.dto.CourseInfo(" +
                "c.name, size(c.students)) " +
                "from Course c " +
                "group by c.name ")
        .getResultList();
    }


    public List<String>
        findStudentsByCourseLike(String course) {
        return em.createQuery(
                "select s.name from Student s " +
                "where s.course.name like ?1")
                .setParameter(1, course)
                .getResultList();
    }

    public List<String> findCoursesHavingStudent(String student) {
        return em.createQuery(
                "select c.name from Course c " +
                "where :student in " +
                "(select name from c.students) ")
                .setParameter("student", student)
                .getResultList();
    }

    public List<String> findCoursesHavingStudent2(String student) {
        return em.createQuery(
                "select c.name from Course c " +
                "JOIN c.students s " +
                "where :student in s.name")
                .setParameter("student", student)
                .getResultList();
    }

    public List<Student> findStudentsByTechnology(String technology) {
        return em.createQuery(
                "select s from Student s join " +
                "treat(s.course as ITCourse) c " +
                "where :technology member of c.technologies ")
                .setParameter("technology", technology)
                .getResultList();
    }

    public List<String> findCourseByTechnology(String technology) {
        return em.createQuery(
                "select c.name from Course c " +
                        "where :technology " +
                        "member of c.technologies ")
                .setParameter("technology", technology)
                .getResultList();
    }

    public List<String> findCourseByTechnologyFetch(String technology) {
        return em.createQuery(
                "select c.name from Course c " +
                        "join fetch c.students " +
                        "where :technology " +
                        "member of c.technologies ")
                .setParameter("technology", technology)
                .getResultList();
    }


    public List<Object> getNamedQuery(String query) {
        return em.createNamedQuery(query)
                .getResultList();
    }

    public List<Course> findAllCourses() {
        return em.createQuery("select c from Course c " +
                "join fetch c.students")
                .getResultList();
    }


    public void addSchool(String name) {
        CodingSchool codingSchool =
                new CodingSchool(name);
        em.persist(codingSchool);
    }

    public void saveSchool(CodingSchool school) {
        em.persist(school);
    }
    public void updateSchool(CodingSchool school) {
        em.merge(school);
        school.setName("Kiev Coding School");
    }


    public CodingSchool getSchool(int id) {
        return em.find(CodingSchool.class, id);
    }

    public void printSchool(int id) {
        CodingSchool codingSchool =
                em.find(CodingSchool.class, id);
        codingSchool.setName("Newest");
        System.out.println(codingSchool);
    }

    public void removeCoursesWithoutStudents() {
        em.createQuery(
            "delete from Course c " +
            "where c.students is empty ")
            .executeUpdate();
    }

    public void setAllITCourseAsActual() {
        em.createQuery(
                "update ITCourse c " +
                        "set c.actual=true ")
                .executeUpdate();
    }
}
