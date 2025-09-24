module License.School.ORM {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jbcrypt;

    opens lk.ijse.controllers to javafx.fxml;
    opens lk.ijse.entity to org.hibernate.orm.core;

    exports lk.ijse;
    exports lk.ijse.controllers;
}
