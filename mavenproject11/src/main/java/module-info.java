module com.mycompany.mavenproject11 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.mavenproject11 to javafx.fxml;
    exports com.mycompany.mavenproject11;
}
