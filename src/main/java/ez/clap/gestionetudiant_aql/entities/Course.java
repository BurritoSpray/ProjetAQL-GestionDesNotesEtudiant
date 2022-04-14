package ez.clap.gestionetudiant_aql.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Course implements Serializable {
    private String courseNumber;
    private String code;
    private String title;
    private ArrayList<Grade> gradeList = new ArrayList<>();

    public Course(){
        this("", "", "");
    }
    public Course(String title, String code, String courseNumber){
        this.title = title;
        this.code = code;
        this.courseNumber = courseNumber;
    }

    public Course(String title, String code, String courseNumber, ArrayList<Grade> grades){
        this(title, code, courseNumber);
        this.gradeList = grades;
    }


    // Getters
    public String getCourseNumber() {return this.courseNumber;}

    public String getCode() {return this.code;}

    public String getTitle() {return  this.title;}

    public ArrayList<Grade> getGradeList() {
        return this.gradeList;
    }

    public double getAverageInPercent(){
        double average = 0;
        for(Grade grade : this.gradeList){
            average += grade.getGradeInPercent();
        }
        return this.gradeList.isEmpty() ? 0 : average/this.gradeList.size();
    }

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

    public void setGradeList(ArrayList<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public void addGrade(Grade grade){
        this.gradeList.add(grade);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseNumber='" + courseNumber + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                '}';
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
