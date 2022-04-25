package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ManageStudentController {
    @FXML
    public Button buttonCancel;
    @FXML
    public Button buttonConfirm;
    @FXML
    public TextField textFieldNumber, textFieldFirstName, textFieldSecondName;
    @FXML
    public ListView<CheckBox> listViewCourse;

    private Student selectedStudent;
    private boolean isNewStudent;
    private MainWindowController mainWindowController;


    private void loadStudent() {
        textFieldFirstName.setText(this.selectedStudent.getFirstName());
        textFieldSecondName.setText(this.selectedStudent.getSecondName());
        textFieldNumber.setText(this.selectedStudent.getStudentID());
    }

    private void setupEvents() {
        this.buttonConfirm.setOnAction(actionEvent -> addStudentIfValid());
        this.buttonCancel.setOnAction(actionEvent -> closeWindow());
    }

    private void loadCourseList() {
        for (Course course : Data.getCourseList()) {
            CheckBox courseTitleCheckBox = new CheckBox();
            courseTitleCheckBox.setText(course.getTitle());
            if (this.selectedStudent.getCourseList().contains(course)) {
                courseTitleCheckBox.setSelected(true);
            }
            listViewCourse.getItems().add(courseTitleCheckBox);
        }
    }

    public void loadData(MainWindowController mainWindowController, boolean newStudent) {
        this.mainWindowController = mainWindowController;
        this.selectedStudent = newStudent ? new Student() : mainWindowController.tableViewStudent.getSelectionModel().getSelectedItem();
        this.isNewStudent = newStudent;
        loadStudent();
        loadCourseList();
        setupEvents();
    }

    private void addStudent() {
        ArrayList<Course> studentCourseList = new ArrayList<>();
        if (!isNewStudent)
            studentCourseList = fixCourseList();
        else {
            for (int i = 0; i < listViewCourse.getItems().size(); i++) {
                if (listViewCourse.getItems().get(i).isSelected()) {
                    Data.getCourseList().get(i).getGradeList().clear();
                    studentCourseList.add(Data.getCourseList().get(i));
                }
            }
        }
        Student student = new Student(this.textFieldFirstName.getText(),
                this.textFieldSecondName.getText(),
                this.textFieldNumber.getText(), studentCourseList);
        Data.getStudentList().remove(selectedStudent);
        Data.getStudentList().add(student);
        this.mainWindowController.tableViewStudent.refresh();
    }

    private ArrayList<Course> fixCourseList() {
        Student tempStudent = this.selectedStudent;
        ArrayList<Course> newCourseList = new ArrayList<>();
        for (int i = 0; i < this.listViewCourse.getItems().size(); i++) {
            Course course = Data.getCourseList().get(i);
            if(this.listViewCourse.getItems().get(i).isSelected()) {
                boolean isAdded = false;
                for (Course studentCourse : tempStudent.getCourseList()) {
                    if (course.getCourseNumber().equals(studentCourse.getCourseNumber()) &&
                            course.getCode().equals(studentCourse.getCode()) &&
                            course.getTitle().equals(studentCourse.getTitle())) {
                        newCourseList.add(studentCourse);
                        tempStudent.getCourseList().remove(studentCourse);
                        isAdded = true;
                        break;
                    }
                }
                if (!isAdded) {
                    newCourseList.add(course);
                }
            }
        }
        return newCourseList;
    }

    private void addStudentIfValid() {
        if (isStudentFieldsValid()) {
            addStudent();
            closeWindow();
        } else {
            this.mainWindowController.showWarningPopup("Erreur", "Information manquante!", "OK");
        }
    }

    private boolean isStudentFieldsValid() {
        return !this.textFieldFirstName.getText().isEmpty() &&
                !this.textFieldSecondName.getText().isEmpty() &&
                !this.textFieldNumber.getText().isEmpty();
    }

    private void closeWindow() {
        ((Stage) this.buttonConfirm.getScene().getWindow()).close();
    }


}
