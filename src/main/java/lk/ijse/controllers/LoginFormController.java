package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Dto.UserDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.LoginBO;
import lk.ijse.exception.ExceptionHandler;
import lk.ijse.exception.InvalidCredentialsException;
import lk.ijse.util.Password;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane fullLoginForm;
    public AnchorPane loginForm;
    public TextField inputUserName;
    public PasswordField inputPassword;

    public static UserDto userDto;
    LoginBO loginBO =(LoginBO) BOFactory.getBO(BOFactory.BOType.LOGIN);

    public void inputUserNameOnAction(ActionEvent actionEvent) {
        inputPassword.requestFocus();
    }

    public void inputPasswordOnAction(ActionEvent actionEvent) {
        loginOnAction(actionEvent);
    }


    public void loginOnAction(ActionEvent actionEvent) {
        if (!inputUserName.getText().isEmpty() && !inputPassword.getText().isEmpty()) {
            try {
                UserDto loginUser = loginBO.getUser(inputUserName.getText().trim());

                if (Password.checkPassword(inputPassword.getText().trim(), loginUser.getPassword())) {
                    userDto = loginUser;
                    openDashboardForm(loginUser.getRole());
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid User Password !!").show();
                }
            } catch (InvalidCredentialsException e) {
                ExceptionHandler.handleException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Enter All Fields !!").show();
        }
    }

    private void openDashboardForm(String role) {
        try {
            String fxmlFile;

            if ("Admin".equalsIgnoreCase(role)) {
                fxmlFile = "/view/DashBoard_01_Form.fxml";
            } else if ("Admissions Coordinator".equalsIgnoreCase(role)) {
                fxmlFile = "/view/DashBoard_02_Form.fxml";
            } else {
                new Alert(Alert.AlertType.ERROR, "Unknown role: " + role).show();
                return;
            }

            Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource(fxmlFile)));
            Stage stage = (Stage) fullLoginForm.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToSignUpOnAction(MouseEvent mouseEvent) throws IOException {
        fullLoginForm.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/SignUpForm.fxml"));
        load.prefWidthProperty().bind(fullLoginForm.widthProperty());
        load.prefHeightProperty().bind(fullLoginForm.heightProperty());
        fullLoginForm.getChildren().add(load);

    }
}
