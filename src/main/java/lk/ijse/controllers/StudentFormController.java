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
                    new Alert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the instructor!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnUpdateStudentOnAction(ActionEvent actionEvent) {
        String id = lblid.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String regDate = dpRegistrationDate.getValue().toString();

        StudentDto studentDto = new StudentDto(
                id,
                name,
                address,
                contact,
                regDate
        );
        try{
            studentBO.updateStudent(studentDto);
            new Alert(Alert.AlertType.INFORMATION, "Updated", ButtonType.OK).showAndWait();
            resetPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveStudentOnAction(ActionEvent actionEvent) {
        try {
            String newId = studentBO.generateStudentId();
            lblid.setText(newId);
            String newName = txtName.getText();
            String newAddress = txtAddress.getText();
            String newContact = txtContact.getText();
            String newDate = dpRegistrationDate.getValue().toString();

            StudentDto studentDto = new StudentDto(
                    newId,
                    newName,
                    newAddress,
                    newContact,
                    newDate
            );
            studentBO.saveStudent(studentDto);
            new Alert(Alert.AlertType.INFORMATION, "Saved", ButtonType.OK).showAndWait();

             resetPage();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickTable(MouseEvent mouseEvent) throws Exception {
        StudentTm selected = studentTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            // set values to form fields
            lblid.setText(selected.getStudentId());
            txtName.setText(selected.getName());
            txtAddress.setText(selected.getAddress());
            txtContact.setText(selected.getContact());
            dpRegistrationDate.setValue(LocalDate.parse(selected.getRegDate()));

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            LoadTableData();
        }
    }

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

        LoadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        lblid.setText(studentBO.generateStudentId());
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        dpRegistrationDate.setValue(null);
    }

    private void LoadTableData() throws Exception {
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
}
