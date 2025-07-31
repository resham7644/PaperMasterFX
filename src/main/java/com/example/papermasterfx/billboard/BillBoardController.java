package com.example.papermasterfx.billboard;
import com.example.papermasterfx.papertable.ExcelExporter;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.papermasterfx.jdbc.MySqlDbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillBoardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboBillStatus;

    @FXML
    private DatePicker dtFrom;

    @FXML
    private DatePicker dtTo;

    @FXML
    private Label lblAmount;

    @FXML
    private TableView<Billbean> tblBillView;

    @FXML
    void doExport(ActionEvent event) {
        ExcelExporter.exportToExcel(tblBillView);
    }

    @FXML
    void doFind(ActionEvent event) {
        TableColumn<Billbean,String> mobileCol = new TableColumn<>("Mobile ");
        mobileCol.setCellValueFactory(new PropertyValueFactory<Billbean, String>("mobile"));
        mobileCol.setMinWidth(100);

        TableColumn<Billbean,String> daysCol = new TableColumn<>("Days");
        daysCol.setCellValueFactory(new PropertyValueFactory<Billbean,String>("days"));
        daysCol.setMinWidth(100);

        TableColumn<Billbean,String> billCol = new TableColumn<>("Bill Amount");
        billCol.setCellValueFactory(new PropertyValueFactory<Billbean,String>("amount"));
        billCol.setMinWidth(100);

        tblBillView.getColumns().clear();
        tblBillView.getColumns().addAll(mobileCol,daysCol,billCol);
        tblBillView.setItems(null);
        tblBillView.setItems(getarrayofBills());
        lblAmount.setText("Amount : "+String.valueOf(amount));
//        ExcelExporter.exportToExcel(tblBillView);
    }

    ObservableList<Billbean> getarrayofBills()
    {
        ObservableList<Billbean> list = FXCollections.observableArrayList();
        try{
            String query;
            if(comboBillStatus.getValue()=="All")
                query = "select * from billing where dos>=? && doe<=?";
            else if(comboBillStatus.getValue()=="Paid")
                query = "select * from billing where dos>=? && doe<=? && status=0";
            else
                query = "select * from billing where dos>=? && doe<=? && status=1";
            PreparedStatement pst = con.prepareStatement(query);
            LocalDate ldt = dtFrom.getValue();
            java.sql.Date dt = java.sql.Date.valueOf(ldt);
            pst.setDate(1,dt);
            LocalDate lde = dtTo.getValue();
            java.sql.Date de = java.sql.Date.valueOf(lde);
            pst.setDate(2,de);
            ResultSet resp = pst.executeQuery();
            amount = 0f;
            while (resp.next())
            {
                String mob = resp.getString("mobile");
                String days = String.valueOf(resp.getInt("days"));
                Float am = resp.getFloat("bill");
                amount += am;
                String bill = String.valueOf(am);
                Billbean obj = new Billbean(mob,days,bill);
                list.add(obj);
            }
            System.out.println("Fetched");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  list;
    }

    Connection con;
    Float amount;
    @FXML
    void initialize() {
        con = MySqlDbConnection.getMySQLConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
        }
        comboBillStatus.getItems().add("All");
        comboBillStatus.getItems().add("Paid");
        comboBillStatus.getItems().add("Unpaid");

    }

}
