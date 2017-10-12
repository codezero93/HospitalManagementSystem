package com.screens.controllers;

import com.Alerts.ShowAlerts;
import com.hibernate.HibernateUtils;
import entities.Inpatient;

import entities.Room;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
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
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

/**
 *
 * @author Olca
 */
public class Room_Screen_Controller implements Initializable {

    private ObservableList<Room> roomData = FXCollections.observableArrayList();

    @FXML
    ComboBox inpatientCbox;

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> roomnoColumn;

    @FXML
    private TableColumn<Room, String> roomtypeColumn;

    @FXML
    private TableColumn<Room, String> statusColumn;

    @FXML
    private TextField roomnoF;
    @FXML
    private TextField roomtypeF;
    @FXML
    private TextField statusF;

    private Session session;

    @FXML
    private void handleAddAction(ActionEvent e) {
        String roomno = roomnoF.getText().trim();
        String roomtype = roomtypeF.getText().trim();
        String status = statusF.getText().trim();

        if (!roomno.startsWith("d")) {
            ShowAlerts.showErrorAlert("Docotr", "Error in doctor id", "A Doctor id must start with a D letter");
            return;
        }

        if (roomno.equals("") || roomtype.equals("") || status.equals("")) {
            ShowAlerts.showWarningAlert("Room", "Error", "Please fill out all fields");
        }

        addRoomData(roomno, roomtype, status);
    }

    @FXML
    private void handleDeleteAction(ActionEvent e) {
        if (roomTable.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showWarningAlert("Room", "Warning", "Please select a row or record");
            return;
        }

        Alert alert = ShowAlerts.getAlert();
        alert.setAlertType(AlertType.CONFIRMATION);
        alert.setTitle("Rooms");
        alert.setContentText("This action will delete the selected room. Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                int result = deleteData();
                ShowAlerts.showInformationAlert("Room", "Information", "Successfully Deleted! Rows Effected:" + String.valueOf(result));

            }

        });

    }

    @FXML
    private void handleSearchAction(ActionEvent e) {
        String rno = roomnoF.getText().trim().toLowerCase();
        searchByRoomNo(rno);
    }

    @FXML
    private void handleShowAction(ActionEvent e) {
        showData();
    }

    @FXML
    private void handleClearAction(ActionEvent e) {
        clearFields();
    }

    private void clearFields() {
        roomnoF.clear();
        roomtypeF.clear();
        statusF.clear();
        inpatientCbox.valueProperty().set(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        roomTable.setEditable(true);
        roomnoColumn.setCellValueFactory(new PropertyValueFactory<>("roomno"));

        roomtypeColumn.setCellValueFactory(new PropertyValueFactory<>("room_type"));
        roomtypeColumn.setCellFactory(TextFieldTableCell.<Room>forTableColumn());
        roomtypeColumn.setOnEditCommit(t -> {
            ((Room) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setRoom_type(t.getNewValue());

            Room room = roomTable.getSelectionModel().getSelectedItem();

            session = HibernateUtils.openSession();
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Room SET room_type = :rt"
                    + " WHERE roomno = :rno");
            query.setParameter("rt", t.getNewValue());
            query.setParameter("rno", room.getRoomno());
            int result = query.executeUpdate();

            session.getTransaction().commit();
            session.close();

            ShowAlerts.showInformationAlert("Room", "Information", "Updated! RowsEffected:" + String.valueOf(result));

        });

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(TextFieldTableCell.<Room>forTableColumn());
        statusColumn.setOnEditCommit(t -> {

            ((Room) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setStatus(t.getNewValue());

            Room room = roomTable.getSelectionModel().getSelectedItem();

            session = HibernateUtils.openSession();
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Room SET status = :rt"
                    + " WHERE roomno = :rno");
            query.setParameter("rt", t.getNewValue());
            query.setParameter("rno", room.getRoomno());
            int result = query.executeUpdate();

            session.getTransaction().commit();
            session.close();

            ShowAlerts.showInformationAlert("Room", "Information", "Updated! RowsEffected:" + String.valueOf(result));
        });

        //change listener for dataRoom List to update contents in table
        roomData.addListener(new ListChangeListener() {

            @Override
            public void onChanged(ListChangeListener.Change c) {
                roomTable.getItems().setAll(roomData);
            }
        });
    }

    private void showData() {
        session = HibernateUtils.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Room");
        List<Room> list = query.list();
        roomData.setAll(list);

        session.getTransaction().commit();

        session.close();

    }

    private void addRoomData(String roomno, String roomtype, String status) {

        try {
            session = HibernateUtils.openSession();
            session.beginTransaction();
            Room room = new Room(roomno, roomtype, status);
            session.persist(room);
            roomData.add(room);
            session.getTransaction().commit();
            session.close();
        } catch (ConstraintViolationException ex) {
            ShowAlerts.showInformationAlert("Rooms", "Invalid RoomNo", "RoomNo found duplicate.RoomNo must be unique");
            session.getTransaction().rollback();
            session.close();
        } catch (DataException exe) {
            ShowAlerts.showErrorAlert("Rooms", "Error", "Invalid data entered");
            session.getTransaction().rollback();
            session.close();
        }

    }

    private int deleteData() {
        Room room = roomTable.getSelectionModel().getSelectedItem();
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Room WHERE roomno = :no");
        query.setParameter("no", room.getRoomno());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        roomData.remove(room);

        return result;
    }

    private void searchByRoomNo(String roomno) {
        session = HibernateUtils.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Room WHERE roomno = :rno");
        query.setParameter("rno", roomno);
        List<Room> list = query.list();
        session.getTransaction().commit();
        roomData.setAll(list);
        session.close();
    }

    @FXML
    private void handleOnShown() {
        if (roomTable.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("Room", "Error", "Please select a room");
            clearFields();
            return;
        }
        Room room = roomTable.getSelectionModel().getSelectedItem();
        Set<Inpatient> inpatientSet = room.getInpatient();
        List<Inpatient> inpatientIds = new ArrayList();
        inpatientIds.addAll(inpatientSet);
        inpatientCbox.getItems().setAll(inpatientIds.stream().map(Inpatient::getPid).collect(toList()));
    }

}
