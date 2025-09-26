package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
        if (isValid()) {
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
                new Alert(Alert.AlertType.INFORMATION, "Account created successfully!").show();
                loadLoginPage();
            } catch (UserAlreadyExistsException e) {
                ExceptionHandler.handleException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            resetForm();
        }
    }

    private boolean isValid() {
        String username = inputUserName.getText().trim();
        String password = inputPassword.getText().trim();

        // Username validation
        if (username.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Username cannot be empty!").show();
            return false;
        }
        if (username.length() < 4) {
            new Alert(Alert.AlertType.WARNING, "Username must be at least 4 characters long!").show();
            return false;
        }
        if (username.contains(" ")) {
            new Alert(Alert.AlertType.WARNING, "Username cannot contain spaces!").show();
            return false;
        }

        if (password.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Password cannot be empty!").show();
            return false;
        }
        if (password.length() < 6) {
            new Alert(Alert.AlertType.WARNING, "Password must be at least 6 characters long!").show();
            return false;
        }
        if (!password.matches(".*[A-Za-z].*") || !password.matches(".*\\d.*")) {
            new Alert(Alert.AlertType.WARNING, "Password must contain at least one letter and one number!").show();
            return false;
        }

        if (!adminCheckBox.isSelected() && !admissionCheckBox.isSelected()) {
            new Alert(Alert.AlertType.WARNING, "Please select a role (Admin or Admissions Coordinator)!").show();
            return false;
        }

        return true;
    }

    private void resetForm() {
        inputUserName.clear();
        inputPassword.clear();
        adminCheckBox.setSelected(false);
        admissionCheckBox.setSelected(false);
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
