package ez.clap.gestionetudiant_aql.entities;

import javafx.scene.control.ComboBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Student implements Serializable {
    private String studentID;
    private String firstName;
    private String secondName;
    private ArrayList<Course> courseList;

    public Student (){
        this("","","");
    }

    public Student(String firstName, String secondName, String studentID){
        this.studentID = studentID;
        this.firstName = firstName;
        this.secondName = secondName;
        this.courseList = new ArrayList<>();
    }

    public Student(String firstName, String secondName, String studentID, ArrayList<Course> courseList){
        this(firstName, secondName, studentID);
        this.courseList = courseList;
    }

    // Getters
    public String getStudentID(){
        return this.studentID;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getSecondName(){
        return this.secondName;
    }

    public ArrayList<Course> getCourseList(){return this.courseList;}

    public ComboBox<String> getCourseListAsComboBox(){
        ComboBox<String> courseListComboBox = new ComboBox<>();
        for(Course course : courseList){
            courseListComboBox.getItems().add(course.getTitle());
        }
        if(this.courseList.size() == 0) {
            courseListComboBox.getItems().add("Aucun Cours");
        }

        courseListComboBox.getSelectionModel().selectFirst();
        courseListComboBox.setPrefWidth(140);

        return courseListComboBox;
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Student student = (Student) object;
        return getStudentID().equals(student.getStudentID()) &&
                getFirstName().equals(student.getFirstName()) &&
                getSecondName().equals(student.getSecondName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentID(), getFirstName(), getSecondName());
    }
}
