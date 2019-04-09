package com.codingschool;

import com.codingschool.dao.CodingSchoolDao;
import com.codingschool.dao.CourseDao;
import com.codingschool.model.CodingSchool;
import com.codingschool.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

@SpringBootApplication
public class SpringDataApp
        implements CommandLineRunner
{

    @Autowired
    CourseDao courseDao;

    @Autowired
    CodingSchoolDao codingSchoolDao;

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(SpringDataApp.class,
                        args);
    }

    public void run(String... strings) throws Exception {
        Course course = new Course("Java");
        CodingSchool codingSchool = new CodingSchool("JCS");
        codingSchoolDao.save(codingSchool);

        course.setCodingSchool(codingSchool);
        courseDao.save(course);

        Course course2 = new Course("Java advanced");
        course2.setCodingSchool(codingSchool);
        courseDao.save(course2);

        List<Course> courses =
                courseDao.findByCodingSchoolNameOrderByNameDesc("JCS");
        courses.forEach(c-> System.out.println("course: "+c.getName()));

        courseDao.saveAll(Arrays.asList(
                new Course("JavaScript"),
                new Course("Spring"),
                new Course("Spring Data")
        ));

        System.out.println("==All courses");
        List<Course> all = courseDao.findAll();
        all.stream()
                .map(Course::getName)
                .forEach(System.out::println);

        System.out.println("==Count of courses");
        System.out.println(courseDao.count());

        System.out.println("==Page 2");
        Page<Course> allPage2 = courseDao.findAll(
                PageRequest.of(1, 2));
        allPage2.stream()
                .map(Course::getName)
                .forEach(System.out::println);

        System.out.println("==Find by example: Java course");
        List<Course> javaCourses =
                courseDao.findAll(
                        Example.of(new Course("Java"),
                    ExampleMatcher.matchingAny()
                        .withMatcher("name", startsWith())));
        javaCourses.forEach(c-> System.out.println(c.getName()));

        System.out.println("==Courses sorted by name");
        List<Course> coursesSorted =
                courseDao.findAll(Sort.by("name"));
        coursesSorted.stream()
                .map(Course::getName)
                .forEach(System.out::println);

        System.out.println("==Course found by name:");
        Course spring = courseDao.findByName("Spring");
        System.out.println(spring.getName());

        System.out.println("==Course with ids 1 and 2:");
        courseDao.findByIdIsIn(1,2,3,4)
            .forEach(c->System.out.println(c.getName()));
        System.out.println("ending ing: ");
        courseDao.findByNameLike("%ing")
                .forEach(c->System.out.println(c.getName()));;

        System.out.println("==courses like Java:");
        int coursesAmount = courseDao.findCoursesAmountLikeNative("Java%");
        System.out.println(coursesAmount);

    }
}
