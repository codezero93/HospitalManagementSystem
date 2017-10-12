/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.screens.controllers;

import entities.Bill;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Sunny
 */
public class VoucherDialogController implements Initializable {

    private String billno;
    private String pid;
    private String ptype;

    private String doctorCharge;

    private String medicineCharge;

    private String roomCharge;

    private String operationCharge;

    private String nursingCharge;

    private String advance;

    private String labCharge;

    private String totalBill;

    @FXML
    private Label bnoL;

    @FXML
    private Label pidL;

    @FXML
    private Label ptypeL;

    @FXML
    private Label dcL;

    @FXML
    private Label mcL;

    @FXML
    private Label rcL;

    @FXML
    private Label ocL;

    @FXML
    private Label ncL;

    @FXML
    private Label lcL;

    @FXML
    private Label advanceL;

    @FXML
    private Label totalL;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    private Bill bill;

    public VoucherDialogController(Bill bill) {
        this.bill = bill;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        advanceL.setText(bill.getAdvance().toString());
        dcL.setText(bill.getDoctorCharge().toString());
        bnoL.setText(bill.getBillno());
        pidL.setText(bill.getPid());
        ptypeL.setText(bill.getPatientType());
        rcL.setText(bill.getRoomCharge().toString());
        ncL.setText(bill.getNursingCharge().toString());
        mcL.setText(bill.getMedicineCharge().toString());
        lcL.setText(bill.getLabCharge().toString());
        totalL.setText(bill.getTotal().toString());
        ocL.setText(bill.getOprtnCharge().toString());
        dateLabel.setText(LocalDate.now().toString());
        timeLabel.setText(LocalTime.now().toString());
    }
}
