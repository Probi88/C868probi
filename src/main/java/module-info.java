module com.example.c868probi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.c868probi to javafx.fxml;
    exports com.example.c868probi;
    exports com.example.c868probi.Controller;
    opens com.example.c868probi.Controller to javafx.fxml;
    exports com.example.c868probi.Model;
    opens com.example.c868probi.Model to javafx.fxml;
    exports com.example.c868probi.DAO;
    opens com.example.c868probi.DAO to javafx.fxml;
    exports com.example.c868probi.Helper;
    opens com.example.c868probi.Helper to javafx.fxml;
}