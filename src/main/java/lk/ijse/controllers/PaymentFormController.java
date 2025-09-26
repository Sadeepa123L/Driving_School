package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.Dto.PaymentDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CourseBO;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.Dto.tm.PaymentTm;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {
    public Label lblId;
    public TextField txtpaymentdate;
    public TextField txtAmount;
    public ComboBox<String> cmbStudentId;
    public ComboBox<String> cmbCourseId;
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


    private boolean validateInputs() {
        if (txtpaymentdate.getText().isEmpty() ||
                txtAmount.getText().isEmpty() ||
                txtStatus.getText().isEmpty() ||
                cmbStudentId.getValue() == null ||
                cmbCourseId.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "All fields are required!").show();
            return false;
        }

        try {
            double amount = Double.parseDouble(txtAmount.getText());
            if (amount <= 0) {
                new Alert(Alert.AlertType.WARNING, "Amount must be greater than 0!").show();
                return false;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Invalid amount! Please enter a valid number.").show();
            return false;
        }

        String status = txtStatus.getText().trim().toUpperCase();
        if (!(status.equals("PAID") || status.equals("UNPAID"))) {
            new Alert(Alert.AlertType.WARNING, "Status must be either 'PAID' or 'UNPAID'.").show();
            return false;
        }

        return true;
    }

    public void btnResetPageOnAction(ActionEvent actionEvent) throws Exception {
        lblId.setText(paymentBO.generateNewId());
        txtpaymentdate.clear();
        txtAmount.clear();
        txtStatus.clear();
        cmbStudentId.getSelectionModel().clearSelection();
        cmbCourseId.getSelectionModel().clearSelection();
    }

    public void btnSavePaymentOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        try{
            String newId = paymentBO.generateNewId();
            lblId.setText(newId);
            String studentId = cmbStudentId.getValue();
            String courseId = cmbCourseId.getValue();
            String paymentDate = txtpaymentdate.getText();
            Double amount = Double.valueOf(txtAmount.getText());
            String status = txtStatus.getText().toUpperCase();

            PaymentDto paymentDto = new PaymentDto(
                    newId,
                    studentId,
                    courseId,
                    amount,
                    paymentDate,
                    status
            );

            paymentBO.savePayment(paymentDto);
            new Alert(Alert.AlertType.INFORMATION, "Payment Saved!").show();
            loadAllPayments();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the Payment!").show();
            e.printStackTrace();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        PaymentTm selected = PaymentTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            lblId.setText(selected.getPaymentId());
            txtpaymentdate.setText(selected.getPaymentDate());
            txtAmount.setText(String.valueOf(selected.getAmount()));
            txtStatus.setText(selected.getStatus());
            cmbStudentId.setValue(selected.getStudentId());
            cmbCourseId.setValue(selected.getProgramId());
        }
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
        PaymentTable.setItems(FXCollections.observableList(
                paymentBO.getAllPayments().stream().map(paymentDto ->
                        new PaymentTm(
                                paymentDto.getPaymentId(),
                                paymentDto.getStudentId(),
                                paymentDto.getProgramId(),
                                paymentDto.getAmount(),
                                paymentDto.getPaymentDate(),
                                paymentDto.getStatus()
                        )).toList()
        ));
    }

    public void btnUpdatePaymentOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        String id = lblId.getText();
        String studentId = cmbStudentId.getValue();
        String courseId = cmbCourseId.getValue();
        String paymentDate = txtpaymentdate.getText();
        Double amount = Double.valueOf(txtAmount.getText());
        String status = txtStatus.getText().toUpperCase();

        PaymentDto paymentDto = new PaymentDto(
                id,
                studentId,
                courseId,
                amount,
                paymentDate,
                status
        );
        try {
            boolean isUpdated = paymentBO.updatePayment(paymentDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Updated!").show();
                loadAllPayments();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update the Payment!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
