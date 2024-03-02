module com.mycompany.seethenews {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.seethenews to javafx.fxml;
    exports com.mycompany.seethenews;
}
