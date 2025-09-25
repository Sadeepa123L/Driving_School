package lk.ijse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoard_01_Controller implements Initializable {
    public AnchorPane mainContentPane;

    public void studentManagementOnAction(ActionEvent actionEvent) {
        NavigateTo("/view/StudentForm.fxml");
    }

    public void instructorManagementOnAction(ActionEvent actionEvent) {
        NavigateTo("/view/InstructorForm.fxml");
    }

    public void courseManagementOnAction(ActionEvent actionEvent) {
        NavigateTo("/view/CourseForm.fxml");
    }

    public void lessonSchedulingOnAction(ActionEvent actionEvent) {
    }

    public void paymentManagementOnAction(ActionEvent actionEvent) {
    }

    public void usersManagementOnAction(ActionEvent actionEvent) {
    }

    public void logoutOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainContentPane.getChildren().clear();
        AnchorPane load = null;
        try {
            load = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        load.prefWidthProperty().bind(mainContentPane.widthProperty());
        load.prefHeightProperty().bind(mainContentPane.heightProperty());
        mainContentPane.getChildren().add(load);
    }
    private void NavigateTo(String path) {
        try {
            mainContentPane.getChildren().clear();
            AnchorPane dashBoard = FXMLLoader.load(getClass().getResource(path));

            dashBoard.prefWidthProperty().bind(mainContentPane.widthProperty());
            dashBoard.prefHeightProperty().bind(mainContentPane.heightProperty());

            mainContentPane.getChildren().add(dashBoard);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Location Not Found").show();
            e.printStackTrace();
        }
    }
}
