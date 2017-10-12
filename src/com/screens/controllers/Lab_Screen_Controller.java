package com.screens.controllers;

import com.Alerts.ShowAlerts;
import com.hibernate.HibernateUtils;

import com.utils.DatePickerTableCell;
import com.utils.EntitiesList;
import entities.Inpatient;

import entities.Lab;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.IntegerStringConverter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

public class Lab_Screen_Controller implements Initializable {
    
    @FXML
    private ComboBox inpCbox;
    @FXML
    private ComboBox oupCbox;
    
    @FXML
    ProgressIndicator pIndicator;
    
    @FXML
    TextField labnoF;
    
    @FXML
    DatePicker dateP = new DatePicker();
    
    @FXML
    TextField categoryF;
    
    @FXML
    TextField amountF;
    
    ObservableList<Lab> data = FXCollections.observableArrayList();
    
    @FXML
    TableView<Lab> table;
    
    @FXML
    TableColumn<Lab, String> labnoColumn;
    
    @FXML
    TableColumn<Lab, String> pidColumn;
    
    @FXML
    TableColumn<Lab, String> didColumn;
    
    @FXML
    TableColumn<Lab, LocalDate> dateColumn;
    
    @FXML
    TableColumn<Lab, String> categoryColumn;
    
    @FXML
    TableColumn<Lab, Integer> amountColumn;
    
    @FXML
    private ComboBox<String> pidCbox;
    
    @FXML
    private ComboBox<String> docCbox;
    
    private Session session;
    
    @FXML
    private void handleAddAction(ActionEvent e) {
        String labno = labnoF.getText().trim();
        String pid = pidCbox.getSelectionModel().getSelectedItem();
        String drid = docCbox.getSelectionModel().getSelectedItem();
        LocalDate date = dateP.getValue();
        
        
        
        
        String category = categoryF.getText().trim();
        String amount = amountF.getText().trim();
        
        if (labno.equals("") || pid.equals("") || drid.equals("") || date.equals("") || category.equals("") || amount.equals("")) {
            ShowAlerts.showWarningAlert("Lab", "Warning", "Please fill out all fields");
            
        } else {
            add(labno, pid, drid, date, category, amount);
            clearFields();
        }
    }
    
