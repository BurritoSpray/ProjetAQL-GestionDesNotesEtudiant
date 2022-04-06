package ez.clap.gestionetudiant_aql.entities;

import java.util.Objects;

public class Grade {
    private Student student;
    private Course course; // TODO: Check with the teacher if we can really use a Student and Course instead of just a string with the number
    private double grade;


    // TODO: add a constructor with the fractional grade and one in percent
    public Grade(Student student, Course course, double grade){
        this.course = course;
        this.student = student;
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return Double.compare(grade1.getGrade(), getGrade()) == 0 && Objects.equals(getStudent(), grade1.getStudent()) && Objects.equals(getCourse(), grade1.getCourse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudent(), getCourse(), getGrade());
    }
}
