package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.Dto.LessonDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CourseBO;
import lk.ijse.bo.custom.InstructorBO;
import lk.ijse.bo.custom.LessonBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.Dto.tm.LessonTm;

import java.net.URL;
import java.util.Optional;
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
        lblid.setText(lessonBO.generateLessonId());
        txtLessonDate.clear();
        txtdurtion.clear();
        cmbCourseId.getSelectionModel().clearSelection();
        cmbInsId.getSelectionModel().clearSelection();
        cmbStudentId.getSelectionModel().clearSelection();

    }

    public void btnDeleteLessonOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this lesson?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Lesson");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {

            try {
                boolean isDeleted = lessonBO.deleteLesson(lblid.getText());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Lesson deleted successfully!").show();
                    loadTableData();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the lesson!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnUpdateLessonOnAction(ActionEvent actionEvent) {
        String id = lblid.getText();
        String studentId = cmbStudentId.getValue().toString();
        String courseId = cmbCourseId.getValue().toString();
        String instructorId = cmbInsId.getValue().toString();
        String lessonDate = txtLessonDate.getText();
        Integer duration = Integer.valueOf(txtdurtion.getText());

        LessonDto dto = new LessonDto(
                id,
                studentId,
                courseId,
                instructorId,
                lessonDate,
                duration
        );
        try {
            boolean isUpdated = lessonBO.updateLesson(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Course Updated!").show();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update the Course!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveLessonOnAction(ActionEvent actionEvent) {
        try{
            String newId = lessonBO.generateLessonId();
            lblid.setText(newId);
            String studentId = cmbStudentId.getValue().toString();
            String courseId = cmbCourseId.getValue().toString();
            String instructorId = cmbInsId.getValue().toString();
            String lessonDate = txtLessonDate.getText();
            Integer duration = Integer.valueOf(txtdurtion.getText());

            LessonDto dto = new LessonDto(
                    newId,
                    studentId,
                    courseId,
                    instructorId,
                    lessonDate,
                    duration
            );

            lessonBO.saveLesson(dto);
            new Alert(Alert.AlertType.INFORMATION, "Lesson Saved!").show();
            loadTableData();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the Lesson!").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        LessonTm selectedItem = LessonTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblid.setText(selectedItem.getLessonId());
            txtLessonDate.setText(selectedItem.getLessonDate());
            txtdurtion.setText(String.valueOf(selectedItem.getDuration()));
            cmbStudentId.setValue(selectedItem.getStudentId());
            cmbCourseId.setValue(selectedItem.getCourseId());
            cmbInsId.setValue(selectedItem.getInstructorId());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("lessonId"));
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
            loadTableData();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            e.printStackTrace();
        }
    }

    private void loadTableData() throws Exception {
        LessonTable.setItems(FXCollections.observableArrayList(
                lessonBO.getAllLessons().stream().map(lessonDto -> new LessonTm(
                       lessonDto.getLessonId(),
                        lessonDto.getStudentId(),
                        lessonDto.getCourseId(),
                        lessonDto.getInstructorId(),
                        lessonDto.getLessonDate(),
                        lessonDto.getDuration()
                )).toList()
        ));
    }
}
