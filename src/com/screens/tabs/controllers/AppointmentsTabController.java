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
import entities.Appointments;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.input.KeyEvent;
import javafx.util.converter.LocalTimeStringConverter;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

/**
 *
 * @author Sunny
 */
public class AppointmentsTabController implements Initializable {
    
    @FXML
    private TableView<Appointments> table;
    
    @FXML
    private TextField anoF;
    
    @FXML
    private DatePicker appdateD;
    
    @FXML
    private TextField hourF;
    
    @FXML
    private TextField minuteF;
    
    @FXML
    private TextField secondF;
    
    private ObservableList<Appointments> data = FXCollections.observableArrayList();
    
    @FXML
    TableColumn<Appointments, String> appnoColumn;
    
    @FXML
    TableColumn<Appointments, String> pidColumn;
    
    @FXML
    TableColumn<Appointments, String> didColumn;
    
    @FXML
    TableColumn<Appointments, LocalDate> appdateColumn;
    
    @FXML
    TableColumn<Appointments, LocalTime> apptimeColumn;
    
    @FXML
    private ComboBox<String> pidCbox;
    
    @FXML
    private ComboBox<String> docCbox;
    
    private Session session;
    
    @FXML
    private void handleAddAction(ActionEvent event) {
        String appno = anoF.getText().trim();
        String pid = pidCbox.getSelectionModel().getSelectedItem();
        String drid = docCbox.getSelectionModel().getSelectedItem();
        LocalDate appdate = appdateD.getValue();
        String hours = hourF.getText().trim();
        String minutes = minuteF.getText().trim();
        String seconds = secondF.getText().trim();
        
         if (!appno.startsWith("a")) {
            ShowAlerts.showErrorAlert("Appointments", "Error in Appointment no", "AppointmentNo must start with a A letter");
            return;
        }
        
        if (appno.equals("") || pid.equals("") || drid.equals("") || appdate.equals("") || hours.equals("") || minutes.equals("") || seconds.equals("")) {
            ShowAlerts.showErrorAlert("Appointments", "Error", "Please fill out all fields");
            
        } else {
            add(appno, pid, drid, appdate, hours, minutes, seconds);
            clearFields();
        }
    }
    
    public void add(String appno, String pid, String drid, LocalDate appdate, String hours, String minutes, String seconds) {
        try {
            session = HibernateUtils.openSession();
            session.beginTransaction();
            Appointments app = new Appointments(appno, pid, drid, appdate, LocalTime.of(Integer.parseInt(hours), Integer.parseInt(minutes), Integer.parseInt(seconds)));
            session.persist(app);
            data.add(app);
            session.getTransaction().commit();
            session.close();
            ShowAlerts.showInformationAlert("Lab", "Information", "Testno " + app.getAno() + " successfully added");
        } catch (ConstraintViolationException ex) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showInformationAlert("Appointments", "Invalid AppointmentNumber",
                    "Please enter different Appointment Number");
            
        } catch (DataException exe) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showErrorAlert("Appointments", "Error", "Invalid data entered");
        }
        
    }
//-----------------------------------------------------------------------------

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showWarningAlert("Appointments", "Warning", "Please select an appointment record");
            return;
        }
        
        Alert alert = ShowAlerts.getAlert();
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lab");
        alert.setContentText("This action will delete the selected lab record.Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                delete();
            }
        });
    }
    
    private void delete() {
        Appointments app = table.getSelectionModel().getSelectedItem();
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Appointments WHERE ano = :no");
        query.setParameter("no", app.getAno());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        data.remove(app);
        ShowAlerts.showInformationAlert("Appointments", "Information", "Deleted Successfully! Rows Effected: " + result);
        
    }
//-----------------------------------------------------------------------------

    @FXML
    private void handleSearchAction(ActionEvent event) {
        String appno = anoF.getText().trim();
        if (appno.equals("")) {
            ShowAlerts.showErrorAlert("Appointments", "Error", "Please enter appointment number to search");
            
        } else {
            search(appno);
        }
    }
    
    public void search(String appno) {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Appointments WHERE ano = :no");
        query.setParameter("no", appno);
        List<Appointments> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
        
    }
//------------------------------------------------------------------------------

    @FXML
    private void handleShowAction(ActionEvent event) {
        show();
    }
    
    private void show() {
        
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Appointments");
        List<Appointments> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
        
    }
    
    @FXML
    private void handleClearAction(ActionEvent event) {
        clearFields();
    }
    
    private void clearFields() {
        pidCbox.valueProperty().set(null);
        docCbox.valueProperty().set(null);
        anoF.clear();
        hourF.clear();
        secondF.clear();
        minuteF.clear();
        appdateD.getEditor().clear();
        appdateD.setValue(null);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        appnoColumn.setCellValueFactory(new PropertyValueFactory<>("ano"));
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        didColumn.setCellValueFactory(new PropertyValueFactory<>("drid"));
        
        appdateColumn.setCellValueFactory(new PropertyValueFactory<>("app_date"));
        appdateColumn.setCellFactory(DatePickerTableCell.forTableColumn());
        
        apptimeColumn.setCellValueFactory(new PropertyValueFactory<>("app_time"));
        apptimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));
        
        data.addListener(new ListChangeListener() {
            
            @Override
            public void onChanged(ListChangeListener.Change c) {
                table.getItems().setAll(data);
            }
        });
        
        hourF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        minuteF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        secondF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        
    }
    
    @FXML
    private void handleAppDateEditCommit(TableColumn.CellEditEvent<Appointments, LocalDate> t) {
        ((Appointments) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setApp_date(t.getNewValue());
        
        Appointments app = table.getSelectionModel().getSelectedItem();
        
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Appointments SET app_date = :dt"
                + " WHERE ano = :tno");
        query.setParameter("dt", t.getNewValue());
        query.setParameter("tno", app.getAno());
        int result = query.executeUpdate();
        
        session.getTransaction().commit();
        session.close();
        
        ShowAlerts.showInformationAlert("Appointments", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));
        
    }
    
    @FXML
    private void handleAppTimeEditCommit(TableColumn.CellEditEvent<Appointments, LocalTime> t) {
        ((Appointments) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setApp_time(t.getNewValue());
        
        Appointments app = table.getSelectionModel().getSelectedItem();
        
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Appointments SET app_time = :time"
                + " WHERE ano = :no");
        query.setParameter("time", t.getNewValue());
        query.setParameter("no", app.getAno());
        int result = query.executeUpdate();
        
        session.getTransaction().commit();
        session.close();
        
        ShowAlerts.showInformationAlert("Appointments", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));
        
    }
    
    private void handleAppointmentsSelection() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/screens/Patient_Screen"));
        loader.setController(this);
        
    }
    
    class KeyFilter implements EventHandler<KeyEvent> {
        
        @Override
        public void handle(KeyEvent e) {
            if (!"0123456789".contains(e.getCharacter())) {
                e.consume();
            }
        }
    }
    
    @FXML
    private void handlePidOnShown() {
        List<String> list = EntitiesList.getPatients();
        pidCbox.getItems().setAll(list);
    }
    
    @FXML
    private void handleDocOnShown() {
        List<String> list = EntitiesList.getDoctors();
        docCbox.getItems().setAll(list);
    }
}
