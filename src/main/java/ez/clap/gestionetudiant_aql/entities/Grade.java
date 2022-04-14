package ez.clap.gestionetudiant_aql.entities;

import java.io.Serializable;

public class Grade implements Serializable {
    private final String studentNumber;
    private final String courseNumber;
    private double grade;

    public Grade(String studentNumber, String courseNumber, double grade){
        this.studentNumber = studentNumber;
        this.courseNumber = courseNumber;
        this.grade = grade;
    }

    public String getStudentNumber() {
        return this.studentNumber;
    }

    public String getCourseNumber(){
        return this.courseNumber;
    }

    public double getGrade(){
        return this.grade;
    }

    public void setGrade(double grade) {
        if(grade > 100){
            this.grade = 100;
        }else if(grade < 0){
            this.grade = 0;
        }
    }
}
