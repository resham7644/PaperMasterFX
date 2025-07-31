package com.example.papermasterfx.papermaster;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;

import com.example.papermasterfx.jdbc.MySqlDbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class PaperMasterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combonewspaper;

    @FXML
    private TextField txtLang;

    @FXML
    private TextField txtPrice;

    @FXML
    void doClear(ActionEvent event) {
        combonewspaper.getSelectionModel().clearSelection();
        txtLang.setText("");
        txtPrice.setText("");
    }

    @FXML
    void doDelete(ActionEvent event) {
        try{
            PreparedStatement pst = con.prepareStatement("delete from newspapers where paper=?");
            String pp = combonewspaper.getValue();
            pst.setString(1,pp);
            int resp = pst.executeUpdate();
            if(resp==1)
            {
                showMsg("Deletion","Record Deleted Successfully");
                setPaperVal();
            }
            else{
                showMsg("Deletion","No Record Available to Delete");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void doFind(MouseEvent event) {
        try{
            PreparedStatement pst = con.prepareStatement("select * from newspapers where paper=?");
            pst.setString(1,combonewspaper.getValue());
            ResultSet resp = pst.executeQuery();

            if(resp.next()==true)
            {

                String lang = resp.getString("language");
                txtLang.setText(String.valueOf(lang));
                float pr = resp.getFloat("price");
                txtPrice.setText(String.valueOf(pr));

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void showMsg(String title,String msg)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Result");
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    void doSave(ActionEvent event) {

        String query = "insert into newspapers values(?,?,?)";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,combonewspaper.getValue());
            pst.setString(2,txtLang.getText());
            pst.setFloat(3,Float.parseFloat(txtPrice.getText()));
            pst.executeUpdate();
            System.out.println("Record Saved");
            showMsg("Saving","Record Saved Successfully");
            doClear(null);
            setPaperVal();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @FXML
    void doUpdate(ActionEvent event) {
        String query = "update newspapers set language=?, price=? where paper=?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtLang.getText());
            pst.setFloat(2,Float.parseFloat(txtPrice.getText()));
            pst.setString(3,combonewspaper.getValue());

            int c = pst.executeUpdate();
            if(c==0)
            {
                showMsg("Updation","Invalid Newspaper");
            }
            else{
                showMsg("Updation","Updated Successfully");
                doClear(null);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    ArrayList<String> getPapers()
    {
        ArrayList<String> papers = new ArrayList<String>();
        try {
            PreparedStatement pst = con.prepareStatement("select distinct paper from newspapers");
            ResultSet resp = pst.executeQuery();

            while(resp.next())
            {
                String eid = resp.getString("paper");
                papers.add(eid);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return papers;
    }
    void setPaperVal()
    {
        ArrayList<String> pp = getPapers();
        ListIterator it = pp.listIterator();
        combonewspaper.getItems().clear();
        while (it.hasNext())
        {
            combonewspaper.getItems().add(it.next().toString());
        }
    }

    Connection con;
    @FXML
    void initialize() {
        con = MySqlDbConnection.getMySQLConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
        }
        setPaperVal();

    }

}
