package com.screens.controllers;

import com.Alerts.ShowAlerts;
import com.hibernate.HibernateUtils;
import com.utils.EntitiesList;
import entities.Bill;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

public class Bill_Screen_Controller implements Initializable {

    @FXML
    private TextField billnoF;

  

    @FXML
    private ComboBox<String> ptypeCbox;

    @FXML
    private TextField doctorchargeF;

    @FXML
    private TextField medicinechargeF;

    @FXML
    private TextField roomchargeF;

    @FXML
    private TextField operationchargeF;

    @FXML
    private TextField noofdaysF;

    @FXML
    private TextField nursingchargeF;

    @FXML
    private TextField advanceF;

    @FXML
    private TextField labchargeF;

    @FXML
    private TextField totalF;

    @FXML
    private TableColumn<Bill, String> billnoColumn;

    @FXML
    private TableColumn<Bill, String> pidColumn;

    @FXML
    private TableColumn<Bill, String> ptypeColumn;

    @FXML
    private TableColumn<Bill, Integer> doctorchargeColumn;

    @FXML
    private TableColumn<Bill, Integer> medicinechargeColumn;

    @FXML
    private TableColumn<Bill, Integer> roomchargeColumn;

    @FXML
    private TableColumn<Bill, Integer> operationchargeColumn;

    @FXML
    private TableColumn<Bill, Integer> nursingchargeColumn;

    @FXML
    private TableColumn<Bill, Integer> advanceColumn;

    @FXML
    private TableColumn<Bill, Integer> labchargeColumn;

    @FXML
    private TableColumn<Bill, Integer> totalColumn;

    @FXML
    private TableColumn<Bill, Integer> noofdaysColumn;

    @FXML
    private ComboBox<String> pidCbox;

    @FXML
    private TableView<Bill> table;

    private Session session;
    private StringProperty totalP = new SimpleStringProperty();
    private ObservableList<Bill> data = FXCollections.observableArrayList();

    IntegerProperty dcI = new SimpleIntegerProperty();
    IntegerProperty ocI = new SimpleIntegerProperty();
    IntegerProperty mcI = new SimpleIntegerProperty();
    IntegerProperty rcI = new SimpleIntegerProperty();
    IntegerProperty ncI = new SimpleIntegerProperty();
    IntegerProperty lcI = new SimpleIntegerProperty();
    IntegerProperty adv = new SimpleIntegerProperty();
    IntegerProperty totalbill = new SimpleIntegerProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        totalF.setDisable(true);
        ptypeCbox.getItems().addAll("inpatient", "outpatient");
        table.setEditable(true);

        billnoColumn.setCellValueFactory(new PropertyValueFactory<>("billno"));
        billnoColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        pidColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        ptypeColumn.setCellValueFactory(new PropertyValueFactory<>("patientType"));
        ptypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        doctorchargeColumn.setCellValueFactory(new PropertyValueFactory<>("doctorCharge"));
        doctorchargeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        medicinechargeColumn.setCellValueFactory(new PropertyValueFactory<>("medicineCharge"));
        medicinechargeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        roomchargeColumn.setCellValueFactory(new PropertyValueFactory<>("roomCharge"));
        roomchargeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        operationchargeColumn.setCellValueFactory(new PropertyValueFactory<>("oprtnCharge"));
        operationchargeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        noofdaysColumn.setCellValueFactory(new PropertyValueFactory<>("noofdays"));
        noofdaysColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        nursingchargeColumn.setCellValueFactory(new PropertyValueFactory<>("nursingCharge"));
        nursingchargeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        advanceColumn.setCellValueFactory(new PropertyValueFactory<>("advance"));
        advanceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        labchargeColumn.setCellValueFactory(new PropertyValueFactory<>("labCharge"));
        labchargeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        data.addListener(new ListChangeListener() {

            @Override
            public void onChanged(ListChangeListener.Change c) {
                table.getItems().setAll(data);
            }
        });

