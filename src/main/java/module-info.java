module ez.clap.gestionetudian_aql {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens ez.clap.gestionetudian_aql to javafx.fxml;
    exports ez.clap.gestionetudian_aql;
    exports ez.clap.gestionetudian_aql.controllers;
    opens ez.clap.gestionetudian_aql.controllers to javafx.fxml;
}