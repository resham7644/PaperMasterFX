package com.example.papermasterfx.areas;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;

import com.example.papermasterfx.jdbc.MySqlDbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class AreasController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> coboAreas;

    @FXML
    void doDelete(ActionEvent event) {
        try{
            PreparedStatement pst = con.prepareStatement("delete from areas where area=?");
            String area = coboAreas.getValue();
            pst.setString(1,area);
            int resp = pst.executeUpdate();
            if(resp==1)
            {
                showMsg("Deletion","Deleted Successfully");
                setAreasVal();
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
    void setAreasVal()
    {
        ArrayList<String> pp = getAreasVal();
        ListIterator it = pp.listIterator();
        coboAreas.getItems().clear();
        while (it.hasNext())
        {
            coboAreas.getItems().add(it.next().toString());
        }
    }

    ArrayList<String> getAreasVal()
    {
        ArrayList<String> areas = new ArrayList<String>();
        try {
            PreparedStatement pst = con.prepareStatement("select area from areas");
            ResultSet resp = pst.executeQuery();

            while(resp.next())
            {
                String ar = resp.getString("area");
                areas.add(ar);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return areas;
    }

    @FXML
    void doSave(ActionEvent event) {

        String query = "insert into areas values(?)";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,coboAreas.getValue());
            pst.executeUpdate();
            System.out.println("Record Saved");
            showMsg("Result","Saved Successfully");
            setAreasVal();
            coboAreas.getSelectionModel().clearSelection();

        }
        catch (Exception e)
        {
            System.out.println(e);
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
    Connection con;
    @FXML
    void initialize() {
        con = MySqlDbConnection.getMySQLConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
        }
        setAreasVal();
    }

}
