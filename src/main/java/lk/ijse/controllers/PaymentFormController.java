package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.Dto.LessonDto;
import lk.ijse.Dto.PaymentDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CourseBO;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.tm.LessonTm;
import lk.ijse.tm.PaymentTm;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {
    public Label lblId;
    public TextField txtpaymentdate;
    public TextField txtAmount;
    public ComboBox cmbStudentId;
    public ComboBox cmbCourseId;
    public TextField txtStatus;

    public Button btnReset;
    public Button btnSave;

    public TableView<PaymentTm> PaymentTable;
    public TableColumn<PaymentTm, String> colId;
    public TableColumn<PaymentTm, String> colDate;
    public TableColumn<PaymentTm, String > colAmount;
    public TableColumn<PaymentTm, String> colSid;
    public TableColumn<PaymentTm, String> colCid;
    public TableColumn<PaymentTm, String > colStatus;

    private final PaymentBO paymentBO =(PaymentBO) BOFactory.getBO(BOFactory.BOType.PAYMENT);
    private final StudentBO studentBO =(StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);
    private final CourseBO courseBO =(CourseBO) BOFactory.getBO(BOFactory.BOType.COURSE);


    public void btnResetPageOnAction(ActionEvent actionEvent) {
    }

    public void btnSavePaymentOnAction(ActionEvent actionEvent) {
        try{
            String newId = paymentBO.generateNewId();
            lblId.setText(newId);
            String studentId = cmbStudentId.getValue().toString();
            String courseId = cmbCourseId.getValue().toString();
            String paymentDate = txtpaymentdate.getText();
            Double amount = Double.valueOf(txtAmount.getText());
            String status = txtStatus.getText();

            PaymentDto paymentDto = new PaymentDto(
                    newId,
                    studentId,
                    courseId,
                    amount,
                    paymentDate,
                    status
            );

            paymentBO.saveLesson(paymentDto);
            new Alert(Alert.AlertType.INFORMATION, "Lesson Saved!").show();
            loadAllPayments();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the Lesson!").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colSid.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCid.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            lblId.setText(paymentBO.generateNewId());
            cmbStudentId.setItems(FXCollections.observableArrayList(studentBO.getAllStudentIds()));
            cmbCourseId.setItems(FXCollections.observableArrayList(courseBO.getAllCourseIds()));
            loadAllPayments();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllPayments() throws Exception {
        PaymentTable.setItems(FXCollections.observableArrayList(
                paymentBO.getAllPayments().stream().map(paymentDto -> new PaymentTm(
                       paymentDto.getPaymentId(),
                        paymentDto.getStudentId(),
                        paymentDto.getProgramId(),
                        paymentDto.getAmount(),
                        paymentDto.getPaymentDate(),
                        paymentDto.getStatus()
                )).toList()
        ));
    }
}
