package edu.neu.csye6200.Controller;

import edu.neu.csye6200.Model.Classroom;
import edu.neu.csye6200.Model.DayCare;
import edu.neu.csye6200.Model.Student;
import edu.neu.csye6200.Model.Teacher;
import edu.neu.csye6200.View.StudentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.List;

public class StudentController
{
    private final DayCare model;
    private final StudentView view;

    public StudentController(DayCare model, StudentView view)
    {
        this.model = model;
        this.view = view;
        updateStudentTable();
        setEditable(false);
    }

    private void updateStudentTable()
    {
        DefaultTableModel model = (DefaultTableModel) view.tblStudents.getModel();
        model.setRowCount(0);
        List<Student> students = this.model.getStudents();
        if (!students.isEmpty())
        {
            for (Student s : students)
            {
                Object[] row = new Object[4];
                row[0] = s.getName();
                row[1] = s.getAge();
                row[2] = s.getAssignedTeacher().getName();
                row[3] = s.getAssignedTeacher().getClassroom().getName();
                model.addRow(row);
            }
        }
    }

    public void enableAddStudent()
    {
        setEditable(true);
        for (Teacher teacher : model.getTeachers())
            view.teacherGroup.addItem(teacher.getName() + "-Capacity:" + String.valueOf(teacher.getCapacity()));
    }

    public void addStudent()
    {
        String name = view.txtName.getText();
        String ageText = view.txtAge.getText();
        if (!ageText.matches("[0-9]*"))
        {
            printError("Invalid age!");
            return;
        }
        int age = Integer.parseInt(ageText);
        String parentName = view.txtParName.getText();
        String parentNum = view.txtParNum.getText();
        if (!parentNum.matches("[0-9]*"))
        {
            printError("Invalid phone number!");
            return;
        }
        String address = view.txtAddress.getText();

        Student student = new Student(name, age, parentName, parentNum, address, LocalDate.now());

        if (!model.addStudent(student))
        {
            printError("Student already exists!");
            return;
        }

        String teacherField = (String) view.teacherGroup.getSelectedItem();
        if (teacherField == null)
        {
            printError("Invalid teacher!");
            return;
        }
        String teacherName = teacherField.substring(0, teacherField.indexOf("-"));
        Teacher teacher = model.findTeacherByName(teacherName);
        if (Classroom.getAgeGroupByAge(age) != teacher.getAgeGroup())
        {
            printError("Teacher's responsible age group incorrect!");
            return;
        }
        student.assignTeacher(teacher);
        updateStudentTable();
        clearInput();
        setEditable(false);
        model.updateDB();
    }

    public void deleteStudent()
    {
        int selectedRow = view.tblStudents.getSelectedRow();
        String studentName = (String) view.tblStudents.getModel().getValueAt(selectedRow, 0);
        model.deleteStudentByName(studentName);
        updateStudentTable();
        model.updateDB();
    }

    public void updateClassroom()
    {
        String teacherField = (String) view.teacherGroup.getSelectedItem();
        if (teacherField == null || teacherField.isEmpty())
        {
            view.txtClassroom.setText("");
            return;
        }
        String teacherName = teacherField.substring(0, teacherField.indexOf("-"));
        if (model.findTeacherByName(teacherName).getClassroom() == null)
            view.txtClassroom.setText("");
        else
            view.txtClassroom.setText(model.findTeacherByName(teacherName).getClassroom().getName());
    }

    public void setEditable(Boolean n){
        view.txtAddress.setEditable(n);
        view.txtAge.setEditable(n);
        view.txtParName.setEditable(n);
        view.txtParNum.setEditable(n);
        view.txtName.setEditable(n);
        view.teacherGroup.setEnabled(n);
        view.btnConfirm.setEnabled(n);
    }

    public void clearInput()
    {
        view.txtAddress.setText("");
        view.txtAge.setText("");
        view.txtParName.setText("");
        view.txtParNum.setText("");
        view.txtName.setText("");
        view.teacherGroup.removeAllItems();
    }

    private void printError(String errMsg)
    {
        JOptionPane.showMessageDialog(view,
                errMsg,
                "Error!",
                JOptionPane.ERROR_MESSAGE);
    }

    private void printSuccMsg(String msg)
    {
        JOptionPane.showMessageDialog(view,
                msg,
                "Success!",
                JOptionPane.PLAIN_MESSAGE);
    }
}
