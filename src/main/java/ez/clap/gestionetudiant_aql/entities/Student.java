package ez.clap.gestionetudiant_aql.entities;

import java.util.Objects;

public class Student {
    private String studentID;
    private String firstName;
    private String secondName;

    public Student(String firstName, String secondName, String studentID){
        this.studentID = studentID;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    // Getters
    public String getStudentID(){
        return this.studentID;
    }

    public String getFirstName(){
        return this.studentID;
    }

    public String getSecondName(){
        return this.secondName;
    }


    // Setters
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID='" + studentID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return getStudentID().equals(student.getStudentID()) &&
                getFirstName().equals(student.getFirstName()) &&
                getSecondName().equals(student.getSecondName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentID(), getFirstName(), getSecondName());
    }
}
