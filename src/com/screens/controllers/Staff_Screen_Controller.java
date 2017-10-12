/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.screens.controllers;

import com.Alerts.ShowAlerts;
import com.hibernate.HibernateUtils;

import entities.Staff;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

/**
 *
 * @author Sunny
 */
public class Staff_Screen_Controller implements Initializable {

    @FXML
    private TextField idF;

    @FXML
    private TextField nameF;

    @FXML
    private TextField addressF;

    @FXML
    private TextField contactF;

    @FXML
    private ComboBox<String> genderCbox = new ComboBox<>();

    @FXML
    private ComboBox<String> stypeCbox = new ComboBox<>();

    @FXML
    private ComboBox<String> departmentCbox = new ComboBox<>();

    private ObservableList<Staff> data = FXCollections.observableArrayList();

    @FXML
    private TableView<Staff> table;

    @FXML
    private TableColumn<Staff, String> idColumn;

    @FXML
    private TableColumn<Staff, String> nameColumn;

    @FXML
    private TableColumn<Staff, String> genderColumn;

    @FXML
    private TableColumn<Staff, String> addressColumn;

    @FXML
    private TableColumn<Staff, String> departmentColumn;

    @FXML
    private TableColumn<Staff, String> contactColumn;

    @FXML
    private TableColumn<Staff, String> stypeColumn;

    private Session session;

    //Add button ActionEvent handler
    @FXML
    private void handleAddAction(ActionEvent e) {

        String id = idF.getText().trim();
        String name = nameF.getText().trim();
        String gender = genderCbox.getSelectionModel().getSelectedItem();
        String dept = departmentCbox.getSelectionModel().getSelectedItem();
        String address = addressF.getText().trim();
        String contact = contactF.getText().trim();
        String staffType = stypeCbox.getSelectionModel().getSelectedItem();

        if (!id.startsWith("s")) {
            ShowAlerts.showErrorAlert("Staffs", "Error in staff id", "A Staff id must start with a 'S' letter");
            return;
        }

        if (id.equals("") || name.equals("") || gender.equals("") || dept.equals("") || address.equals("") || contact.equals("") || staffType.equals("")) {
            ShowAlerts.showWarningAlert("Staff", "Warning!", "Please fill out all fields");

        } else {
            add(id, name, gender, staffType, dept, address, contact);
            clearFields();
        }
    }

