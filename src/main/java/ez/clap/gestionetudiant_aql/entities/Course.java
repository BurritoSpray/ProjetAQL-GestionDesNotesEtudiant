package ez.clap.gestionetudiant_aql.entities;

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
}
