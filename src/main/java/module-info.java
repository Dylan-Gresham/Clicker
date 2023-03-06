module com.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.test to javafx.fxml;
    exports com.test;
}
