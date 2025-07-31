package com.example.papermasterfx.customers;

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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.LocalDateStringConverter;

public class CustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private ComboBox<String> comboSelArea;

    @FXML
    private ComboBox<String> comboSelHawker;

    @FXML
    private DatePicker dtDOS;

    @FXML
    private ListView<String> lstPapers;

    @FXML
    private ListView<Float> lstPrices;

    @FXML
    private ListView<String> lstSelPapers;

    @FXML
    private ListView<Float> lstSelPrices;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    void doAddSelected(MouseEvent event) {
        if(event.getClickCount()==1)
        {
            int inxprice = lstPapers.getSelectionModel().getSelectedIndex();
            lstPrices.getSelectionModel().select(inxprice);
        }
        else if(event.getClickCount()==2)
        {
            String selpaper = lstPapers.getSelectionModel().getSelectedItem();
            lstSelPapers.getItems().add(selpaper);

            int inxprice = lstPapers.getSelectionModel().getSelectedIndex();
            lstPrices.getSelectionModel().select(inxprice);

            lstSelPrices.getItems().add(lstPrices.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void doDelSelected(MouseEvent event) {
        if(event.getClickCount()==1)
        {
            int inxprice = lstSelPapers.getSelectionModel().getSelectedIndex();
            lstSelPrices.getSelectionModel().select(inxprice);
        }
        if(event.getClickCount()==2)
        {
            int indx = lstSelPapers.getSelectionModel().getSelectedIndex();
            lstSelPapers.getItems().remove(indx);
            lstSelPrices.getItems().remove(indx);
        }
    }


    @FXML
    void doClear(ActionEvent event) {
        txtMobile.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        dtDOS.setValue(null);
        comboSelArea.getSelectionModel().clearSelection();
        comboSelHawker.getSelectionModel().clearSelection();
        lstSelPapers.getItems().clear();
        lstSelPrices.getItems().clear();
    }

    @FXML
    void doDelete(ActionEvent event) {
        String query = "update customers set status=0 where mobile=?";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtMobile.getText());
            int count = pst.executeUpdate();
            if(count==0)
                showMsg("Deletion","Invalid ID");
            else{
                showMsg("Deletion","Deleted Successfully");
                doClear(null);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void doFetchMobile(ActionEvent event) {
        try{
            PreparedStatement pst = con.prepareStatement("select * from customers where mobile=? && status=1");
            pst.setString(1, txtMobile.getText());
            ResultSet resp = pst.executeQuery();

            if(resp.next()==true)
            {

                String name = resp.getString("cname");
                txtName.setText(name);
                String mail = resp.getString("emailid");
                txtEmail.setText(mail);
                String add = resp.getString("address");
                txtAddress.setText(add);
                java.sql.Date dos = resp.getDate("dos");
                dtDOS.setValue(dos.toLocalDate());
                String area = resp.getString("area");
                comboSelArea.getSelectionModel().select(area);
                String hw = resp.getString("hawkerid");
                comboSelHawker.getItems().add(hw);
                comboSelHawker.getSelectionModel().select(hw);

                String selpaper = resp.getString("papers");
                String[] papers = selpaper.split(",");
                String selprices = resp.getString("prices");
                String prices[] = selprices.split(", ");
                for(String a : papers) {
                    lstSelPapers.getItems().add(a);
                }
                for(String p:prices)
                {
                    lstSelPrices.getItems().add(Float.parseFloat(p));
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void doUpdate(ActionEvent event) {
        String query = "update customers set cname=?, emailid=?, address=?, dos=?, area=?, hawkerid=?, papers=?, prices=? where mobile=?";
        try{
            ObservableList<String> papers = lstSelPapers.getItems();
            StringBuilder selPap = new StringBuilder();
            for(int i=0;i<papers.size();i++)
            {
                if(i>0)selPap.append(", ");
                selPap.append(papers.get(i));
            }
            ObservableList<Float> prices = lstSelPrices.getItems();
            StringBuilder selpri = new StringBuilder();
            for(int i=0;i<prices.size();i++)
            {
                if(i>0)selpri.append(", ");
                selpri.append(Float.valueOf(prices.get(i)));
            }
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtName.getText());
            pst.setString(2,txtEmail.getText());
            pst.setString(3,txtAddress.getText());
            LocalDate ldt = dtDOS.getValue();
            java.sql.Date dt = java.sql.Date.valueOf(ldt);
            pst.setDate(4,dt);
            pst.setString(5,comboSelArea.getValue());
            pst.setString(6,comboSelHawker.getValue());
            pst.setString(7,selPap.toString());
            pst.setString(8,selpri.toString());
            pst.setString(9,txtMobile.getText());
            int count = pst.executeUpdate();
            if(count==0)
                showMsg("Updation","Invalid ID");
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

    @FXML
    void dosave(ActionEvent event) {
        String query = "insert into customers values(?,?,?,?,?,?,?,?,?,?)";
        try{
            ObservableList<String> papers = lstSelPapers.getItems();
            StringBuilder selPap = new StringBuilder();
            for(int i=0;i<papers.size();i++)
            {
                if(i>0)selPap.append(", ");
                selPap.append(papers.get(i));
            }
            ObservableList<Float> prices = lstSelPrices.getItems();
            StringBuilder selpri = new StringBuilder();
            for(int i=0;i<prices.size();i++)
            {
                if(i>0)selpri.append(", ");
                selpri.append(Float.valueOf(prices.get(i)));
            }

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtMobile.getText());
            pst.setString(2,txtName.getText());
            pst.setString(3,txtEmail.getText());
            pst.setString(4,txtAddress.getText());
            LocalDate ldt = dtDOS.getValue();
            java.sql.Date dt = java.sql.Date.valueOf(ldt);
            pst.setDate(5,dt);
            pst.setString(6,comboSelArea.getValue());
            pst.setString(7,comboSelHawker.getValue());
            pst.setString(8,selPap.toString());
            pst.setString(9,selpri.toString());
            pst.setInt(10,1);
            pst.executeUpdate();
            System.out.println("Record Saved");
            showMsg("Saving","Record Saved Successfully");
            doClear(null);

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
    void doSelHawker(ActionEvent event) {
        String area = comboSelArea.getValue();
        comboSelHawker.getItems().clear();
        gethIds(area);
    }

    void setAreasVal()
    {
        ArrayList<String> pp = getAreasVal();
        ListIterator it = pp.listIterator();
        comboSelArea.getItems().clear();
        while (it.hasNext())
        {
            comboSelArea.getItems().add(it.next().toString());
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


    void gethIds(String area)
    {
        try {
            PreparedStatement pst = con.prepareStatement("select hawkerid from hawkers where selareas like ?");
            pst.setString(1,"%"+area+"%");
            ResultSet resp = pst.executeQuery();

            while(resp.next())
            {
                String hid = resp.getString("hawkerid");
                comboSelHawker.getItems().add(hid);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void setPapers()
    {
        ArrayList<String> papers = new ArrayList<String>();
        try {
            PreparedStatement pst = con.prepareStatement("select paper,price from newspapers");
            ResultSet resp = pst.executeQuery();

            while(resp.next())
            {
                String eid = resp.getString("paper");
                lstPapers.getItems().add(eid);
//                papers.add(eid);
                Float pr = resp.getFloat("price");
                lstPrices.getItems().add(pr);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
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
        setAreasVal();
        setPapers();

    }

}
