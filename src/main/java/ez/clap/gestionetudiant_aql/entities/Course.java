package ez.clap.gestionetudiant_aql.entities;

import java.util.Objects;

public class Course {
    private String courseNumber;
    private String code;
    private String title;

    public Course(String title, String code, String courseNumber){
        this.title = title;
        this.code = code;
        this.courseNumber = courseNumber;
    }


    // Getters
    public String getCourseNumber() {return this.courseNumber;}

    public String getCode() {return this.code;}

    public String getTitle() {return  this.title;}

    // Setters
    public void setCourseNumber(String courseNumber){
        this.courseNumber = courseNumber;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setTitle(String title){
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseNumber, course.courseNumber) && Objects.equals(code, course.code) && Objects.equals(title, course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseNumber, code, title);
    }
}
