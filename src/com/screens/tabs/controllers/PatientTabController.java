/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.screens.tabs.controllers;

import com.Alerts.ShowAlerts;
import com.hibernate.HibernateUtils;
import entities.Appointments;
import entities.Inpatient;
import entities.Lab;
import entities.Patient;
import entities.Room;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.IntegerStringConverter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

/**
 *
 * @author Sunny
 */
public class PatientTabController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button showButton;

    @FXML
    private TextField pidF;

    @FXML
    private TextField nameF;

    @FXML
    private TextField ageF;

    @FXML
    private TextField weightF;

    @FXML
    private TextField addressF;

    @FXML
    private TextField dridF;

    @FXML
    private TextField diseaseF;

    @FXML
    private TextField contactnoF;

    @FXML
    private ComboBox<String> appCbox;

    @FXML
    private ComboBox<String> labCbox;

    @FXML
    private ComboBox<String> genderCbox;

    private ObservableList<Patient> data = FXCollections.observableArrayList();

    private Session session;
    @FXML
    private TableView<Patient> table;

    @FXML
    private TableColumn<Patient, String> pidColumn;

    @FXML
    private TableColumn<Patient, String> nameColumn;

    @FXML
    private TableColumn<Patient, Integer> ageColumn;

    @FXML
    private TableColumn<Patient, Integer> weightColumn;

    @FXML
    private TableColumn<Patient, String> genderColumn;

    @FXML
    private TableColumn<Patient, String> addressColumn;

    @FXML
    private TableColumn<Patient, String> dridColumn;

    @FXML
    private TableColumn<Patient, String> diseaseColumn;

    @FXML
    private TableColumn<Patient, String> contactnoColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ageF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        weightF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        contactnoF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());

        genderCbox.getItems().addAll("male", "female");

        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        pidColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        weightColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setCellFactory(ComboBoxTableCell.forTableColumn("male", "female"));

        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        contactnoColumn.setCellValueFactory(new PropertyValueFactory<>("contactno"));
        contactnoColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        dridColumn.setCellValueFactory(new PropertyValueFactory<>("drid"));
        dridColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        diseaseColumn.setCellValueFactory(new PropertyValueFactory<>("disease"));
        diseaseColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        data.addListener(new ListChangeListener() {

            @Override
            public void onChanged(ListChangeListener.Change c) {
                table.getItems().setAll(data);
            }
        });
    }

    @FXML
    private void handleAddAction() {

        String pid = pidF.getText().trim();
        String drid = dridF.getText().trim();

        String gender = genderCbox.getSelectionModel().getSelectedItem();
        String name = nameF.getText().trim();
        String age = ageF.getText().trim();
        String weight = weightF.getText().trim();
        String disease = diseaseF.getText().trim();
        String contactno = contactnoF.getText().trim();
        String address = addressF.getText().trim();

        if (!pid.startsWith("p")) {
            ShowAlerts.showErrorAlert("Patients", "Error in Patient Id", "Patient ID must start with letter 'P'");
            return;
        }

        if (pid.equals("") || drid.equals("") || gender.equals("") || name.equals("") || age.equals("")
                || contactno.equals("") || address.equals("") || disease.equals("") || weight.equals("")) {
            ShowAlerts.showWarningAlert("Lab", "Warning", "Please fill out all fields");

        } else {
            add(pid, name, age, weight, gender, address, drid, disease, contactno);
            clearFields();
        }
    }

    private void add(String pid, String name, String age, String weight, String gender, String address, String drid, String disease, String contactno) {
        try {
            session = HibernateUtils.openSession();

            session.beginTransaction();

            Patient patient = new Patient(pid, name, Integer.valueOf(age), Integer.valueOf(weight), gender, address, drid, disease, contactno);

            session.persist(patient);
            data.add(patient);

            session.getTransaction().commit();

            session.close();
            ShowAlerts.showInformationAlert("Patients", "Information", "Patient ID:" + patient.getPid() + " successfully added");
        } catch (ConstraintViolationException ex) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showInformationAlert("Patient", "Duplicate Patient ID found",
                    "Please enter different Patient ID");

        } catch (DataException exe) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showErrorAlert("Patient", "Error", "Invalid data entered");
        }
    }
//--------------------------------------------------------------------------------

    @FXML
    private void handleClearAction(ActionEvent e) {
        clearFields();
    }

    private void clearFields() {
        pidF.clear();
        nameF.clear();
        ageF.clear();
        dridF.clear();
        addressF.clear();
        weightF.clear();
        genderCbox.valueProperty().set(null);
        diseaseF.clear();
        contactnoF.clear();
    }
