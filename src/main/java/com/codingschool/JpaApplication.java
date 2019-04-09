package com.codingschool;

import com.codingschool.dto.CourseInfo;
import com.codingschool.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class JpaApplication
//		implements CommandLineRunner
{
	@Autowired
	CodingSchoolDAO schoolDAO1;

	@Autowired
	CodingSchool2DAO schoolDAO;

	@Bean
	public CodingSchoolDAO schoolDAO() {
		return new CodingSchoolDAO();
	}

	@Bean
	public CodingSchool2DAO school2DAO() {
		return new CodingSchool2DAO();
	}

	public void run(String... args) throws Exception {
		schoolDAO1.optimisticDemo();

		CodingSchool school =
				new CodingSchool("Moscow Coding School");
		Course course = school.addCourse("Spring");
		Student student = course.addStudent("Petr");
		Student student2 = course.addStudent("Diana");
		Student student3 = course.addStudent("Ivan");

		Course useless = school.addITCourse("useless");

		ITCourse jpa = school.addITCourse("JPA", "JPA", "Hibernate", "DAO");
		jpa.addStudent("Petr");

		Course jee = school.addITCourse("JEE", "JPA", "EJB");
		jee.addStudent("Julia");

		Course js = school.addITCourse("JavaScript", "Angular", "React");
		js.addStudent("Semen");

		schoolDAO.saveSchool(school);

		school.setName("SPB Coding School");
		schoolDAO.updateSchool(school);

		schoolDAO.addSchool("London Coding School");
		CodingSchool codingSchool = schoolDAO.getSchool(1);
		//System.out.println(codingSchool);
		schoolDAO.printSchool(school.getId());
		System.out.println("succeed");

		jpql();


	}

	public static void main(String[] args) {
		ApplicationContext applicationContext =
				SpringApplication.run(JpaApplication.class, args);
	}

	// JPQL
	public void jpql() {
		schoolDAO.removeCoursesWithoutStudents();
		schoolDAO.setAllITCourseAsActual();

		System.out.println("Students of Spring course");
		List<Student> students = schoolDAO.findStudentsByCourse("Spring");
		students.forEach(System.out::println);

		System.out.println("Courses and students amount");
		List<Object[]> courseStudentsAmount = schoolDAO.findCourseStudentsAmount();
		for (Object[] c: courseStudentsAmount) {
			System.out.println(c[0] + " "+ c[1]);
		}

		System.out.println("Courses and students amount");
		List<CourseInfo> courseStudentsAmount2 = schoolDAO.findCourseStudentsAmount2();
		for (CourseInfo c: courseStudentsAmount2) {
			System.out.println(c.getName()+"  "+
					c.getCoursesAmount());
		}

		System.out.println("Students attending courses J...");
		List<String> students1 = schoolDAO.findStudentsByCourseLike("J%");
		System.out.println(students1.stream().collect(Collectors.joining(", ")));

		System.out.println("Courses having Petr");
		List<String> courses = schoolDAO.findCoursesHavingStudent("Petr");
		courses.forEach(System.out::println);

		System.out.println("Students knowing JPA technology");
		List<Student> students2 = schoolDAO.findStudentsByTechnology("JPA");
		students2.forEach(System.out::println);

		System.out.println("All courses where Petr is attended");
		List<String> courses2 = schoolDAO.findStudentCourses("Petr");
		courses2.forEach(System.out::println);

		System.out.println("All courses with no students");
		List<Object> noStudents = schoolDAO.getNamedQuery("findCoursesNoStudents");
		noStudents.forEach(System.out::println);

		System.out.println("All courses learning JPA technology");
		List<String> jpaCourses = schoolDAO.findCourseByTechnology("JPA");
		jpaCourses.forEach(System.out::println);
		for(String c: jpaCourses) {
			System.out.println(c);
		}
		System.out.println("All courses");
		List<Course> allCourses = schoolDAO.findAllCourses();
		for (Course c : allCourses) {
			System.out.println(c.getName());
			for (Student s: c.getStudents()) {
				System.out.println("- "+s.getName());
			}
		}



	}
}

