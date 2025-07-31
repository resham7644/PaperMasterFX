package com.example.papermasterfx.billmenu;

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

public class BillMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label txtUse;

    @FXML
    private Label txtUse1;

    @FXML
    private Label txtUse2;

    @FXML
    void doOpenBillBoard(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("billboardd/billBoardView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Bill Board");
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) txtUse.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doOpenBillColl(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("billcollectt/billCollectView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Bill Collection");
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) txtUse.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doOpenBillGen(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("billingg/billingView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Generate Bills");
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) txtUse.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }

}
