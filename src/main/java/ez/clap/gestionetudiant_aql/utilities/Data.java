package ez.clap.gestionetudiant_aql.utilities;

import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Student;

import java.util.ArrayList;

public class Data {
    private static ArrayList<Student> studentList = new ArrayList<>();

    private static ArrayList<Course> courseList = new ArrayList<>();

    public static ArrayList<Student> getStudentList(){
        return Data.studentList;
    }

    public static ArrayList<Course> getCourseList(){
        return Data.courseList;
    }

    public static void setStudentList(ArrayList<Student> studentList){
        Data.studentList = studentList;
    }

    public static void setCourseList(ArrayList<Course> courseList){
        Data.courseList = courseList;
    }

    public static boolean loadDataFromFiles(){
        return true;
    }

    public static void saveDataFromFiles(){

    }
}
