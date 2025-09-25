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
import lk.ijse.tm.CourseTm;

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
    public ComboBox cmbInsId;
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

    public void btnSaveProgramOnAction(ActionEvent actionEvent) {
        try {
            String newId = courseBO.generateProgramId(); // generate new ID
            lblid.setText(newId);
            String newName = txtName.getText();
            Integer newDuration = Integer.valueOf(txtdurtion.getText());
            Double newFee = Double.valueOf(txtfee.getText());
            String instructorId =cmbInsId.getValue().toString();

            CourseDto dto = new CourseDto(
                    newId,
                    newName,
                    newDuration,
                    newFee,
                    instructorId
            );

            System.out.println(dto);

            courseBO.saveProgram(dto);

            new Alert(Alert.AlertType.INFORMATION, "Saved", ButtonType.OK).showAndWait();
            LoadTableData();

            // clear fields
            txtName.clear();
            txtdurtion.clear();
            txtfee.clear();

            // update ID for next entry
            lblid.setText(courseBO.generateProgramId());

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
        lblid.setText(courseBO.generateProgramId());
        txtName.clear();
        txtdurtion.clear();
        txtfee.clear();
        cmbInsId.getSelectionModel().clearSelection();
    }

    public void btnDeleteProgramOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this student?",
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
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the course!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnUpdateProgramOnAction(ActionEvent actionEvent) {
        String id = lblid.getText();
        String newName = txtName.getText();
        Integer newDuration = Integer.valueOf(txtdurtion.getText());
        Double newFee = Double.valueOf(txtfee.getText());
        String instructorId =cmbInsId.getValue().toString();

        CourseDto dto = new CourseDto(
                id,
                newName,
                newDuration,
                newFee,
                instructorId
        );
        try {
            boolean isUpdated = courseBO.updateProgram(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Course Updated!").show();
                LoadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update the Course!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
