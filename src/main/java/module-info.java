module com.github.dylangresham {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.github.dylangresham to javafx.fxml;
    exports com.github.dylangresham;
}
