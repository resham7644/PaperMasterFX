package com.example.papermasterfx.billcollect;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import com.example.papermasterfx.billboard.Billbean;
import com.example.papermasterfx.jdbc.MySqlDbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillCollectController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblAmount;

    @FXML
    private TableView<Billbean> tblBill;

    @FXML
    private TextField txtMobile;

    @FXML
    void doPay(ActionEvent event) {
        String q = "update billing set status=0 where mobile=?";
        try{
            PreparedStatement pst = con.prepareStatement(q);
            pst.setString(1,txtMobile.getText());
            pst.executeUpdate();
            amount=0f;
            lblAmount.setText("Pending Amount : "+amount);
            tblBill.setItems(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doShowPending(ActionEvent event) {
        TableColumn<Billbean,String> mobileCol = new TableColumn<>("Sr No.");
        mobileCol.setCellValueFactory(new PropertyValueFactory<Billbean, String>("mobile"));
        mobileCol.setMinWidth(50);

        TableColumn<Billbean,String> daysCol = new TableColumn<>("Month");
        daysCol.setCellValueFactory(new PropertyValueFactory<Billbean,String>("days"));
        daysCol.setMinWidth(120);

        TableColumn<Billbean,String> billCol = new TableColumn<>("Bill Amount");
        billCol.setCellValueFactory(new PropertyValueFactory<Billbean,String>("amount"));
        billCol.setMinWidth(120);

        tblBill.getColumns().clear();
        tblBill.getColumns().addAll(mobileCol,daysCol,billCol);
        tblBill.setItems(null);
        tblBill.setItems(getArrayofBills());

        lblAmount.setText("Pending Amount : "+amount);
    }

    ObservableList<Billbean> getArrayofBills()
    {
        ObservableList<Billbean> list = FXCollections.observableArrayList();
        String query = "select * from billing where mobile=? AND status=1";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtMobile.getText());
            ResultSet resp = pst.executeQuery();
            int i=0;
            while (resp.next())
            {
                Float am = resp.getFloat("bill");
                amount +=am;
                Date dos = resp.getDate("dos"); // java.util.Date
                int month = dos.getMonth();
                String[] months = {
                        "January", "February", "March", "April", "May", "June",
                        "July", "August", "September", "October", "November", "December"
                };
                String monthName = months[month];
                Billbean obj = new Billbean(String.valueOf(++i),monthName,String.valueOf(am));
                list.add(obj);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  list;
    }

    Connection con;
    Float amount=0f;
    @FXML
    void initialize() {
        con = MySqlDbConnection.getMySQLConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
        }
    }

}
