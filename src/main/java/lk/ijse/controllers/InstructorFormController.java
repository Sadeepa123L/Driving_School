package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.Dto.InstructorDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.InstructorBO;
import lk.ijse.Dto.tm.InstructorTm;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InstructorFormController implements Initializable {
    public Label lblid;
    public TextField txtName;
    public TextField txtspecialization;
    public TextField txtavailability;

    public TableView<InstructorTm> InstructorTable;
    public TableColumn<InstructorTm, String> colId;
    public TableColumn<InstructorTm, String> colName;
    public TableColumn<InstructorTm, String> colspecialization;
    public TableColumn<InstructorTm, String> colavailability;

    public Button btnReset;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;

    InstructorBO instructorBO = (InstructorBO) BOFactory.getBO(BOFactory.BOType.INSTRUCTOR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colspecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colavailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        lblid.setText(instructorBO.generateInstructorId());
        try {
            LoadTableData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateInputs() {
        if (txtName.getText().trim().isEmpty()) {
            showAlert("Instructor name cannot be empty!");
            return false;
        }
        if (!txtName.getText().matches("^[A-Za-z ]+$")) {
            showAlert("Instructor name must contain only letters!");
            return false;
        }
        if (txtspecialization.getText().trim().isEmpty()) {
            showAlert("Specialization cannot be empty!");
            return false;
        }
        if (txtavailability.getText().trim().isEmpty()) {
            showAlert("Availability cannot be empty!");
            return false;
        }
        if (!txtavailability.getText().matches("(?i)^(available|not available)$")) {
            showAlert("Availability must be 'Available' or 'Not Available'!");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.WARNING, message, ButtonType.OK).showAndWait();
    }

    public void btnResetPageOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnDeleteInstructorOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this instructor?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Instructor");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = instructorBO.deleteInstructor(lblid.getText());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!").show();
                    LoadTableData();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the instructor!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnUpdateInstructorOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        InstructorDto dto = new InstructorDto(
                lblid.getText(),
                txtName.getText(),
                txtspecialization.getText(),
                txtavailability.getText()
        );
        try {
            boolean isUpdated = instructorBO.updateInstructor(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor Updated!").show();
                LoadTableData();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update the Instructor!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveInstructorOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        try {
            String newId = instructorBO.generateInstructorId();
            lblid.setText(newId);

            InstructorDto dto = new InstructorDto(
                    newId,
                    txtName.getText(),
                    txtspecialization.getText(),
                    txtavailability.getText()
            );

            boolean isSaved = instructorBO.saveInstructor(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved").showAndWait();
                LoadTableData();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save instructor!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) throws Exception {
        InstructorTm selected = InstructorTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            lblid.setText(selected.getInstructorId());
            txtName.setText(selected.getName());
            txtspecialization.setText(selected.getSpecialization());
            txtavailability.setText(selected.getAvailability());
        }
    }

    private void clearFields() {
        lblid.setText(instructorBO.generateInstructorId());
        txtName.clear();
        txtspecialization.clear();
        txtavailability.clear();
    }

    private void LoadTableData() throws Exception {
        InstructorTable.setItems(FXCollections.observableList(
                instructorBO.getAllInstructor().stream().map(instructorDto ->
                        new InstructorTm(
                                instructorDto.getInstructorId(),
                                instructorDto.getName(),
                                instructorDto.getSpecialization(),
                                instructorDto.getAvailability()
                        )).toList()
        ));
    }
}
