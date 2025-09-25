package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

    }
}
