package com.example.papermasterfx.dashboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.example.papermasterfx.HelloApplication;
import com.example.papermasterfx.jdbc.MySqlDbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label TotalPayment;

    @FXML
    private Label lblPendingPay;

    @FXML
    private Label lblTotalCus;

    @FXML
    private PieChart pieChart;

    @FXML
    void doOpenBillMenu(MouseEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("billmenu/billmenuView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Bill Console");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doOpenCusMenu(MouseEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("customermenu/customerMenuView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Customer Manager");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doOpenHawkers(MouseEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("hawkerss/hawkersView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("PaperMaster");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doOpenPaperMenu(MouseEvent event) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("papermenu/paperMenuView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("PaperMaster");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getNewspaperFrequency() {
        Map<String, Integer> frequencyMap = new HashMap<>();
        try  {
            String query = "SELECT papers FROM customers";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String papers = rs.getString("papers");
                if (papers != null && !papers.isEmpty()) {
                    String[] paperList = papers.split(",");
                    for (String paper : paperList) {
                        paper = paper.trim();
                        frequencyMap.put(paper, frequencyMap.getOrDefault(paper, 0) + 1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frequencyMap;
    }
    void fillChart()
    {
        Map<String, Integer> data = getNewspaperFrequency();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        pieChart.setData(pieData);
        pieChart.setTitle("Pie Chart");

    }

    void getTotalCus()
    {
        int c=0;
        try{
            PreparedStatement pst = con.prepareStatement("select count(mobile) as total FROM customers");
            ResultSet resp = pst.executeQuery();
            if(resp.next()) {
                c = resp.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(c!=0)
        {
            lblTotalCus.setText(String.valueOf(c));
        }
    }

    void getTotalAmount()
    {
        int am = 0;
        try{
            PreparedStatement pst = con.prepareStatement("select sum(bill) as total from billing");
            ResultSet resp = pst.executeQuery();
            if(resp.next()) {
                am = resp.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(am!=0)
        {
            TotalPayment.setText(String.valueOf(am));
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
        fillChart();
        getTotalCus();
        getTotalAmount();
    }

}