    @FXML
    private void handleDeleteAction(ActionEvent e) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showWarningAlert("Lab", "Warning", "Please select a lab record");
            return;
        }
        
        Alert alert = ShowAlerts.getAlert();
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lab");
        alert.setContentText("This action will delete the selected lab record.Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                delete();
            } else {
                
            }
        });
    }
    
    @FXML
    private void handleSearchAction(ActionEvent e) {
        String labno = labnoF.getText().trim();
        if (labno.equals("")) {
            ShowAlerts.showErrorAlert("Lab", "Error", "Please enter labno to search");
            
        } else {
            search(labno);
        }
        
    }
    
    @FXML
    private void handleClearAction(ActionEvent e) {
        clearFields();
    }
    
    @FXML
    private void handleShowAction() {
        showAll();
    }
    
    @FXML
    private void handleDateEditCommit(TableColumn.CellEditEvent<Lab, LocalDate> t) {
        ((Lab) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDate(t.getNewValue());
        
        Lab lab = table.getSelectionModel().getSelectedItem();
        
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Lab SET date = :dt"
                + " WHERE labno = :tno");
        query.setParameter("dt", t.getNewValue());
        query.setParameter("tno", lab.getLabno());
        int result = query.executeUpdate();
        
        session.getTransaction().commit();
        session.close();
        
        ShowAlerts.showInformationAlert("Lab", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));
        
    }
    
    @FXML
    private void handleCategoryEditCommit(TableColumn.CellEditEvent<Lab, String> t) {
        ((Lab) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setCategory(t.getNewValue());
        
        Lab lab = table.getSelectionModel().getSelectedItem();
        
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Lab SET category = :ct"
                + " WHERE labno = :tno");
        query.setParameter("ct", t.getNewValue());
        query.setParameter("tno", lab.getLabno());
        int result = query.executeUpdate();
        
        session.getTransaction().commit();
        session.close();
        
        ShowAlerts.showInformationAlert("Lab", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));
        
    }
    
    @FXML
    private void handleAmountEditCommit(TableColumn.CellEditEvent<Lab, Integer> t) {
        ((Lab) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setAmount(t.getNewValue());
        
        Lab lab = table.getSelectionModel().getSelectedItem();
        
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Lab SET amount = :am"
                + " WHERE labno = :tno");
        query.setParameter("am", t.getNewValue());
        query.setParameter("tno", lab.getLabno());
        int result = query.executeUpdate();
        
        session.getTransaction().commit();
        session.close();
        
        ShowAlerts.showInformationAlert("Lab", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amountF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        
        table.setEditable(true);
        
        labnoColumn.setCellValueFactory(new PropertyValueFactory<>("labno"));
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        didColumn.setCellValueFactory(new PropertyValueFactory<>("drid"));
        
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setCellFactory(DatePickerTableCell.<Lab>forTableColumn());
        
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        data.addListener(new ListChangeListener() {
            
            @Override
            public void onChanged(ListChangeListener.Change c) {
                table.getItems().setAll(data);
            }
        });
    }
    
    public void search(String labno) {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Lab WHERE labno = :no");
        query.setParameter("no", labno);
        List<Lab> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
        
    }
    
    public void add(String labno, String pid, String drid, LocalDate date, String category, String amount) {
        if (!labno.startsWith("l")) {
            ShowAlerts.showErrorAlert("Lab", "Error in labno", "A labno must start with a L letter");
            return;
        }
        
        try {
            session = HibernateUtils.openSession();
            session.beginTransaction();
            Lab labtest = new Lab(labno, pid, drid, date, category, Integer.valueOf(amount));
            session.persist(labtest);
            session.getTransaction().commit();
            session.close();
            data.add(labtest);
            ShowAlerts.showInformationAlert("Lab", "Information", "Testno " + " successfully added");
        } catch (ConstraintViolationException ex) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showInformationAlert("Lab", "TestNo must be unique", "Please enter different TestNo");
            
        } catch (DataException exe) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showErrorAlert("Lab", "Error", "Invalid data entered");
        }
        
    }
    
    public void delete() {
        
        Lab labtest = table.getSelectionModel().getSelectedItem();
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Lab WHERE labno = :no");
        query.setParameter("no", labtest.getLabno());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        data.remove(labtest);
        ShowAlerts.showInformationAlert("Lab", "Information", "Deleted Successfully! Rows Effected: " + result);
        
    }
    
    public void clearFields() {
        labnoF.clear();
        categoryF.clear();
        pidCbox.valueProperty().set(null);
        docCbox.valueProperty().set(null);
        amountF.clear();
        dateP.getEditor().clear();
        dateP.setValue(null);
        
    }
    
    public void showAll() {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Lab");
        List<Lab> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
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
    private void handleOnShownInpatient() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("Lab", "Error", "Please select a lab test");
            clearFields();
            return;
        }
        Lab lab = table.getSelectionModel().getSelectedItem();
        Set<Inpatient> inpSet = lab.getInpatient();
        List<Inpatient> inpList = new ArrayList();
        inpList.addAll(inpSet);
        inpCbox.getItems().setAll(inpList.stream().map(Inpatient::getPid).collect(toList()));
    }
    
    @FXML
    private void handleOnShownOutpatient() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("Lab", "Error", "Please select a lab test");
            clearFields();
            return;
        }
        Lab lab = table.getSelectionModel().getSelectedItem();
        Set<Outpatient> oupSet = lab.getOutpatient();
        List<Outpatient> oupList = new ArrayList();
        oupList.addAll(oupSet);
        oupCbox.getItems().setAll(oupList.stream().map(Outpatient::getPid).collect(toList()));
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
