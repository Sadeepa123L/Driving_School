package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CourseBO;
import lk.ijse.bo.custom.InstructorBO;
import lk.ijse.bo.custom.LessonBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.tm.LessonTm;

import java.net.URL;
import java.util.ResourceBundle;

public class LessonFormController implements Initializable {
    public Label lblid;
    public TextField txtLessonDate;
    public TextField txtdurtion;
    public ComboBox cmbStudentId;
    public ComboBox cmbCourseId;
    public ComboBox cmbInsId;

    public Button btnReset;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;

    public TableView<LessonTm> LessonTable;
    public TableColumn<LessonTm, String > colId;
    public TableColumn<LessonTm, String> colDate;
    public TableColumn<LessonTm, String > colduration;
    public TableColumn<LessonTm, String > colSid;
    public TableColumn<LessonTm, String> colIid;
    public TableColumn<LessonTm, String> colCid;

    LessonBO lessonBO = (LessonBO) BOFactory.getBO(BOFactory.BOType.LESSON);
    InstructorBO instructorBO = (InstructorBO) BOFactory.getBO(BOFactory.BOType.INSTRUCTOR);
    CourseBO courseBO = (CourseBO) BOFactory.getBO(BOFactory.BOType.COURSE);
    StudentBO studentBO = (StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);

    public void btnResetPageOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteLessonOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateLessonOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveLessonOnAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCid.setCellValueFactory(new PropertyValueFactory<>("lessonId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        colduration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colSid.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colIid.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colCid.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        lblid.setText(lessonBO.generateLessonId());

        try {
            cmbCourseId.setItems(FXCollections.observableArrayList(courseBO.getAllCourseIds()));
            cmbInsId.setItems(FXCollections.observableArrayList(instructorBO.getAllInstructorIds()));
            cmbStudentId.setItems(FXCollections.observableArrayList(studentBO.getAllStudentIds()));
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            e.printStackTrace();
        }
    }
}
