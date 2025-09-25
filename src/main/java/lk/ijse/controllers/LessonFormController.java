package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class LessonFormController {
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

    public TableView LessonTable;
    public TableColumn colId;
    public TableColumn colDate;
    public TableColumn colduration;
    public TableColumn colSid;
    public TableColumn colIid;
    public TableColumn colCid;

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
}
