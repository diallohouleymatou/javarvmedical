module com.ouly.rendezvous {
    requires javafx.controls;
    requires javafx.fxml;
    requires  org.hibernate.orm.core;

    requires org.controlsfx.controls;
    requires jakarta.persistence;

    opens com.ouly.rendezvous to javafx.fxml;
    opens com.ouly.rendezvous.model to org.hibernate.orm.core, javafx.fxml;
    opens com.ouly.rendezvous.controller to javafx.fxml;
    exports com.ouly.rendezvous;

}

