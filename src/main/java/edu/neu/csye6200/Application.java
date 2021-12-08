package edu.neu.csye6200;

import edu.neu.csye6200.Model.Classroom;
import edu.neu.csye6200.Model.Student;
import edu.neu.csye6200.Model.Teacher;

import java.time.LocalDate;

public class Application
{
    public static void main(String[] args)
    {
        ApplicationContext context = new ApplicationContext();

        Classroom room3 = new Classroom("classroom3", 1);
        context.getClassrooms().add(room3);
        Teacher teacher3 = new Teacher("teacher3", 1, LocalDate.parse("2020-01-01"));
        context.getTeachers().add(teacher3);
        Student student3 = new Student("student3", 16, "parentName3", "parentAddr3", "777777777", LocalDate.parse("2019-02-02"));
        context.getStudents().add(student3);
        student3.assignTeacher(teacher3);
        teacher3.assignClassroom(room3);
        context.updateDB();
    }
}