        doctorchargeF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                doctorchargeF.setText("0");
            } else {
                dcI.set(Integer.valueOf(newValue));
            }
        });

        roomchargeF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                roomchargeF.setText("0");
            } else {
                rcI.set(Integer.valueOf(newValue));
            }
        });

        operationchargeF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                operationchargeF.setText("0");
            } else {
                ocI.set(Integer.valueOf(newValue));
            }
        });

        labchargeF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                labchargeF.setText("0");
            } else {
                lcI.set(Integer.valueOf(newValue));
            }
        });

        nursingchargeF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                nursingchargeF.setText("0");
            } else {
                ncI.set(Integer.valueOf(newValue));
            }
        });

        medicinechargeF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                medicinechargeF.setText("");
            } else {
                mcI.set(Integer.valueOf(newValue));
            }
        });

        advanceF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                advanceF.setText("0");
            } else {
                adv.set(Integer.valueOf(newValue));
            }

        });

        totalbill.bind(dcI.add(rcI).add(ocI).add(lcI).add(ncI).add(mcI).add(adv));
        totalbill.addListener((observable, oldValue, newValue) -> {
            totalP.setValue(newValue.toString());
        });

        totalF.textProperty().bind(totalP);

        advanceF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        doctorchargeF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        labchargeF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        medicinechargeF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        nursingchargeF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        operationchargeF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());
        roomchargeF.addEventFilter(KeyEvent.KEY_TYPED, new KeyFilter());

    }

    @FXML
    private void handleEditCommit(TableColumn.CellEditEvent<Bill, ?> t) {

    }

    @FXML
    private void handleAddAction(ActionEvent e) {
        String billno = billnoF.getText().trim();
        String pid = pidCbox.getSelectionModel().getSelectedItem();
        String ptype = ptypeCbox.getSelectionModel().getSelectedItem();
        String mc = medicinechargeF.getText().trim();
        String nc = nursingchargeF.getText().trim();
        String rc = roomchargeF.getText().trim();
        String lc = labchargeF.getText().trim();
        String advance = advanceF.getText().trim();
        String oc = operationchargeF.getText().trim();
//        String b = totalF.getText().trim();
        String dc = doctorchargeF.getText().trim();
        String noofdays = noofdaysF.getText().trim();

        if ((billno.equals("") || pid.equals("") || mc.equals("") || nc.equals("") || rc.equals("") || lc.equals("") || advance.equals("") || oc.equals("")
                || dc.equals("") || noofdays.equals("")) && ptypeCbox.getSelectionModel().getSelectedItem().equals("inpatient")) {
            ShowAlerts.showWarningAlert("Bill", "Warning", "Please fill out all fields");

        } else if ((billno.equals("") || pid.equals("") || dc.equals("") || mc.equals("") || advance.equals("")
                || lc.equals("")) && ptypeCbox.getSelectionModel().getSelectedItem().equals("outpatient")) {
            ShowAlerts.showWarningAlert("Bill", "Warning", "Please fill out all fields");

        } else {
            add(billno, pid, ptype, dc, mc, rc, oc, noofdays, nc, advance, lc);
            clearFields();

        }

    }

    private void add(String billno, String pid, String ptype, String dc, String mc, String rc, String oc, String noofdays, String nc, String advance, String lc) {
       if (!billno.startsWith("b")) {
            ShowAlerts.showErrorAlert("Bill", "Error in billno", "A billno must start with a B letter");
            return;
        }
        
        
        try {
            session = HibernateUtils.openSession();
            session.beginTransaction();
            Bill bill = new Bill();
            if (ptype.equals("inpatient")) {
                bill.setAdvance(Integer.valueOf(advance));
                bill.setBillno(billno);
                bill.setPid(pid);
                bill.setPatientType(ptype);
                bill.setDoctorCharge(Integer.valueOf(dc));
                bill.setMedicineCharge(Integer.valueOf(mc));
                bill.setTotal(totalbill.get());
                bill.setLabCharge(Integer.valueOf(lc));
                bill.setNoofdays(Integer.valueOf(noofdays));
                bill.setOprtnCharge(Integer.valueOf(oc));
                bill.setRoomCharge(Integer.valueOf(rc));
                bill.setNursingCharge(Integer.valueOf(nc));
                session.persist(bill);
                data.add(bill);
                session.getTransaction().commit();
            } else {
                bill.setAdvance(Integer.valueOf(advance));
                bill.setBillno(billno);
                bill.setPid(pid);
                bill.setPatientType(ptype);
                bill.setDoctorCharge(Integer.valueOf(dc));
                bill.setMedicineCharge(Integer.valueOf(mc));
                bill.setTotal(totalbill.get());
                bill.setLabCharge(Integer.valueOf(lc));
                bill.setNoofdays(0);
                bill.setOprtnCharge(0);
                bill.setRoomCharge(0);
                bill.setNursingCharge(0);
                session.persist(bill);
                data.add(bill);
                session.getTransaction().commit();
            }
            session.close();
            ShowAlerts.showInformationAlert("Bill", "Information", "This Bill " + "Successfully added");
        } catch (ConstraintViolationException ex) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showInformationAlert("Bill", "Invalid BillNo", "The billno is already entered. Please enter different billno ");

        } catch (DataException exe) {
            session.getTransaction().rollback();
            session.close();
            ShowAlerts.showErrorAlert("Bill", "Error", "Invalid data entered");
        }
    }
