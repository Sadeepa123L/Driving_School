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
    public ComboBox<String> cmbStudentId;
    public ComboBox<String> cmbCourseId;
    public ComboBox<String> cmbInsId;

    public Button btnReset;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;

    public TableView<LessonTm> LessonTable;
    public TableColumn<LessonTm, String> colId;
    public TableColumn<LessonTm, String> colDate;
    public TableColumn<LessonTm, String> colduration;
    public TableColumn<LessonTm, String> colSid;
    public TableColumn<LessonTm, String> colIid;
    public TableColumn<LessonTm, String> colCid;

    LessonBO lessonBO = (LessonBO) BOFactory.getBO(BOFactory.BOType.LESSON);
    InstructorBO instructorBO = (InstructorBO) BOFactory.getBO(BOFactory.BOType.INSTRUCTOR);
    CourseBO courseBO = (CourseBO) BOFactory.getBO(BOFactory.BOType.COURSE);
    StudentBO studentBO = (StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);

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
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            e.printStackTrace();
        }
    }

    // ---------- VALIDATION ----------
    private boolean validateInputs() {
        if (cmbStudentId.getValue() == null) {
            showAlert("Please select a Student ID!");
            return false;
        }
        if (cmbCourseId.getValue() == null) {
            showAlert("Please select a Course ID!");
            return false;
        }
        if (cmbInsId.getValue() == null) {
            showAlert("Please select an Instructor ID!");
            return false;
        }
        if (txtLessonDate.getText().trim().isEmpty()) {
            showAlert("Lesson Date cannot be empty!");
            return false;
        }
        if (!txtLessonDate.getText().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            showAlert("Lesson Date must be in YYYY-MM-DD format!");
            return false;
        }
        if (txtdurtion.getText().trim().isEmpty()) {
            showAlert("Duration cannot be empty!");
            return false;
        }
        if (!txtdurtion.getText().matches("\\d+")) {
            showAlert("Duration must be a number!");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.WARNING, message, ButtonType.OK).showAndWait();
    }

    private void clearFields() {
        lblid.setText(lessonBO.generateLessonId());
        txtLessonDate.clear();
        txtdurtion.clear();
        cmbCourseId.getSelectionModel().clearSelection();
        cmbInsId.getSelectionModel().clearSelection();
        cmbStudentId.getSelectionModel().clearSelection();
    }

    // ---------- BUTTON ACTIONS ----------
    public void btnResetPageOnAction(ActionEvent actionEvent) {
        clearFields();
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
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the lesson!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnUpdateLessonOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        LessonDto dto = new LessonDto(
                lblid.getText(),
                cmbStudentId.getValue(),
                cmbCourseId.getValue(),
                cmbInsId.getValue(),
                txtLessonDate.getText(),
                Integer.parseInt(txtdurtion.getText())
        );
        try {
            boolean isUpdated = lessonBO.updateLesson(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Lesson Updated!").show();
                loadTableData();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update the Lesson!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveLessonOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        try {
            String newId = lessonBO.generateLessonId();
            lblid.setText(newId);

            LessonDto dto = new LessonDto(
                    newId,
                    cmbStudentId.getValue(),
                    cmbCourseId.getValue(),
                    cmbInsId.getValue(),
                    txtLessonDate.getText(),
                    Integer.parseInt(txtdurtion.getText())
            );

            boolean isSaved = lessonBO.saveLesson(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Lesson Saved!").show();
                loadTableData();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the Lesson!").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong while saving!").show();
            e.printStackTrace();
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