    //Delete Button actionevent handler
    @FXML
    private void handleDeleteAction(ActionEvent e) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showWarningAlert("Staf", "Warning!", "Please select a row or record");
            return;
        }

        Alert alert = ShowAlerts.getAlert();
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Staff");
        alert.setContentText("This action will delete the selected staff record.Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                delete();
            }
        });
    }

    //Search button actionevent handler
    @FXML
    private void handleSearchAction(ActionEvent e) {

        String id = idF.getText().trim().toLowerCase();
        if (id.equals("")) {
            ShowAlerts.showWarningAlert("Staff", "Warning!", "Pleasee enter Staff id to search");

        } else {
            search(id);
        }
    }

    //clear button actionevent handler
    @FXML
    private void handleClearAction(ActionEvent e) {
        clearFields();
    }

    //show button actionevent handler
    @FXML
    private void handleShowAction(ActionEvent e) {
        showAllStaffs();
    }

    //method to handle Name cell edit event on editcommit
    @FXML
    private void handleNameEdit(TableColumn.CellEditEvent<Staff, String> t) {
        ((Staff) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setName(t.getNewValue());

        Staff staff = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Staff SET name = :rt"
                + " WHERE sid = :sd");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("sd", staff.getSid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Staff", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    //method to handle gender cell edit event on editcommit
    @FXML
    private void handleGenderEdit(TableColumn.CellEditEvent<Staff, String> t) {
        ((Staff) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setGender(t.getNewValue());

        Staff staff = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Staff SET gender = :rt"
                + " WHERE sid = :sd");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("sd", staff.getSid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Staff", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    //method to handle department cell edit event on editcommit
    @FXML
    private void handleDepartmentEdit(TableColumn.CellEditEvent<Staff, String> t) {
        ((Staff) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDept(t.getNewValue());

        Staff staff = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Staff SET dept = :rt"
                + " WHERE sid = :sd");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("sd", staff.getSid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Staff", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    //method to handle contactno cell edit event
    @FXML
    private void handleContactEdit(TableColumn.CellEditEvent<Staff, String> t) {
        ((Staff) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setContactno(t.getNewValue());

        Staff staff = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Staff SET contactno = :rt"
                + " WHERE sid = :sd");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("sd", staff.getSid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Staff", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    //method to handle address cell edit event on editcommit
    @FXML
    private void handleAddressEdit(TableColumn.CellEditEvent<Staff, String> t) {
        ((Staff) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setAddress(t.getNewValue());

        Staff staff = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Staff SET address = :rt"
                + " WHERE sid = :sd");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("sd", staff.getSid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Staff", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));
    }

    @FXML
    private void handleStypeEdit(TableColumn.CellEditEvent<Staff, String> t) {
        ((Staff) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setAddress(t.getNewValue());

        Staff staff = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Staff SET staff_type = :rt"
                + " WHERE sid = :sd");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("sd", staff.getSid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Staff", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));
    }

    //------add method for insertion of data in database-----------------------
    private void add(String id, String name, String gender, String staffType, String dept, String address, String contact) {
        try {
            session = HibernateUtils.openSession();

            session.beginTransaction();

            Staff staff = new Staff(id, name, dept, gender, staffType, contact, address);

            session.persist(staff);
            data.add(staff);

            session.getTransaction().commit();

            session.close();
            ShowAlerts.showInformationAlert("Information", "Information Added", staff.getSid() + " got successfully added");
        } catch (ConstraintViolationException ex) {
            ShowAlerts.showInformationAlert("Staff", "Invalid StaffId", "Please enter different Staff Id");
            session.getTransaction().rollback();
            session.close();
        } catch (DataException exe) {
            ShowAlerts.showErrorAlert("Staff", "Error", "Invalid data entered");
            session.getTransaction().rollback();
            session.close();
        }

    }

    //method to fetch and show all records of Staff
    private void showAllStaffs() {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Staff");
        List<Staff> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
    }

    //method to clear all textfields
    private void clearFields() {
        nameF.clear();
        idF.clear();
        addressF.clear();
        contactF.clear();
        genderCbox.valueProperty().set(null);
        departmentCbox.valueProperty().set(null);
        stypeCbox.valueProperty().set(null);

    }

    //method to delete a record of Staff from Staff table in database
    private void delete() {
        Staff staff = table.getSelectionModel().getSelectedItem();
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Staff WHERE sid = :no");
        query.setParameter("no", staff.getSid());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        data.remove(staff);
        ShowAlerts.showInformationAlert("Staff", "Information", "Deleted Succesfully! Rows Effected: " + result);

    }

    //method to search a specific record from Staff table in database
    private void search(String id) {
        session = HibernateUtils.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Staff WHERE sid = :id");
        query.setParameter("id", id);
        List<Staff> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
    }

    //controller's intialization method
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderCbox.getItems().addAll("male", "female");
        departmentCbox.getItems().addAll("none", "emergency", "cardiology", "surgery", "dental", "orthopedics", "pediatrics", "physiotherapy", "dermatology");
        stypeCbox.getItems().addAll("receptionist", "nurse", "worker");
        table.setEditable(true);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("sid"));
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setCellFactory(ComboBoxTableCell.forTableColumn("male", "female"));

        stypeColumn.setCellValueFactory(new PropertyValueFactory<>("staff_type"));
        stypeColumn.setCellFactory(ComboBoxTableCell.forTableColumn("receptionist", "nurse", "worker"));

        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
        departmentColumn.setCellFactory(ComboBoxTableCell.forTableColumn("none", "emergency",
                "cardiology", "surgery", "dental", "orthopedics",
                "pediatrics", "physiotherapy", "dermatology"));

        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactno"));
        contactColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        data.addListener(new ListChangeListener() {

            @Override
            public void onChanged(ListChangeListener.Change c) {
                table.getItems().setAll(data);
            }
        });
        contactF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());

    }

    class KeyFilter implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent e) {
            if (!"0123456789".contains(e.getCharacter())) {
                e.consume();
            }
        }
    }

}
