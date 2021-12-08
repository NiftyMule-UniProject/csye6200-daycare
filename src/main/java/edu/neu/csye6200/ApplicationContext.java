package edu.neu.csye6200;

import edu.neu.csye6200.Helper.FileUtils;
import edu.neu.csye6200.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicationContext
{
    private final static String RULES_CSV_FILEPATH = "src/main/resources/rules.txt";
    private final static String STUDENTS_CSV_FILEPATH = "src/main/resources/students.txt";
    private final static String TEACHERS_CSV_FILEPATH = "src/main/resources/teachers.txt";
    private final static String CLASSROOMS_CSV_FILEPATH = "src/main/resources/classrooms.txt";
    private final static String RELATIONS_CSV_FILEPATH = "src/main/resources/relations.txt";

    private List<Student> students;
    private List<Teacher> teachers;
    private List<Classroom> classrooms;

    public ApplicationContext()
    {
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        classrooms = new ArrayList<>();
        init();
    }

    private void init()
    {
        // load student classroom rules
        List<String> rulesCSV = FileUtils.readAllLines(RULES_CSV_FILEPATH);
        assert rulesCSV != null;
        Classroom.loadRules(rulesCSV);

        // load students
        List<String> studentsCSV = FileUtils.readAllLines(STUDENTS_CSV_FILEPATH);
        assert studentsCSV != null;
        StudentFactory studentFactory = StudentFactory.getInstance();
        for (String csv : studentsCSV)
        {
            Student student = (Student) studentFactory.creatObj(csv);
            students.add(student);
        }

        // load teachers
        List<String> teachersCSV = FileUtils.readAllLines(TEACHERS_CSV_FILEPATH);
        assert teachersCSV != null;
        TeacherFactory teacherFactory = TeacherFactory.getInstance();
        for (String csv : teachersCSV)
        {
            Teacher teacher = (Teacher) teacherFactory.creatObj(csv);
            teachers.add(teacher);
        }

        // load classrooms
        List<String> classroomsCSV = FileUtils.readAllLines(CLASSROOMS_CSV_FILEPATH);
        assert classroomsCSV != null;
        for (String csv : classroomsCSV)
        {
            Classroom classroom = Classroom.parseCSV(csv);
            if (classroom != null) classrooms.add(classroom);
        }

        // load relations
        List<String> relationsCSV = FileUtils.readAllLines(RELATIONS_CSV_FILEPATH);
        assert relationsCSV != null;
        createRelations(relationsCSV);
    }

    private void createRelations(List<String> relationCSV)
    {
        // Format: "[relationType ('student'/'teacher')],[Person],[PersonToBeAssigned]"

        for (String csv : relationCSV)
        {
            try
            {
                Scanner scanner = new Scanner(csv);
                scanner.useDelimiter(",");
                String type = scanner.next();
                if (type.equals("student"))
                {
                    String student = scanner.next();
                    String teacher = scanner.next();
                    Student studentObj = findStudentByName(student);
                    Teacher teacherObj = findTeacherByName(teacher);
                    if (studentObj == null || teacherObj == null)
                        throw new Exception("Student or teacher not found");
                    if (!studentObj.assignTeacher(teacherObj))
                        throw new Exception("Teacher's student list full or age group incorrect");
                }
                else if (type.equals("teacher"))
                {
                    String teacher = scanner.next();
                    String classroom = scanner.next();
                    Teacher teacherObj = findTeacherByName(teacher);
                    Classroom classroomObj = findClassroomByName(classroom);
                    if (teacherObj == null || classroomObj == null)
                        throw new Exception("Teacher or classroom not found");
                    if (!teacherObj.assignClassroom(classroomObj))
                        throw new Exception("Classroom is full or age group incorrect");
                }
                else
                {
                    throw new Exception("Relation type error");
                }
            } catch (Exception e)
            {
                System.err.printf("Cannot initialize relation - [%s], ", csv);
                System.err.println(e.getMessage());
            }
        }
    }

    public Student findStudentByName(String name)
    {
        for (Student student : students)
        {
            if (student.getName().equals(name)) return student;
        }
        return null;
    }

    public Teacher findTeacherByName(String name)
    {
        for (Teacher teacher : teachers)
        {
            if (teacher.getName().equals(name)) return teacher;
        }
        return null;
    }

    public Classroom findClassroomByName(String name)
    {
        for (Classroom classroom : classrooms)
        {
            if (classroom.getName().equals(name)) return classroom;
        }
        return null;
    }

    public void updateDB()
    {
        // update students
        List<String> studentsCSV = new ArrayList<>();
        for (Student student : students) studentsCSV.add(student.toCSV());
        FileUtils.writeToFile(STUDENTS_CSV_FILEPATH, studentsCSV);

        // update teachers
        List<String> teachersCSV = new ArrayList<>();
        for (Teacher teacher : teachers) teachersCSV.add(teacher.toCSV());
        FileUtils.writeToFile(TEACHERS_CSV_FILEPATH, teachersCSV);

        // update classrooms
        List<String> classroomsCSV = new ArrayList<>();
        for (Classroom room : classrooms) classroomsCSV.add(room.toCSV());
        FileUtils.writeToFile(CLASSROOMS_CSV_FILEPATH, classroomsCSV);

        // update relations
        List<String> relations = generateRelations();
        FileUtils.writeToFile(RELATIONS_CSV_FILEPATH, relations);
    }

    private List<String> generateRelations()
    {
        List<String> relations = new ArrayList<>();
        for (Student student : students)
        {
            if (student.getAssignedTeacher() != null)
            {
                String relation = String.format("student,%s,%s",
                        student.getName(),
                        student.getAssignedTeacher().getName());
                relations.add(relation);
            }
        }

        for (Teacher teacher : teachers)
        {
            if (teacher.getClassroom() != null)
            {
                String relation = String.format("teacher,%s,%s",
                        teacher.getName(),
                        teacher.getClassroom().getName());
                relations.add(relation);
            }
        }
        return relations;
    }

    @Override
    public String toString()
    {
        return "ApplicationContext{" +
                "students=" + students +
                ", teachers=" + teachers +
                ", classrooms=" + classrooms +
                '}';
    }

    // ========= setters and getters ==========

    public List<Student> getStudents()
    {
        return students;
    }

    public void setStudents(List<Student> students)
    {
        this.students = students;
    }

    public List<Teacher> getTeachers()
    {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers)
    {
        this.teachers = teachers;
    }

    public List<Classroom> getClassrooms()
    {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms)
    {
        this.classrooms = classrooms;
    }
}
