package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.configuration.FactoryConfiguration;

import java.io.IOException;
import java.util.Objects;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primarystage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        primarystage.setScene(scene);
        primarystage.setTitle("Life Fitness Gym");
        primarystage.show();

        FactoryConfiguration.getInstance();
        FactoryConfiguration.getSession();
    }
}
