/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.screens.controllers;

import com.Alerts.ShowAlerts;
import com.hibernate.HibernateUtils;
import entities.Appointments;
import entities.Doctor;
import entities.Lab;
import entities.Patient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
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

public class Doctor_Screen_Controller implements Initializable {

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
    private ComboBox<String> departmentCbox = new ComboBox<>();

    private ObservableList<Doctor> data = FXCollections.observableArrayList();

    @FXML
    private TableView<Doctor> table;

    @FXML
    private TableColumn<Doctor, String> idColumn;

    @FXML
    private TableColumn<Doctor, String> nameC;

    @FXML
    private TableColumn<Doctor, String> genderColumn;

    @FXML
    private TableColumn<Doctor, String> addressColumn;

    @FXML
    private TableColumn<Doctor, String> departmentColumn;

    @FXML
    private TableColumn<Doctor, String> contactC;

    @FXML
    private ComboBox appCbox;

    @FXML
    private ComboBox labCbox;

    @FXML
    private ComboBox patientCbox;

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

        if (!id.startsWith("d")) {
            ShowAlerts.showErrorAlert("Docotr", "Error in doctor id", "A Doctor id must start with a D letter");
            return;
        }

        if (id.equals("") || name.equals("") || gender.equals("") || dept.equals("") || address.equals("") || contact.equals("")) {
            ShowAlerts.showWarningAlert("Doctor", "Warning!", "Please fill out all fields");
            return;
        } else {
            add(id, name, gender, dept, address, contact);
            clearFields();
        }
    }

    //Delete Button actionevent handler
    @FXML
    private void handleDeleteAction(ActionEvent e) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showWarningAlert("Doctor", "Warning!", "Please select a row or record");
            return;
        }
        Alert alert = ShowAlerts.getAlert();
        alert.setAlertType(AlertType.CONFIRMATION);
        alert.setTitle("Doctors");
        alert.setContentText("This action will delete the selected doctor record.Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                delete();
            } else {
                return;
            }
        });
    }

    //Search button actionevent handler
    @FXML
    private void handleSearchAction(ActionEvent e) {

        String id = idF.getText().trim().toLowerCase();
        if (id.equals("")) {
            ShowAlerts.showWarningAlert("Lab", "Warning", "Pleasee enter Doctor id to search");
            return;
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
        showAllDoctors();
    }

    //refersh's the table
    @FXML
    private void handleRefreshAction(ActionEvent e) {
        table.setItems(data);
    }

    //method to handle Name cell edit event on editcommit
    @FXML
    private void handleNameEdit(CellEditEvent<Doctor, String> t) {
        ((Doctor) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDrname(t.getNewValue());

        Doctor doctor = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Doctor SET drname = :rt"
                + " WHERE drid = :did");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("did", doctor.getDrid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Doctor", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    //method to handle gender cell edit event on editcommit
    @FXML
    private void handleGenderEdit(CellEditEvent<Doctor, String> t) {
        ((Doctor) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setGender(t.getNewValue());

        Doctor doctor = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Doctor SET gender = :rt"
                + " WHERE drid = :did");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("did", doctor.getDrid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Doctor", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    //method to handle department cell edit event on editcommit
    @FXML
    private void handleDepartmentEdit(CellEditEvent<Doctor, String> t) {
        ((Doctor) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDept(t.getNewValue());

        Doctor doctor = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Doctor SET dept = :rt"
                + " WHERE drid = :did");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("did", doctor.getDrid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Doctor", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    //method to handle contactno cell edit event
    @FXML
    private void handleContactEdit(CellEditEvent<Doctor, String> t) {
        ((Doctor) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setContactno(t.getNewValue());

        Doctor doctor = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Doctor SET contactno = :rt"
                + " WHERE drid = :did");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("did", doctor.getDrid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Doctor", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    //method to handle address cell edit event on editcommit
    @FXML
    private void handleAddressEdit(CellEditEvent<Doctor, String> t) {
        ((Doctor) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setAddress(t.getNewValue());

        Doctor doctor = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Doctor SET address = :rt"
                + " WHERE drid = :did");
        query.setParameter("rt", t.getNewValue());
        query.setParameter("did", doctor.getDrid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Doctor", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));
    }

    //------add method for insertion of data in database-----------------------
    private void add(String id, String name, String gender, String dept, String address, String contact) {

        try {
            session = HibernateUtils.openSession();
            session.beginTransaction();
            Doctor doctor = new Doctor(id, name, dept, gender, address, contact);
            session.persist(doctor);
            data.add(doctor);
            session.getTransaction().commit();
            session.close();
            ShowAlerts.showInformationAlert("Doctor", "Information", "Added Successfully");
        } catch (ConstraintViolationException ex) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showInformationAlert("Doctor", "Invalid Doctor ID", "ID found duplicate.ID must be unique");

        } catch (DataException exe) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showErrorAlert("Doctor", "Error", "Invalid data entered");
        }
    }

    //method to fetch and show all records of doctor
    private void showAllDoctors() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Doctor");
        List<Doctor> list = query.list();
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

    }

    //method to delete a record of doctor from doctor table in database
    private void delete() {
        Doctor doctor = table.getSelectionModel().getSelectedItem();
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Doctor WHERE drid = :no");
        query.setParameter("no", doctor.getDrid());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        data.remove(doctor);
        ShowAlerts.showInformationAlert("Doctor", "Information", "Deleted Successfully! Rows Effected: " + result);

    }

    //method to search a specific record from doctor table in database
    private void search(String id) {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Doctor WHERE drid = :did");
        query.setParameter("did", id);
        List<Doctor> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
    }

    //controller's intialization method
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderCbox.getItems().addAll("male", "female");
        departmentCbox.getItems().addAll("none", "emergency", "cardiology", "surgery", "dental", "orthopedics", "pediatrics", "physiotherapy", "dermatology");
        table.setEditable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("drid"));
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameC.setCellValueFactory(new PropertyValueFactory<>("drname"));
        nameC.setCellFactory(TextFieldTableCell.forTableColumn());
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setCellFactory(ComboBoxTableCell.forTableColumn("male", "female"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
        departmentColumn.setCellFactory(ComboBoxTableCell.forTableColumn("none", "emergency",
                "cardiology", "surgery", "dental", "orthopedics",
                "pediatrics", "physiotherapy", "dermatology"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contactC.setCellValueFactory(new PropertyValueFactory<>("contactno"));
        contactC.setCellFactory(TextFieldTableCell.forTableColumn());

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

    @FXML
    private void handleOnShownApp() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("Patients", "Error", "Please select a doctor");
            clearFields();
            return;
        }
        Doctor doctor = table.getSelectionModel().getSelectedItem();
        Set<Appointments> appSet = doctor.getAppointments();
        List<Appointments> appIds = new ArrayList();
        appIds.addAll(appSet);
        appCbox.getItems().setAll(appIds.stream().map(Appointments::getAno).collect(toList()));
    }

    @FXML
    private void handleOnShownLab() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("Doctors", "Error", "Please select a doctor");
            clearFields();
            return;
        }
        Doctor doctor = table.getSelectionModel().getSelectedItem();
        Set<Lab> labset = doctor.getLab();
        List<Lab> lablist = new ArrayList();
        lablist.addAll(labset);
        labCbox.getItems().setAll(lablist.stream().map(Lab::getLabno).collect(toList()));
    }

    @FXML
    private void handleOnShownPatient() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("Doctors", "Error", "Please select a doctor");
            clearFields();
            return;
        }
        Doctor doctor = table.getSelectionModel().getSelectedItem();
        Set<Patient> patientSet = doctor.getPatient();
        List<Patient> patientList = new ArrayList();
        patientList.addAll(patientSet);
        patientCbox.getItems().setAll(patientList.stream().map(Patient::getPid).collect(toList()));
    }

}
