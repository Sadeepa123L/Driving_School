module License.School.ORM {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    opens lk.ijse.Controllers to javafx.fxml;
    exports lk.ijse;
    exports lk.ijse.Controllers;
}