package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Dto.UserDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SignUpBO;
import lk.ijse.exception.ExceptionHandler;
import lk.ijse.exception.UserAlreadyExistsException;
import lk.ijse.util.Password;

import java.io.IOException;

public class SignUpController {
    public AnchorPane signUpForm;
    public TextField inputUserName;
    public PasswordField inputPassword;
    public CheckBox adminCheckBox;
    public CheckBox admissionCheckBox;

    SignUpBO signUpBO = (SignUpBO) BOFactory.getBO(BOFactory.BOType.SIGNUP);

    public void signUpBtnOnAction(ActionEvent actionEvent) {
        if (isValied()){
            UserDto userdto = new UserDto();
            userdto.setUserName(inputUserName.getText().trim());
            userdto.setPassword(Password.hashPassword(inputPassword.getText().trim()));

            if (adminCheckBox.isSelected()) {
                userdto.setRole("Admin");
            } else {
                userdto.setRole("Admissions Coordinator");
            }

            try {
                signUpBO.signUp(userdto);
            } catch (UserAlreadyExistsException e) {
                ExceptionHandler.handleException(e);
            }
            try {
                loadLoginPage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            inputUserName.clear();
            inputPassword.clear();
            adminCheckBox.setSelected(false);
            admissionCheckBox.setSelected(false);
        } else {
            new Alert(Alert.AlertType.WARNING,"Please Enter All Fields !!").show();
        }
    }

    private boolean isValied() {
        return inputUserName.getText().trim().length() > 0 && inputPassword.getText().trim().length() > 0;
    }

    public void inputUserNameOnAction(ActionEvent actionEvent) {
        inputPassword.requestFocus();
    }

    public void adminCheckBoxOnAction(ActionEvent actionEvent) {
        adminCheckBox.setSelected(true);
        admissionCheckBox.setSelected(false);
    }

    public void ReceptionistCheckBoxOnAction(ActionEvent actionEvent) {
        admissionCheckBox.setSelected(true);
        adminCheckBox.setSelected(false);
    }

    public void btnGoLogin(ActionEvent actionEvent) throws IOException {
        loadLoginPage();
    }
    private void loadLoginPage() throws IOException {
        signUpForm.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        load.prefWidthProperty().bind(signUpForm.widthProperty());
        load.prefHeightProperty().bind(signUpForm.heightProperty());
        signUpForm.getChildren().add(load);
    }

}
