package com.example.papermasterfx.hawkers;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.ResourceBundle;

import com.example.papermasterfx.jdbc.MySqlDbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class HawkersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboAreas;

    @FXML
    private ComboBox<String> combohids;

    @FXML
    private DatePicker dtDOJ;

    @FXML
    private ImageView imgPrev;

    @FXML
    private TextField txtAdhaar;

    @FXML
    private TextField txtAdress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSelAreas;

    @FXML
    void doClear(ActionEvent event) {
        txtName.setText("");
        txtContact.setText("");
        txtAdhaar.setText("");
        txtAdress.setText("");
        txtSelAreas.setText("");
        dtDOJ.setValue(null);
        combohids.getSelectionModel().clearSelection();
        comboAreas.getSelectionModel().clearSelection();
        imgPrev.setImage(t);

    }

    @FXML
    void doDelete(ActionEvent event) {
        try{
            PreparedStatement pst = con.prepareStatement("delete from hawkers where hawkerid=?");
            String hid = combohids.getValue();
            pst.setString(1,hid);
            int resp = pst.executeUpdate();
            if(resp==1)
            {
                showMsg("Deletion","Record Deleted Successfully");
                sethIds();
                doClear(null);
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
    void doFetchIds(ActionEvent event) {
        try{
            PreparedStatement pst = con.prepareStatement("select * from hawkers where hawkerid=?");
            pst.setString(1,combohids.getValue());
            ResultSet resp = pst.executeQuery();

            if(resp.next()==true)
            {

                String name = resp.getString("name");
                txtName.setText(name);
                String cont = resp.getString("contact");
                txtContact.setText(cont);
                String addr = resp.getString("address");
                txtAdress.setText(addr);
                String adhar = resp.getString("adhaar");
                txtAdhaar.setText(adhar);

                java.sql.Date dt = resp.getDate("doj");
                dtDOJ.setValue(dt.toLocalDate());
                String areas = resp.getString("selareas");
                txtSelAreas.setText(areas);
                String path = resp.getString("picpath");
                imgPrev.setImage(new Image(new FileInputStream(new File(path))));



            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void doSave(ActionEvent event) {
        String query = "insert into hawkers values(?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            String name = txtName.getText();
            String cont = txtContact.getText();
            String hid = name.split(" ")[0]+cont.substring(cont.length()-5);

            pst.setString(1,hid);
            pst.setString(2,name);
            pst.setString(3,cont);
            pst.setString(4,txtAdress.getText());
            pst.setString(5,txtAdhaar.getText());
            LocalDate ldt = dtDOJ.getValue();
            java.sql.Date dt = java.sql.Date.valueOf(ldt);
            pst.setDate(6,dt);
            pst.setString(7,txtSelAreas.getText());
            pst.setString(8,picpath);
            pst.executeUpdate();
            System.out.println("Record Saved");
            showMsg("Result","Saved Successfully");
            sethIds();
            doClear(null);

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
    String picpath;
    Image t;
    @FXML
    void doSelPic(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images","*.jpg","*.jpeg","*.png"));
        File file = chooser.showOpenDialog(null);
        picpath = file.getAbsolutePath();
        try{
            imgPrev.setImage(new Image(new FileInputStream(file)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void doUpdate(ActionEvent event) {
        try {
            PreparedStatement pst = con.prepareStatement("update hawkers set name=?, contact=?, address=?, adhaar=?, doj=?, selareas=?, picpath=? where hawkerid=?");
            String name = txtName.getText();
            String cont = txtContact.getText();
            pst.setString(1,name);
            pst.setString(2,cont);
            pst.setString(3,txtAdress.getText());
            pst.setString(4,txtAdhaar.getText());
            LocalDate ldt = dtDOJ.getValue();
            java.sql.Date dt = java.sql.Date.valueOf(ldt);
            pst.setDate(5,dt);
            pst.setString(6,txtSelAreas.getText());
            pst.setString(7,picpath);
            pst.setString(8,combohids.getValue());
            int count = pst.executeUpdate();
            if(count==0)
                showMsg("Updation","Invalid ID");
            else{
                showMsg("Updation","Updated Successfully");
                doClear(null);
                sethIds();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @FXML
    void doAddSelected(MouseEvent event) {
        String item = comboAreas.getSelectionModel().getSelectedItem();
        if(txtSelAreas.getText()!="")
            item = txtSelAreas.getText()+", "+item;
        txtSelAreas.setText(item);
    }



    void setAreasVal()
    {
        ArrayList<String> pp = getAreasVal();
        ListIterator it = pp.listIterator();
        comboAreas.getItems().clear();
        while (it.hasNext())
        {
            comboAreas.getItems().add(it.next().toString());
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

    void sethIds()
    {
        ArrayList<String> ids = gethIds();
        ListIterator it = ids.listIterator();
        combohids.getItems().clear();
        while (it.hasNext())
        {
            combohids.getItems().add(it.next().toString());
        }
    }

    ArrayList<String> gethIds()
    {
        ArrayList<String> hids = new ArrayList<String>();
        try {
            PreparedStatement pst = con.prepareStatement("select hawkerid from hawkers");
            ResultSet resp = pst.executeQuery();

            while(resp.next())
            {
                String ar = resp.getString("hawkerid");
                hids.add(ar);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return hids;
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
//        combohids.setDisable(true);
        sethIds();
        setAreasVal();
        t = imgPrev.getImage();
    }

}
