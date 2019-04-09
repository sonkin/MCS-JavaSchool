package com.codingschool;

import com.codingschool.model.CodingSchool;
import com.codingschool.model.Student;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;


@Repository
public class CodingSchoolDAO {

    @PersistenceUnit
    EntityManagerFactory emf;


    public void optimisticDemo() {
        EntityManager em = emf.createEntityManager();
        EntityManager em2 = emf.createEntityManager();
        Student student = new Student("Semen");
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Student s1 = em.find(Student.class, student.getId());

        em2.getTransaction().begin();
        Student s2 = em2.find(Student.class, student.getId());
        s2.setName("Ivan");
        em2.getTransaction().commit();
        em2.close();

        s1.setName("Alex");
        try {
            em.getTransaction().commit();
        } catch(RollbackException r) {
            System.out.println(r.getCause().getMessage());
        }


        em.close();


    }


    public void addSchool(String name) {
        EntityManager em = emf.createEntityManager();
        CodingSchool codingSchool =
                new CodingSchool(name);
        em.getTransaction().begin();
        em.persist(codingSchool);
        em.getTransaction().commit();
        em.close();
    }

    public void saveSchool(CodingSchool school) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(school);
        em.getTransaction().commit();
        em.close();
    }

    public CodingSchool getSchool(int id) {
        EntityManager em = emf.createEntityManager();
        CodingSchool codingSchool = em.find(CodingSchool.class, id);
        em.close();
        return codingSchool;
    }

    public void printSchool(int id) {
        EntityManager em = emf.createEntityManager();
        CodingSchool codingSchool = em.find(CodingSchool.class, id);
        System.out.println(codingSchool);
        em.close();
    }

}
