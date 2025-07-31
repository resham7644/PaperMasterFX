module com.example.papermasterfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    requires org.apache.xmlbeans;
    requires org.apache.commons.collections4;
    requires org.apache.commons.compress;

    requires org.controlsfx.controls;
    requires java.sql;


    opens com.example.papermasterfx to javafx.fxml;
    exports com.example.papermasterfx;

    opens com.example.papermasterfx.papermaster to javafx.fxml;
    exports com.example.papermasterfx.papermaster;

    opens com.example.papermasterfx.areas to javafx.fxml;
    exports com.example.papermasterfx.areas;

    opens com.example.papermasterfx.hawkers to javafx.fxml;
    exports com.example.papermasterfx.hawkers;

    opens com.example.papermasterfx.customers to javafx.fxml;
    exports com.example.papermasterfx.customers;

    opens com.example.papermasterfx.billing to javafx.fxml;
    exports com.example.papermasterfx.billing;

    opens com.example.papermasterfx.papertable to javafx.fxml;
    exports com.example.papermasterfx.papertable;

    opens com.example.papermasterfx.billboard to javafx.fxml;
    exports com.example.papermasterfx.billboard;

    opens com.example.papermasterfx.customerboard to javafx.fxml;
    exports com.example.papermasterfx.customerboard;

    opens com.example.papermasterfx.billcollect to javafx.fxml;
    exports com.example.papermasterfx.billcollect;

    opens com.example.papermasterfx.dashboard to javafx.fxml;
    exports com.example.papermasterfx.dashboard;

    opens com.example.papermasterfx.papermenu to javafx.fxml;
    exports com.example.papermasterfx.papermenu;

    opens com.example.papermasterfx.customermenu to javafx.fxml;
    exports com.example.papermasterfx.customermenu;

    opens com.example.papermasterfx.billmenu to javafx.fxml;
    exports com.example.papermasterfx.billmenu;

    opens com.example.papermasterfx.login to javafx.fxml;
    exports com.example.papermasterfx.login;
}