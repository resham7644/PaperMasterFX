package com.example.papermasterfx.billing;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.papermasterfx.jdbc.MySqlDbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class BillingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dtDOE;

    @FXML
    private DatePicker dtDOS;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtLessDays;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

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
                java.sql.Date dos = resp.getDate("dos");
                dtDOS.setValue(dos.toLocalDate());
                String selprices = resp.getString("prices");
                String prices[] = selprices.split(", ");
                Float price=0f;
                for(String p:prices)
                {
                    price += Float.parseFloat(p);
                }
                txtPrice.setText(String.valueOf(price));

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    int days;
    @FXML
    void doGenerate(ActionEvent event) {
        LocalDate ds = dtDOS.getValue();
        LocalDate de = dtDOE.getValue();
        days = (int) ChronoUnit.DAYS.between(ds,de)+1;
        int ldays = Integer.parseInt(txtLessDays.getText());
        days = days - ldays;
        Float rate = Float.parseFloat(txtPrice.getText());
        Float amount = (float)days*rate;
        txtAmount.setText(String.valueOf(amount));

    }

    @FXML
    void doSave(ActionEvent event) {
        String query = "insert into billing values(0,?,?,?,?,?,?)";
        if(Float.parseFloat(txtAmount.getText())==0)
        {
            showMsg("Saving", "Bill Already saved");
            doClear();
            return;
        }
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtMobile.getText());
            LocalDate ldt = dtDOS.getValue();
            java.sql.Date dt = java.sql.Date.valueOf(ldt);
            pst.setDate(2,dt);
            LocalDate lde = dtDOE.getValue();
            java.sql.Date de = java.sql.Date.valueOf(lde);
            pst.setDate(3,de);
            pst.setInt(4,days);
            pst.setFloat(5,Float.parseFloat(txtAmount.getText()));
            pst.setInt(6,1);
            doUpdateDoe();
            pst.executeUpdate();
            showMsg("Saving","Record Saved Successfully");
            doClear();
            System.out.println("Record Saved");

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

    void doUpdateDoe()
    {
        try{
            String query = "update customers set dos=? where mobile=?";
            PreparedStatement pst = con.prepareStatement(query);
            LocalDate doe = dtDOE.getValue();
            java.sql.Date de = java.sql.Date.valueOf(doe.plusDays(1));
            pst.setDate(1,de);
            pst.setString(2,txtMobile.getText());
            pst.executeUpdate();
            System.out.println("DOE Updated in Customer Table");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void doClear()
    {
        txtMobile.setText("");
        txtAmount.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtLessDays.setText("0");
        dtDOS.setValue(null);
        dtDOE.setValue(null);
    }

    Connection con;
    @FXML
    void initialize() {
        con = MySqlDbConnection.getMySQLConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
        }
    }

}
