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
import entities.Inpatient;
import entities.Room;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

/**
 *
 * @author Sunny
 */
public class InpatientTabController implements Initializable {

    @FXML
    private TableView<Inpatient> table;

    @FXML
    private ComboBox billCbox;

    @FXML
    private ComboBox<String> labCbox;

//    @FXML
//    private TextField pidF;
    @FXML
    private ComboBox<String> pidCbox;

//    @FXML
//    private TextField labnoF;
    @FXML
    private ComboBox<String> roomnoC;

    @FXML
    private DatePicker dateofadmP;

    @FXML
    private DatePicker dateofdisP;

    private ObservableList<Inpatient> data = FXCollections.observableArrayList();

    private Session session;

    @FXML
    TableColumn<Inpatient, String> pidColumn;

    @FXML
    TableColumn<Inpatient, String> roomnoColumn;

    @FXML
    TableColumn<Inpatient, String> labnoColumn;

    @FXML
    TableColumn<Inpatient, LocalDate> dateofadmColumn;

    @FXML
    TableColumn<Inpatient, LocalDate> dateofdisColumn;

    @FXML
    private void handleAddAction(ActionEvent event) {
        String rno = roomnoC.getSelectionModel().getSelectedItem();
        String pid = pidCbox.getSelectionModel().getSelectedItem();
        String labno = labCbox.getSelectionModel().getSelectedItem();
        LocalDate dateofadm = dateofadmP.getValue();
        LocalDate dateofdis = dateofdisP.getValue();

        if (labno.equals("") || pid.equals("") || rno.equals("") || dateofadm.equals("") || dateofdis.equals("")) {
            ShowAlerts.showErrorAlert("Inpatient", "Error", "Please fill out all fields");

        } else {
            add(pid, rno, labno, dateofadm, dateofdis);
            clearFields();
        }
    }

    public void add(String pid, String rno, String labno, LocalDate dateofadm, LocalDate dateofdis) {
        try {
            session = HibernateUtils.openSession();

            session.beginTransaction();

            Inpatient ip = new Inpatient(pid, rno, labno, dateofadm, dateofdis);

            session.persist(ip);
            data.add(ip);

            session.getTransaction().commit();

            session.close();
            ShowAlerts.showInformationAlert("Inpatient", "Information", " successfully added");
        } catch (ConstraintViolationException ex) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showInformationAlert("Inpatient", "Duplicate Inpatient ID found",
                    "Please enter different Inpatient ID");

        } catch (DataException exe) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showErrorAlert("Inpatient", "Error", "Invalid data entered");
        }

    }
//-----------------------------------------------------------------------------

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showWarningAlert("Inpatient", "Warning", "Please select an inpatient");
            return;
        }

        Alert alert = ShowAlerts.getAlert();
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Inpatient");
        alert.setContentText("This action will delete the selected inpatient.Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                delete();
            }
        });
    }

    private void delete() {
        Inpatient app = table.getSelectionModel().getSelectedItem();
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Inpatient WHERE pid = :id");
        query.setParameter("id", app.getPid());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        data.remove(app);
        ShowAlerts.showInformationAlert("Inpatient", "Information", "Deleted Successfully! Rows Effected: " + result);

    }
//-----------------------------------------------------------------------------

    @FXML
    private void handleSearchAction(ActionEvent event) {
        String pid = pidCbox.getSelectionModel().getSelectedItem();
        if (pid.equals("")) {
            ShowAlerts.showErrorAlert("Inpatient", "Error", "Please select Patient ID to search");

        } else {
            search(pid);
        }
    }

    public void search(String pid) {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Inpatient WHERE pid = :id");
        query.setParameter("id", pid);
        List<Inpatient> list = query.list();
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
        Query query = session.createQuery("FROM Inpatient");
        List<Inpatient> list = query.list();
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
        billCbox.valueProperty().set(null);
        labCbox.valueProperty().set(null);
        dateofadmP.getEditor().clear();
        dateofadmP.setValue(null);
        dateofdisP.getEditor().clear();
        dateofdisP.setValue(null);
        roomnoC.valueProperty().set(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        roomnoColumn.setCellValueFactory(new PropertyValueFactory<>("roomno"));
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        labnoColumn.setCellValueFactory(new PropertyValueFactory<>("labno"));

        dateofadmColumn.setCellValueFactory(new PropertyValueFactory<>("dateofadm"));
        dateofadmColumn.setCellFactory(DatePickerTableCell.forTableColumn());

        dateofdisColumn.setCellValueFactory(new PropertyValueFactory<>("dateofdis"));
        dateofdisColumn.setCellFactory(DatePickerTableCell.forTableColumn());

        data.addListener(new ListChangeListener() {

            @Override
            public void onChanged(ListChangeListener.Change c) {
                table.getItems().setAll(data);
            }
        });

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Room");
        List<Room> roomlist = query.list();
        session.getTransaction().commit();
        session.close();
        List<String> roomnolist = new ArrayList<>();
        for (Room r : roomlist) {
            roomnolist.add(r.getRoomno());
        }

        roomnoC.getItems().addAll(roomnolist);

    }

    @FXML
    private void handleDateOfAdmEditCommit(TableColumn.CellEditEvent<Inpatient, LocalDate> t) {
        ((Inpatient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDateofadm(t.getNewValue());

        Inpatient app = table.getSelectionModel().getSelectedItem();

        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Inpatient SET dateofadm = :doa"
                + " WHERE pid = :id");
        query.setParameter("doa", t.getNewValue());
        query.setParameter("id", app.getPid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Inpatient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleDateOfDisEditCommit(TableColumn.CellEditEvent<Inpatient, LocalDate> t) {
        ((Inpatient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDateofdis(t.getNewValue());

        Inpatient app = table.getSelectionModel().getSelectedItem();

        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Inpatient SET dateofdis = :dod"
                + " WHERE pid = :id");
        query.setParameter("dod", t.getNewValue());
        query.setParameter("id", app.getPid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Inpatient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleOnShownBill() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("Inpatients", "Error", "Please select a inpatient");
            clearFields();
            return;
        }
        Inpatient inpatient = table.getSelectionModel().getSelectedItem();
        Set<Bill> billSet = inpatient.getBills();
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
