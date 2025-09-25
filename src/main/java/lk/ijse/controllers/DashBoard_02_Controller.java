package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoard_02_Controller implements Initializable {
    public AnchorPane mainContentPane02;

    public void studentManagementOnAction(ActionEvent actionEvent) {
        NavigateTo("/view/StudentForm.fxml");
    }

    public void lessonSchedulingOnAction(ActionEvent actionEvent) {
        NavigateTo("/view/LessonForm.fxml");
    }

    public void paymentManagementOnAction(ActionEvent actionEvent) {
        NavigateTo("/view/PaymentForm.fxml");
    }

    public void logoutOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainContentPane02.getChildren().clear();
        AnchorPane load = null;
        try {
            load = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        load.prefWidthProperty().bind(mainContentPane02.widthProperty());
        load.prefHeightProperty().bind(mainContentPane02.heightProperty());
        mainContentPane02.getChildren().add(load);
    }
    private void NavigateTo(String path) {
        try {
            mainContentPane02.getChildren().clear();
            AnchorPane dashBoard = FXMLLoader.load(getClass().getResource(path));

            dashBoard.prefWidthProperty().bind(mainContentPane02.widthProperty());
            dashBoard.prefHeightProperty().bind(mainContentPane02.heightProperty());

            mainContentPane02.getChildren().add(dashBoard);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Location Not Found").show();
            e.printStackTrace();
        }
    }
}
