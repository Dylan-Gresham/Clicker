module com.github.dylangresham {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.github.dylangresham to javafx.fxml;
    exports com.github.dylangresham;
}