//-----------------------------------------------------------------------------

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showWarningAlert("Patients", "Warning", "Please select a patient");
            return;
        }

        Alert alert = ShowAlerts.getAlert();
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Patient");
        alert.setContentText("This action will delete the selected patient record.Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                delete();
            }
        });
    }

    private void delete() {
        Patient patient = table.getSelectionModel().getSelectedItem();
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Patient WHERE pid = :id");
        query.setParameter("id", patient.getPid());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        data.remove(patient);
        ShowAlerts.showInformationAlert("Patients", "Information", "Deleted Successfully! Rows Effected: " + result);

    }
//------------------------------------------------------------------------------

    @FXML
    private void handleSearchAction(ActionEvent event) {
        String pid = pidF.getText().trim();
        if (pid.equals("")) {
            ShowAlerts.showErrorAlert("Patients", "Error", "Please enter patient id to search");

        } else {
            search(pid);
        }
    }

    public void search(String pid) {
        session = HibernateUtils.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Patient WHERE pid = :id");
        query.setParameter("id", pid);
        List<Patient> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
    }

    //-------------------------------------------------------------------------
    @FXML
    private void handleShowAction(ActionEvent event) {
        show();
    }

    private void show() {

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Patient");
        List<Patient> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();

    }

    @FXML
    private void handleNameEditCommit(TableColumn.CellEditEvent<Patient, String> t) {
        ((Patient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setName(t.getNewValue());

        Patient patient = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Patient SET name = :c"
                + " WHERE pid = :id");
        query.setParameter("c", t.getNewValue());
        query.setParameter("id", patient.getPid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Patient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleAgeEditCommit(TableColumn.CellEditEvent<Patient, Integer> t) {
        ((Patient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setAge(t.getNewValue());

        Patient patient = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Patient SET age = :c"
                + " WHERE pid = :id");
        query.setParameter("c", t.getNewValue());
        query.setParameter("id", patient.getPid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Patient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleWeightEditCommit(TableColumn.CellEditEvent<Patient, Integer> t) {
        ((Patient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setWeight(t.getNewValue());

        Patient patient = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Patient SET weight = :c"
                + " WHERE pid = :id");
        query.setParameter("c", t.getNewValue());
        query.setParameter("id", patient.getPid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Patient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleGenderEditCommit(TableColumn.CellEditEvent<Patient, String> t) {
        ((Patient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setGender(t.getNewValue());

        Patient patient = table.getSelectionModel().getSelectedItem();
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Patient SET gender = :c"
                + " WHERE pid = :id");
        query.setParameter("c", t.getNewValue());
        query.setParameter("id", patient.getPid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Patient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleAddressEditCommit(TableColumn.CellEditEvent<Patient, String> t) {
        ((Patient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setAddress(t.getNewValue());

        Patient patient = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Patient SET address = :c"
                + " WHERE pid = :id");
        query.setParameter("c", t.getNewValue());
        query.setParameter("id", patient.getPid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Patient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleDoctorEditCommit(TableColumn.CellEditEvent<Patient, String> t) {
        ((Patient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDrid(t.getNewValue());

        Patient patient = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Patient SET drid = :c"
                + " WHERE pid = :id");
        query.setParameter("c", t.getNewValue());
        query.setParameter("id", patient.getPid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Patient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleDiseaseEditCommit(TableColumn.CellEditEvent<Patient, String> t) {
        ((Patient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDisease(t.getNewValue());

        Patient patient = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Patient SET disease = :c"
                + " WHERE pid = :id");
        query.setParameter("c", t.getNewValue());
        query.setParameter("id", patient.getPid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Patient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleContactnoEditCommit(TableColumn.CellEditEvent<Patient, String> t) {
        ((Patient) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setContactno(t.getNewValue());

        Patient patient = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Patient SET contactno = :c"
                + " WHERE pid = :id");
        query.setParameter("c", t.getNewValue());
        query.setParameter("id", patient.getPid());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Patient", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

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
            ShowAlerts.showErrorAlert("Patients", "Error", "Please select a patient to show its appointments");
            clearFields();
            return;
        }
        Patient patient = table.getSelectionModel().getSelectedItem();
        Set<Appointments> appSet = patient.getAppointments();
        List<Appointments> appIds = new ArrayList();
        appIds.addAll(appSet);
        appCbox.getItems().setAll(appIds.stream().map(Appointments::getAno).collect(toList()));
    }

    @FXML
    private void handleOnShownLab() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("Patients", "Error", "Please select a patient to show its labs");
            clearFields();
            return;
        }
        Patient patient = table.getSelectionModel().getSelectedItem();
        Set<Lab> labset = patient.getLab();
        List<Lab> lablist = new ArrayList();
        lablist.addAll(labset);
        labCbox.getItems().setAll(lablist.stream().map(Lab::getLabno).collect(toList()));
    }
}
