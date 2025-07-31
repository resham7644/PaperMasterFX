package com.example.papermasterfx.customerboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;

import com.example.papermasterfx.jdbc.MySqlDbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerBoardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private ComboBox<String> comboHawker;

    @FXML
    private ComboBox<String> comboPaper;

    @FXML
    private TableView<CustomerBean> tblView;

    @FXML
    void doFind(ActionEvent event) {
        TableColumn<CustomerBean,String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<CustomerBean,String>("name"));
        nameCol.setMinWidth(100);

        TableColumn<CustomerBean,String> mobileCol = new TableColumn<>("Mobile");
        mobileCol.setCellValueFactory(new PropertyValueFactory<CustomerBean,String>("mobile"));
        mobileCol.setMinWidth(100);

        TableColumn<CustomerBean,String> mailCol = new TableColumn<>("Email ID");
        mailCol.setCellValueFactory(new PropertyValueFactory<CustomerBean,String>("email"));
        mailCol.setMinWidth(150);

        TableColumn<CustomerBean,String> addCol = new TableColumn<>("Area");
        addCol.setCellValueFactory(new PropertyValueFactory<CustomerBean,String>("address"));
        addCol.setMinWidth(100);

        TableColumn<CustomerBean,String> paperCol = new TableColumn<>("Papers");
        paperCol.setCellValueFactory(new PropertyValueFactory<CustomerBean,String>("paper"));
        paperCol.setMinWidth(150);

        tblView.getColumns().clear();
        tblView.getColumns().addAll(nameCol,mobileCol,mailCol,addCol,paperCol);
        tblView.setItems(null);
        tblView.setItems(getArrayofCustomers());

    }

    ObservableList<CustomerBean> getArrayofCustomers()
    {
        ObservableList<CustomerBean> list = FXCollections.observableArrayList();
        StringBuilder query = new StringBuilder("SELECT * FROM customers WHERE status = 1");

        if (comboArea.getValue() != null && !comboArea.getValue().equalsIgnoreCase("none")) {
            String cc = comboArea.getValue();
            query.append(" AND area = '").append(cc).append("'");
        }

        if (comboPaper.getValue() != null && !comboPaper.getValue().equalsIgnoreCase("none")) {
            String pp = comboPaper.getValue();
            query.append(" AND papers like '%").append(pp).append("%'");
        }

        if (comboHawker.getValue() != null && !comboHawker.getValue().equalsIgnoreCase("none")) {
            String hh = comboHawker.getValue();
            query.append(" AND hawkerid = '").append(hh).append("'");
        }
        String q = new String(query);
        try {
            PreparedStatement pst = con.prepareStatement(q);
            ResultSet resp = pst.executeQuery();
            while (resp.next())
            {
                String mob = resp.getString("mobile");
                String name = resp.getString("cname");
                String email = resp.getString("emailid");
                String add = resp.getString("area");
                String paper = resp.getString("papers");
                CustomerBean obj = new CustomerBean(name,mob,email,add,paper);
                list.add(obj);
            }
            System.out.println(q);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;

    }

    void sethawkers()
    {
        ArrayList<String> ids = gethIds();
        ListIterator it = ids.listIterator();
        comboHawker.getItems().add("None");
        while (it.hasNext())
        {
            comboHawker.getItems().add(it.next().toString());
        }
        comboHawker.getSelectionModel().select(0);
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

    void setAreasVal()
    {
        ArrayList<String> pp = getAreasVal();
        ListIterator it = pp.listIterator();
        comboArea.getItems().add("None");
        while (it.hasNext())
        {
            comboArea.getItems().add(it.next().toString());
        }
        comboArea.getSelectionModel().select(0);
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
        comboPaper.getItems().add("None");
        while (it.hasNext())
        {
            comboPaper.getItems().add(it.next().toString());
        }
        comboPaper.getSelectionModel().select(0);
    }

    Connection con;
    @FXML
    void initialize() {
        con = MySqlDbConnection.getMySQLConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
        }
        sethawkers();
        setAreasVal();
        setPaperVal();
    }

}
