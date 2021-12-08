package edu.neu.csye6200.Model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Formatter;
import java.util.List;

public class Student extends Person
{
    private int age;
    private String parentName;
    private String parentAddress;
    private String parentPhoneNum;
    private LocalDate registrationDate;
    private Teacher assignedTeacher;
    private List<Immunization> immunizations;

    public Student(String name, int age, String parentName, String parentAddress, String parentPhoneNum, LocalDate registrationDate)
    {
        super(name);
        this.parentName = parentName;
        this.parentAddress = parentAddress;
        this.parentPhoneNum = parentPhoneNum;
        this.registrationDate = registrationDate;
        this.age = age;
    }

    public String toCSV()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("%s,%s,%s,%s,%s,%s",
                this.name,
                this.age,
                this.parentName,
                this.parentAddress,
                this.parentPhoneNum,
                this.registrationDate.format(formatter));
    }

    public boolean assignTeacher(Teacher teacher)
    {
        if (teacher.getNumOfStudents() < Teacher.getAgeGroupRules().get(teacher.getAgeGroup()) &&
            Classroom.getAgeGroupByAge(this.age) == teacher.getAgeGroup())
        {
            setAssignedTeacher(teacher);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        return "Student{" +
                "parentName='" + parentName + '\'' +
                ", parentAddress='" + parentAddress + '\'' +
                ", parentPhoneNum='" + parentPhoneNum + '\'' +
                ", registrationDate=" + registrationDate +
                ", assignedTeacher=" + assignedTeacher +
                ", immunizations=" + immunizations +
                ", age=" + age +
                '}';
    }

    // ========= setters and getters ==========

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public String getParentAddress()
    {
        return parentAddress;
    }

    public void setParentAddress(String parentAddress)
    {
        this.parentAddress = parentAddress;
    }

    public String getParentPhoneNum()
    {
        return parentPhoneNum;
    }

    public void setParentPhoneNum(String parentPhoneNum)
    {
        this.parentPhoneNum = parentPhoneNum;
    }

    public LocalDate getRegistrationDate()
    {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public Teacher getAssignedTeacher()
    {
        return assignedTeacher;
    }

    public void setAssignedTeacher(Teacher assignedTeacher)
    {
        this.assignedTeacher = assignedTeacher;
    }

    public List<Immunization> getImmunizations()
    {
        return immunizations;
    }

    public void setImmunizations(List<Immunization> immunizations)
    {
        this.immunizations = immunizations;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }
}