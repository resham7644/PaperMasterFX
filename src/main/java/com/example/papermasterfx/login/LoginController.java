package com.example.papermasterfx.login;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.papermasterfx.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label txtError;

    @FXML
    private TextField txtPwd;

    @FXML
    private TextField txtUserid;

    @FXML
    void doLogin(ActionEvent event) {
        if(txtUserid.getText().equals("resham7644") && txtPwd.getText().equals("123456"))
        {
            try{
                Parent root = FXMLLoader.load(HelloApplication.class.getResource("dashboard/dashboardView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("PaperMaster Dashboard");
                stage.setScene(new Scene(root));
                stage.show();

                ((Stage) txtError.getScene().getWindow()).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            txtError.setText("Invalid Credentials");
        }
    }

    @FXML
    void initialize() {

    }

}
