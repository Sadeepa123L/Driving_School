package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.Dto.CourseDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CourseBO;
import lk.ijse.bo.custom.InstructorBO;
import lk.ijse.Dto.tm.CourseTm;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CourseFormController implements Initializable {

    public TextField txtName;
    public TextField txtdurtion;
    public TextField txtfee;

    public TableView<CourseTm> ProgramTable;
    public TableColumn<CourseTm, String> colId;
    public TableColumn<CourseTm, String> colName;
    public TableColumn<CourseTm, Integer> colduration;
    public TableColumn<CourseTm, Double> colfee;

    public Label lblid;
    public ComboBox<String> cmbInsId;
    public TableColumn<CourseTm, String> colinId;

    CourseBO courseBO = (CourseBO) BOFactory.getBO(BOFactory.BOType.COURSE);
    InstructorBO instructorBO = (InstructorBO) BOFactory.getBO(BOFactory.BOType.INSTRUCTOR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colduration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colfee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colinId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));

        lblid.setText(courseBO.generateProgramId());

        try {
            LoadTableData();
            cmbInsId.setItems(FXCollections.observableArrayList(instructorBO.getAllInstructorIds()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateInputs() {
        if (txtName.getText().trim().isEmpty()) {
            showAlert("Course name cannot be empty!");
            return false;
        }
        if (txtdurtion.getText().trim().isEmpty()) {
            showAlert("Duration cannot be empty!");
            return false;
        }
        if (!txtdurtion.getText().matches("\\d+")) {
            showAlert("Duration must be a valid number!");
            return false;
        }
        if (txtfee.getText().trim().isEmpty()) {
            showAlert("Fee cannot be empty!");
            return false;
        }
        if (!txtfee.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert("Fee must be a valid number!");
            return false;
        }
        if (cmbInsId.getValue() == null) {
            showAlert("Please select an Instructor ID!");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.WARNING, message, ButtonType.OK).showAndWait();
    }

    public void btnSaveProgramOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        try {
            String newId = courseBO.generateProgramId();
            lblid.setText(newId);

            CourseDto dto = new CourseDto(
                    newId,
                    txtName.getText(),
                    Integer.parseInt(txtdurtion.getText()),
                    Double.parseDouble(txtfee.getText()),
                    cmbInsId.getValue()
            );

            boolean isSaved = courseBO.saveProgram(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved").showAndWait();
                LoadTableData();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save course!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        CourseTm selectedItem = ProgramTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblid.setText(selectedItem.getProgramId());
            txtName.setText(selectedItem.getProgramName());
            txtdurtion.setText(String.valueOf(selectedItem.getDuration()));
            txtfee.setText(String.valueOf(selectedItem.getFee()));
            cmbInsId.setValue(selectedItem.getInstructorId());
        }
    }

    public void btnResetPageOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnDeleteProgramOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this course?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Course");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = courseBO.deleteProgram(lblid.getText());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Course deleted successfully!").show();
                    LoadTableData();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the course!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnUpdateProgramOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        CourseDto dto = new CourseDto(
                lblid.getText(),
                txtName.getText(),
                Integer.parseInt(txtdurtion.getText()),
                Double.parseDouble(txtfee.getText()),
                cmbInsId.getValue()
        );
        try {
            boolean isUpdated = courseBO.updateProgram(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Course Updated!").show();
                LoadTableData();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update the Course!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        lblid.setText(courseBO.generateProgramId());
        txtName.clear();
        txtdurtion.clear();
        txtfee.clear();
        cmbInsId.getSelectionModel().clearSelection();
    }

    private void LoadTableData() throws Exception {
        ProgramTable.setItems(FXCollections.observableList(
                courseBO.getAllCourse().stream().map(courseDto ->
                        new CourseTm(
                                courseDto.getProgramId(),
                                courseDto.getProgramName(),
                                courseDto.getDuration(),
                                courseDto.getFee(),
                                courseDto.getInstructorId()
                        )).toList()
        ));
    }
}
