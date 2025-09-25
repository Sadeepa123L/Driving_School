package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.Dto.CourseDto;
import lk.ijse.Dto.InstructorDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.InstructorBO;
import lk.ijse.tm.InstructorTm;

import java.net.URL;
import java.util.ArrayList;
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

    InstructorBO instructorBO =(InstructorBO) BOFactory.getBO(BOFactory.BOType.INSTRUCTOR);

    public void btnResetPageOnAction(ActionEvent actionEvent) {
        lblid.setText(instructorBO.generateInstructorId());
        txtName.clear();
        txtspecialization.clear();
        txtavailability.clear();
    }

    public void btnDeleteInstructorOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this student?",
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
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the instructor!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnUpdateInstructorOnAction(ActionEvent actionEvent) {
        String id = lblid.getText();
        String name = txtName.getText();
        String specialization = txtspecialization.getText();
        String availability = txtavailability.getText();
        InstructorDto dto = new InstructorDto(
                id,
                name,
                specialization,
                availability
        );
        try {
            boolean isUpdated = instructorBO.updateInstructor(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor Updated!").show();
                LoadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update the Instructor!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void btnSaveInstructorOnAction(ActionEvent actionEvent) {
        try {
            String newId = instructorBO.generateInstructorId(); // generate new ID
            lblid.setText(newId);
            String newName = txtName.getText();
            String newSpecialization = txtspecialization.getText();
            String newAvailability = txtavailability.getText();

            InstructorDto dto = new InstructorDto(
                    newId,
                    newName,
                    newSpecialization,
                    newAvailability
            );

           // System.out.println(dto);

            instructorBO.saveInstructor(dto);

            new Alert(Alert.AlertType.INFORMATION, "Saved", ButtonType.OK).showAndWait();

            // clear fields
            txtName.clear();
            txtspecialization.clear();
            txtavailability.clear();

            // update ID for next entry
            lblid.setText(instructorBO.generateInstructorId());
            LoadTableData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) throws Exception {
        InstructorTm selected = InstructorTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            // set values to form fields
            lblid.setText(selected.getInstructorId());
            txtName.setText(selected.getName());
            txtspecialization.setText(selected.getSpecialization());
            txtavailability.setText(selected.getAvailability());
        }

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
}
