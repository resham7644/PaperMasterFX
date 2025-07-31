package com.example.papermasterfx.papertable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.papermasterfx.jdbc.MySqlDbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PaperTableController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<PaperBean> tblPapers;

    @FXML
    void doExport(ActionEvent event) {
        ExcelExporter.exportToExcel(tblPapers);
    }


    @FXML
    void doFetchPapers(ActionEvent event) {
        TableColumn<PaperBean,String> paperCol = new TableColumn<>("Newspaper");
        paperCol.setCellValueFactory(new PropertyValueFactory<PaperBean, String>("pname"));
        paperCol.setMinWidth(100);

        TableColumn<PaperBean,String> langCol = new TableColumn<>("Language");
        langCol.setCellValueFactory(new PropertyValueFactory<PaperBean,String>("lang"));
        langCol.setMinWidth(100);

        TableColumn<PaperBean,String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<PaperBean,String>("price"));
        paperCol.setMinWidth(100);

        tblPapers.getColumns().addAll(paperCol,langCol,priceCol);
        tblPapers.setItems(null);
        tblPapers.setItems(getArrayofPapers());

    }

    ObservableList<PaperBean> getArrayofPapers()
    {
        ObservableList<PaperBean> list = FXCollections.observableArrayList();
        try{
            PreparedStatement pst = con.prepareStatement("select * from newspapers");
            ResultSet resp = pst.executeQuery();

            while (resp.next())
            {
                String pname = resp.getString("paper");
                String lang = resp.getString("language");
                String price = String.valueOf(resp.getFloat("price"));

                PaperBean obj = new PaperBean(pname,lang,price);
                list.add(obj);
            }
            System.out.println("Records Fetched Successfully");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    Connection  con;
    @FXML
    void initialize() {
        con = MySqlDbConnection.getMySQLConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
        }
    }

}
