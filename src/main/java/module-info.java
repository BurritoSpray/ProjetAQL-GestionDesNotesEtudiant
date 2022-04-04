module ez.clap.gestionetudiant_aql {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens ez.clap.gestionetudiant_aql to javafx.fxml;
    opens ez.clap.gestionetudiant_aql.controllers to javafx.fxml;
    opens ez.clap.gestionetudiant_aql.entities to javafx.base;

    exports ez.clap.gestionetudiant_aql;
    exports ez.clap.gestionetudiant_aql.controllers;
    exports ez.clap.gestionetudiant_aql.entities;
}