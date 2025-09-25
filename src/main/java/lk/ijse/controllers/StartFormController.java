package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StartFormController implements Initializable {
    public AnchorPane ancmain2;

    private void NavigateTo(String path) {
        try {
            ancmain2.getChildren().clear();
            AnchorPane dashBoard = FXMLLoader.load(getClass().getResource(path));

            dashBoard.prefWidthProperty().bind(ancmain2.widthProperty());
            dashBoard.prefHeightProperty().bind(ancmain2.heightProperty());

            ancmain2.getChildren().add(dashBoard);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Location Not Found").show();
            e.printStackTrace();
        }
    }

    public void signupPageOnAction(ActionEvent actionEvent) {
        NavigateTo("/view/SignUpForm.fxml");
    }

    public void LoginPageOnAction(ActionEvent actionEvent) {
        NavigateTo("/view/LoginForm.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
