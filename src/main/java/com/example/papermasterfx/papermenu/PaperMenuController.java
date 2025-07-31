package com.example.papermasterfx.papermenu;

import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.papermasterfx.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaperMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label txtUse;


    @FXML
    private URL location;

    @FXML
    void doOpenPaperBoard(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("papertablee/paperTableView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("PaperMaster");
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) txtUse.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doOpenPaperMaster(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("papermaster/paperMasterView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("PaperMaster");
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
