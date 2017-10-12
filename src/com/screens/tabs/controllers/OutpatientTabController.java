/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.screens.tabs.controllers;

import com.Alerts.ShowAlerts;
import com.hibernate.HibernateUtils;

import com.utils.DatePickerTableCell;
import com.utils.EntitiesList;
import entities.Bill;
import entities.Outpatient;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

public class OutpatientTabController implements Initializable {
    
    @FXML
    private ComboBox<String> labCbox;
    
    @FXML
    private ComboBox<String> pidCbox;
    
    @FXML
    private DatePicker dateP;
    
    @FXML
    private TableView<Outpatient> table;
    
    private Session session;
    
    @FXML
    private TableColumn<Outpatient, String> pidColumn;
    
    @FXML
    private TableColumn<Outpatient, LocalDate> dateColumn;
    
    @FXML
    private TableColumn<Outpatient, String> labnoColumn;
    
    @FXML
    private ComboBox billCbox;
    
    private ObservableList<Outpatient> data = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setEditable(true);
        pidColumn.setCellValueFactory(new PropertyValueFactory("pid"));
        
        labnoColumn.setCellValueFactory(new PropertyValueFactory("labno"));
        labnoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        dateColumn.setCellFactory(DatePickerTableCell.forTableColumn());
        
        data.addListener(new ListChangeListener() {
            
            @Override
            public void onChanged(ListChangeListener.Change c) {
                table.getItems().setAll(data);
            }
        });
    }
    
    @FXML
    private void handleAddAction() {
        
        String pid = pidCbox.getSelectionModel().getSelectedItem();
        String labno = labCbox.getSelectionModel().getSelectedItem();
        LocalDate date = dateP.getValue();
        
        if (pid.equals("") || labno.equals("") || date.equals("")) {
            ShowAlerts.showErrorAlert("Outpatient", "Error", "Please fill out all fields");
            
        } else {
            add(pid, labno, date);
            clearFields();
        }
    }
    
    private void add(String pid, String labno, LocalDate date) {
        try {
            session = HibernateUtils.openSession();
            
            session.beginTransaction();
            
            Outpatient op = new Outpatient(pid, labno, date);
            
            session.persist(op);
            data.add(op);
            
            session.getTransaction().commit();
            
            session.close();
            ShowAlerts.showInformationAlert("Patients", "Information", "Patient ID:" + op.getPid() + " successfully added");
        } catch (ConstraintViolationException ex) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showInformationAlert("Outpatient", "Duplicate Outpatient ID found",
                    "Please enter different Outpatient ID");
            
        } catch (DataException exe) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showErrorAlert("Outpatient", "Error", "Invalid data entered");
        }
    }

    //---------------------------------------------------------------------------  
    @FXML
    private void handleClearAction(ActionEvent e
    ) {
        clearFields();
    }
    
    private void clearFields() {
        pidCbox.valueProperty().set(null);
        labCbox.valueProperty().set(null);
        dateP.getEditor().clear();
        dateP.setValue(null);
    }

//------------------------------------------------------------------------------
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showWarningAlert("Outpatient", "Warning", "Please select an Outpatient");
            return;
        }
        
        Alert alert = ShowAlerts.getAlert();
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Outpatient");
        alert.setContentText("This action will delete the selected outpatient record.Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                delete();
            }
        });
    }
    
    private void delete() {
        Outpatient op = table.getSelectionModel().getSelectedItem();
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Outpatient WHERE pid = :id");
        query.setParameter("id", op.getPid());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        data.remove(op);
        ShowAlerts.showInformationAlert("Outpatient", "Information", "Deleted Successfully! Rows Effected: " + result);
        
    }

    //--------------------------------------------------------------------------
    @FXML
    private void handleSearchAction(ActionEvent event) {
        String pid = pidCbox.getSelectionModel().getSelectedItem();
        if (pid.equals("")) {
            ShowAlerts.showErrorAlert("Outpatient", "Error", "Please enter patient id to search");
            
        } else {
            search(pid);
        }
    }
    
    public void search(String pid) {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Outpatient WHERE pid = :id");
        query.setParameter("id", pid);
        List<Outpatient> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
    }
//-----------------------------------------------------------------------------

    @FXML
    private void handleShowAction(ActionEvent event) {
        show();
    }
    
    private void show() {
        
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Outpatient");
        List<Outpatient> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
        
    }
    //--------------------------------------------------------------------------

    @FXML
    private void handleDateEditCommit(TableColumn.CellEditEvent<Outpatient, LocalDate> t) {
        ((Outpatient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDate(t.getNewValue());
        
        Outpatient op = table.getSelectionModel().getSelectedItem();
        
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Outpatient SET date = :d"
                + " WHERE pid = :id");
        query.setParameter("d", t.getNewValue());
        query.setParameter("id", op.getPid());
        int result = query.executeUpdate();
        
        session.getTransaction().commit();
        session.close();
        
        ShowAlerts.showInformationAlert("Outpatient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));
        
    }
    
    @FXML
    private void handleOnShownBill() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("Outpatients", "Error", "Please select a outpatient");
            clearFields();
            return;
        }
        Outpatient oup = table.getSelectionModel().getSelectedItem();
        Set<Bill> billSet = oup.getBill();
        List<Bill> billList = new ArrayList();
        billList.addAll(billSet);
        billCbox.getItems().setAll(billList.stream().map(Bill::getBillno).collect(toList()));
    }
    
    @FXML
    private void handlePidOnShown() {
        List<String> list = EntitiesList.getPatients();
        pidCbox.getItems().setAll(list);
    }
    
    @FXML
    private void handleLabOnShown() {
        List<String> list = EntitiesList.getLabs();
        labCbox.getItems().setAll(list);
    }
}
