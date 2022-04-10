package ez.clap.gestionetudiant_aql.utilities;

import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    private static ArrayList<Student> studentList = new ArrayList<>();

    private static ArrayList<Course> courseList = new ArrayList<>();

    private static File mainFile = validateFolder("./Data");

    private static File studentFile = new File(mainFile.getPath() + "/student.txt");

    private static File courseFile = new File(mainFile.getPath() + "/course.txt");




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

    public static void loadDataFromFiles() throws FileNotFoundException {
        // File dir  = new File("");

        //Verifier si le dossier data existe:
        //Si il existe pas, crée les sous dossiers:
        File mainFile = validateFile("./Data");

       File studentFile = validateFile(mainFile.getPath() + "/Student");
       File courseFile = validateFile(mainFile.getPath() + "/Course");
       File gradeFile = validateFile(mainFile.getPath() + "/Grade");

       File[] testFile = studentFile.listFiles();
       for (int i = 0; i < testFile.length; i++) {
           ArrayList<String> studentFileContent = readFile(testFile[i]);
           // ArrayList<Course> CoursesInStudent = new ArrayList<Course>();
           Student newStudent = new Student(studentFileContent.get(1), studentFileContent.get(2), studentFileContent.get(0));
           // for (int j = 3; j < studentFileContent.size(); j++) {
           studentList.add(newStudent);
           //}
           System.out.println(newStudent);
           System.out.println(studentList);

           // Crée un objet student et check pour tous les paramètres dedans le file et les ajouter dans l'objet étudiant.
           // Ensuite ajouter objet étudiant dans le arraylist student list.
       }


       //Chargé le contenu de chacun des dossiers un par un.
        // Chaque dossier, vérifier le nom de chaque fichier dedans.
    }

    public static void saveDataToFiles(){
        writeCourseToFile();
        writeStudentToFile();
    }
}
