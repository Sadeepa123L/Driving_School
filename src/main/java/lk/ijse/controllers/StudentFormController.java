package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.Dto.StudentDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.Dto.tm.StudentTm;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public DatePicker dpRegistrationDate;

    public Button btnReset;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;

    public TableView<StudentTm> studentTable;
    public TableColumn<StudentTm, String> colId;
    public TableColumn<StudentTm, String> colName;
    public TableColumn<StudentTm, String> colAddress;
    public TableColumn<StudentTm, String> colContact;
    public TableColumn<StudentTm, String> colRegistrationDate;
    public Label lblid;

    StudentBO studentBO =(StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    private void resetPage() throws Exception {
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        lblid.setText(studentBO.generateStudentId());
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        dpRegistrationDate.setValue(null);
    }

    private void loadTableData() throws Exception {
        studentTable.setItems(FXCollections.observableList(
                studentBO.getAllStudent().stream().map(studentDto ->
                        new StudentTm(
                                studentDto.getStudentId(),
                                studentDto.getName(),
                                studentDto.getAddress(),
                                studentDto.getContact(),
                                studentDto.getRegDate()
                        )).toList()
        ));
    }

    public void btnResetPageOnAction(ActionEvent actionEvent) throws Exception {
        resetPage();
    }

    public void btnDeleteStudentOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this student?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Student");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = studentBO.deleteStudent(lblid.getText());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Student deleted successfully!").show();
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the student!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnSaveStudentOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        try {
            String newId = studentBO.generateStudentId();
            lblid.setText(newId);

            StudentDto studentDto = new StudentDto(
                    newId,
                    txtName.getText().trim(),
                    txtAddress.getText().trim(),
                    txtContact.getText().trim(),
                    dpRegistrationDate.getValue().toString()
            );
            studentBO.saveStudent(studentDto);
            new Alert(Alert.AlertType.INFORMATION, "Saved", ButtonType.OK).showAndWait();
            resetPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateStudentOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        try {
            StudentDto studentDto = new StudentDto(
                    lblid.getText(),
                    txtName.getText().trim(),
                    txtAddress.getText().trim(),
                    txtContact.getText().trim(),
                    dpRegistrationDate.getValue().toString()
            );
            studentBO.updateStudent(studentDto);
            new Alert(Alert.AlertType.INFORMATION, "Updated", ButtonType.OK).showAndWait();
            resetPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickTable(MouseEvent mouseEvent) throws Exception {
        StudentTm selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblid.setText(selected.getStudentId());
            txtName.setText(selected.getName());
            txtAddress.setText(selected.getAddress());
            txtContact.setText(selected.getContact());
            dpRegistrationDate.setValue(LocalDate.parse(selected.getRegDate()));

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private boolean validateInputs() {
        String name = txtName.getText().trim();
        String address = txtAddress.getText().trim();
        String contact = txtContact.getText().trim();
        LocalDate regDate = dpRegistrationDate.getValue();

        if (name.isEmpty() || address.isEmpty() || contact.isEmpty() || regDate == null) {
            new Alert(Alert.AlertType.WARNING, "All fields are required!").show();
            return false;
        }

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.WARNING, "Name can only contain letters and spaces!").show();
            return false;
        }

        if (!contact.matches("\\d{10}")) {
            new Alert(Alert.AlertType.WARNING, "Contact must be 10 digits!").show();
            return false;
        }

        if (regDate.isAfter(LocalDate.now())) {
            new Alert(Alert.AlertType.WARNING, "Registration date cannot be in the future!").show();
            return false;
        }

        return true;
    }
}
