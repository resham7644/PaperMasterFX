package com.example.papermasterfx.customermenu;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.papermasterfx.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomerMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label txtUse;

    @FXML
    void doOpenCusBoard(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("customerboardd/customerBoardView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Customer Board");
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) txtUse.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doOpenCustomers(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("customerss/customersView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Customer Editor");
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) txtUse.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert txtUse != null : "fx:id=\"txtUse\" was not injected: check your FXML file 'customerMenuView.fxml'.";

    }

}
