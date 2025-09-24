package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Dto.StudentDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.exception.DuplicateException;
import lk.ijse.tm.StudentTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
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
    public TableColumn<StudentTm, Date> colRegistrationDate;
    public TextField txtid;
    public Label lblid;

    StudentBO studentBO =(StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);

    public void btnResetPageOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnDeleteStudentOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateStudentOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveStudentOnAction(ActionEvent actionEvent) {

    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        try {
            resetPage();
//            generateStudentId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }
    private void resetPage(){

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtid.clear();
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        dpRegistrationDate.setValue(null);
    }
}
