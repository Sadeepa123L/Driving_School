module License.School.ORM {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens lk.ijse.controllers to javafx.fxml;
    exports lk.ijse;
    exports lk.ijse.controllers;
}