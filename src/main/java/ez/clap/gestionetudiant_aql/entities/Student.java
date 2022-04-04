package ez.clap.gestionetudiant_aql.entities;

import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import java.util.Objects;

public class Student {
    private String studentID;
    private String firstName;
    private String secondName;
    private ArrayList<Course> courseList;


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
        ComboBox<String> combobox = new ComboBox<>();
        for(Course course : courseList){
            combobox.getItems().add(course.getTitle());
        }
        if(this.courseList.size() == 0) {
            combobox.getItems().add("Aucun Cours");
        }

        combobox.getSelectionModel().selectFirst();
        combobox.setPrefWidth(140);

        return combobox;
    }

//    // TODO: Verifier la fonction
//    public ArrayList<ComboBox<Course>> getCourseListAsComboBox(){
//        ArrayList<ComboBox<Course>> comboBoxArrayList = new ArrayList<>();
//        comboBoxArrayList.add(new ComboBox<>((ObservableList<Course>) courseList));
//        return comboBoxArrayList;
//    }


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
