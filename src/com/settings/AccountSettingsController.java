/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.settings;

import com.Alerts.ShowAlerts;
import com.hibernate.HibernateUtils;
import com.utils.EntitiesList;
import entities.Account;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

/**
 *
 * @author Sunny
 */
public class AccountSettingsController implements Initializable {

    private String uname;

    @FXML
    private ComboBox<String> usersCbox;

    @FXML
    PasswordField passF1;

    @FXML
    PasswordField passF2;

    @FXML
    PasswordField passF3;

    @FXML
    TextField unameF;

    @FXML
    PasswordField passF;

    @FXML
    Button changeButton;

    @FXML
    Button addButton;

    @FXML
    Button deleteButton;

    private Session session;

    public AccountSettingsController(String uname) {
        this.uname = uname;
    }

    @FXML
    private void handleChangeButton(ActionEvent e) {
        if (passF1.getText().equals("") || passF2.getText().equals("")) {
            ShowAlerts.showWarningAlert("AccountSettings", "Warning!", "Please fill out all fields");
            return;
        }
        change();

    }

    private void change() {
        session = HibernateUtils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Account SET password=:pass WHERE username=:uname AND password=:ps");
        query.setParameter("pass", passF3.getText().trim());
        query.setParameter("uname", this.uname);
        query.setParameter("ps", passF1.getText().trim());
        int result = query.executeUpdate();
        if (result == 0 || !(passF2.getText().equals(passF3.getText()))) {
            ShowAlerts.showErrorAlert("AccountSettings", "Error", "Invalid current password OR new passwords doesn't match");
            clearFields();
            session.getTransaction().rollback();
            session.close();

        } else {
            session.getTransaction().commit();
            session.close();
            ShowAlerts.showInformationAlert("AccountSettings", "Information", "Password Changed Successfully! Rows Effected :" + result);
        }
    }

    @FXML
    private void handleAddButton(ActionEvent e) {

        try {
            session = HibernateUtils.openSession();
            session.beginTransaction();

            Account account = new Account(unameF.getText().trim(), passF.getText().trim());
            session.save(account);

            session.getTransaction().commit();
            session.close();
            ShowAlerts.showInformationAlert("AccountSettings", "Information", "Successfully Saved!");
            clearFields();
        } catch (ConstraintViolationException cve) {
            ShowAlerts.showErrorAlert("AccountSettings", "Error", "The username is already taken");
        } catch (DataException de) {
            ShowAlerts.showErrorAlert("AccountSettings", "Error", "Username is too long");
            unameF.setStyle("-fx-border-color:red");
            clearFields();
        }
    }

    @FXML
    private void handleDeleteButton(ActionEvent e) {
        if (usersCbox.getSelectionModel().getSelectedItem() == null) {
            ShowAlerts.showErrorAlert("AccountSettings", "Error", "Please select a user from list");
            clearFields();
            return;
        }

        String userName = usersCbox.getSelectionModel().getSelectedItem();
        Alert alert = ShowAlerts.getAlert();
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Accounts");
        alert.setContentText("This action will delete this account.Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    session = HibernateUtils.openSession();
                    session.beginTransaction();
                    Query query = session.createQuery("DELETE FROM Account WHERE username=:uname");
                    query.setParameter("uname", userName);
                    int result = query.executeUpdate();
                    session.getTransaction().commit();
                    session.close();

                    ShowAlerts.showInformationAlert("AccountSettings", "Information", "Successfully Deleted: " + userName + "  Rows Effected: " + result);
                } catch (Exception ex) {
                    ShowAlerts.showErrorAlert("AccountSettings", "Error", ex.toString());

                }

            }
        });
        clearFields();
    }

    private void clearFields() {
        passF1.clear();
        passF2.clear();
        passF3.clear();
        passF.clear();
        unameF.clear();
        usersCbox.valueProperty().set(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!this.uname.equalsIgnoreCase("admin")) {
            unameF.setDisable(true);
            passF.setDisable(true);
            addButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }

    @FXML
    private void handleOnShownUsers() {
        List<String> list = EntitiesList.getUserAccounts();
        usersCbox.getItems().setAll(list);
    }
}