//------------------------------------------------------------------------------

    @FXML
    private void handleVoucherAction(ActionEvent e) throws IOException {
        if (table.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("Bill", "Error", "Please select a bill record");
            return;
        }
        Bill mybill = table.getSelectionModel().getSelectedItem();
        VoucherDialogController v = new VoucherDialogController(mybill);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/screens/Voucher_Dialog.fxml"));
        loader.setController(v);
        Pane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Payment Voucher");
        stage.getIcons().add(new Image("/pics/voucher.png"));
        stage.showAndWait();

        stage.centerOnScreen();

    }

//------------------------------------------------------------------------------
    @FXML
    private void handleSearchAction(ActionEvent e) {
        String billno = billnoF.getText().trim();
        if (billno.equals("")) {
            ShowAlerts.showErrorAlert("Bill", "Error", "Please enter billno to search");
        } else {
            search(billno);
        }
    }

    private void search(String billno) {
        try {
            session = HibernateUtils.openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM Bill WHERE billno = :no");
            query.setParameter("no", billno);
            List<Bill> list = query.list();
            session.getTransaction().commit();
            data.setAll(list);
        } catch (Exception e) {
            ShowAlerts.showErrorAlert("Bill", "Error", e.toString());
        }
    }
//------------------------------------------------------------------------------

    @FXML
    private void handleClearAction(ActionEvent e) {
        clearFields();
    }

    private void clearFields() {
        billnoF.clear();
      pidCbox.valueProperty().set(null);
        ptypeCbox.valueProperty().set(null);
        doctorchargeF.setText("0");
        medicinechargeF.setText("0");
        roomchargeF.setText("0");
        operationchargeF.setText("0");
        noofdaysF.clear();
        nursingchargeF.setText("0");
        advanceF.setText("0");
        labchargeF.setText("0");

        totalP.set(null);
        dcI.set(0);
        rcI.set(0);
        ocI.set(0);
        adv.set(0);
        ncI.set(0);
        lcI.set(0);
        mcI.set(0);

    }

    //------------------------------------------
    @FXML
    private void handleShowAction(ActionEvent e) {
        show();
    }

    private void show() {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Bill");
        List<Bill> list = query.list();
        session.getTransaction().commit();
        data.setAll(list);
        session.close();
    }

    @FXML
    private void handlePtypeAction(ActionEvent e) {
        if (ptypeCbox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (ptypeCbox.getSelectionModel().getSelectedItem().equalsIgnoreCase("outpatient")) {
            roomchargeF.setDisable(true);
            roomchargeF.textProperty().set("0");
            operationchargeF.setDisable(true);
            operationchargeF.textProperty().set("0");
            noofdaysF.setDisable(true);
            noofdaysF.setText("");
            nursingchargeF.setDisable(true);
            nursingchargeF.textProperty().set("0");

        } else {
            roomchargeF.setDisable(false);
            operationchargeF.setDisable(false);
            noofdaysF.setDisable(false);
            nursingchargeF.setDisable(false);
        }

    }

    @FXML
    private void handleDoctorChargeEditCommit(TableColumn.CellEditEvent<Bill, Integer> t) {
        ((Bill) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDoctorCharge(t.getNewValue());

        Bill bill = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Bill SET doctorCharge = :dc"
                + " WHERE billno = :bno");
        query.setParameter("dc", t.getNewValue());
        query.setParameter("bno", bill.getBillno());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Bill", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleMedicineChargeEditCommit(TableColumn.CellEditEvent<Bill, Integer> t) {
        ((Bill) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setMedicineCharge(t.getNewValue());

        Bill bill = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Bill SET medicineCharge = :c"
                + " WHERE billno = :bno");
        query.setParameter("c", t.getNewValue());
        query.setParameter("bno", bill.getBillno());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Bill", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleRoomChargeEditCommit(TableColumn.CellEditEvent<Bill, Integer> t) {
        roomchargeColumn.setEditable(true);
        Bill bill = table.getSelectionModel().getSelectedItem();
        if (bill.getPatientType().equalsIgnoreCase("outpatient")) {
            roomchargeColumn.setEditable(false);
            ShowAlerts.showErrorAlert("Bill", "Editing Error", "You cannot edit this field for outpatient");
            return;
        }

        ((Bill) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setRoomCharge(t.getNewValue());
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Bill SET roomCharge = :c"
                + " WHERE billno = :bno");
        query.setParameter("c", t.getNewValue());
        query.setParameter("bno", bill.getBillno());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Bill", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleOperationChargeEditCommit(TableColumn.CellEditEvent<Bill, Integer> t) {
        operationchargeColumn.setEditable(true);
        Bill bill = table.getSelectionModel().getSelectedItem();
        if (bill.getPatientType().equalsIgnoreCase("outpatient")) {
            operationchargeColumn.setEditable(false);
            ShowAlerts.showErrorAlert("Bill", "Editing Error", "You cannot edit this field for outpatient");
            return;
        }

        ((Bill) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setOprtnCharge(t.getNewValue());

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Bill SET oprtnCharge = :c"
                + " WHERE billno = :bno");
        query.setParameter("c", t.getNewValue());
        query.setParameter("bno", bill.getBillno());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Bill", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleNoOfDaysEditCommit(TableColumn.CellEditEvent<Bill, Integer> t) {
        noofdaysColumn.setEditable(true);
        Bill bill = table.getSelectionModel().getSelectedItem();
        if (bill.getPatientType().equalsIgnoreCase("outpatient")) {
            noofdaysColumn.setEditable(false);
            ShowAlerts.showErrorAlert("Bill", "Editing Error", "You cannot edit this field for outpatient");
            return;
        }

        ((Bill) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setNoofdays(t.getNewValue());

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Bill SET noofdays = :c"
                + " WHERE billno = :bno");
        query.setParameter("c", t.getNewValue());
        query.setParameter("bno", bill.getBillno());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Bill", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleNurseChargeEditCommit(TableColumn.CellEditEvent<Bill, Integer> t) {
        nursingchargeColumn.setEditable(true);
        Bill bill = table.getSelectionModel().getSelectedItem();
        if (bill.getPatientType().equalsIgnoreCase("outpatient")) {
            nursingchargeColumn.setEditable(false);
            ShowAlerts.showErrorAlert("Bill", "Editing Error", "You cannot edit this field for outpatient");
            return;
        }

        ((Bill) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setNursingCharge(t.getNewValue());

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Bill SET nursingCharge = :c"
                + " WHERE billno = :bno");
        query.setParameter("c", t.getNewValue());
        query.setParameter("bno", bill.getBillno());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Bill", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleAdvanceEditCommit(TableColumn.CellEditEvent<Bill, Integer> t) {
        ((Bill) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setAdvance(t.getNewValue());

        Bill bill = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Bill SET advance = :c"
                + " WHERE billno = :bno");
        query.setParameter("c", t.getNewValue());
        query.setParameter("bno", bill.getBillno());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Bill", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleLabChargeEditCommit(TableColumn.CellEditEvent<Bill, Integer> t) {
        ((Bill) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setLabCharge(t.getNewValue());

        Bill bill = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Bill SET labCharge = :c"
                + " WHERE billno = :bno");
        query.setParameter("c", t.getNewValue());
        query.setParameter("bno", bill.getBillno());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Bill", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

    @FXML
    private void handleTotalBillEditCommit(TableColumn.CellEditEvent<Bill, Integer> t) {
        ((Bill) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setTotal(t.getNewValue());

        Bill bill = table.getSelectionModel().getSelectedItem();

        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Bill SET bill = :c"
                + " WHERE billno = :bno");
        query.setParameter("c", t.getNewValue());
        query.setParameter("bno", bill.getBillno());
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        ShowAlerts.showInformationAlert("Bill", "Information", "Successfully Updated! RowsEffected:" + String.valueOf(result));

    }

 

    @FXML
    private void handlePidOnShown() {
        List<String> list = EntitiesList.getPatients();
        pidCbox.getItems().setAll(list);
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
