/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bill")
public class Bill {

    private StringProperty billno = new SimpleStringProperty();
    private StringProperty pid = new SimpleStringProperty();
    private StringProperty patientType = new SimpleStringProperty();
    private IntegerProperty doctorCharge = new SimpleIntegerProperty();
    private IntegerProperty medicineCharge = new SimpleIntegerProperty();
    private IntegerProperty roomCharge = new SimpleIntegerProperty();
    private IntegerProperty oprtnCharge = new SimpleIntegerProperty();
    private IntegerProperty noofdays = new SimpleIntegerProperty();
    private IntegerProperty nursingCharge = new SimpleIntegerProperty();
    private IntegerProperty advance = new SimpleIntegerProperty();
    private IntegerProperty labCharge = new SimpleIntegerProperty();
    private IntegerProperty total = new SimpleIntegerProperty();
    private Inpatient inpatient;
    private Outpatient outpatient;

    @Id
    @Column(name = "billno")
    public String getBillno() {
        return billno.get();
    }

    public void setBillno(String value) {
        billno.set(value);
    }

    public StringProperty billnoProperty() {
        return billno;
    }

    @Column(name = "pid")
    public String getPid() {
        return pid.get();
    }

    public void setPid(String value) {
        pid.set(value);
    }

    public StringProperty pidProperty() {
        return pid;
    }

    @Column(name = "patientType")
    public String getPatientType() {
        return patientType.get();
    }

    public void setPatientType(String value) {
        patientType.set(value);
    }

    public StringProperty patientTypeProperty() {
        return patientType;
    }

    @Column(name = "doctorCharge")
    public Integer getDoctorCharge() {
        return doctorCharge.get();
    }

    public void setDoctorCharge(Integer value) {
        doctorCharge.set(value);
    }

    public IntegerProperty doctorChargeProperty() {
        return doctorCharge;
    }

    @Column(name = "medicineCharge")
    public Integer getMedicineCharge() {
        return medicineCharge.get();
    }

    public void setMedicineCharge(Integer value) {
        medicineCharge.set(value);
    }

    public IntegerProperty medicineChargeProperty() {
        return medicineCharge;
    }

    @Column(name = "roomCharge")
    public Integer getRoomCharge() {
        return roomCharge.get();
    }

    public void setRoomCharge(Integer value) {
        roomCharge.set(value);
    }

    public IntegerProperty roomChargeProperty() {
        return roomCharge;
    }

    @Column(name = "oprtnCharge")
    public Integer getOprtnCharge() {
        return oprtnCharge.get();
    }

    public void setOprtnCharge(Integer value) {
        oprtnCharge.set(value);
    }

    public IntegerProperty oprtnChargeProperty() {
        return oprtnCharge;
    }

    @Column(name = "noofdays")
    public Integer getNoofdays() {
        return noofdays.get();
    }

    public void setNoofdays(Integer value) {
        noofdays.set(value);
    }

    public IntegerProperty noofdaysProperty() {
        return noofdays;
    }

    @Column(name = "nursingCharge")
    public Integer getNursingCharge() {
        return nursingCharge.get();
    }

    public void setNursingCharge(Integer value) {
        nursingCharge.set(value);
    }

    public IntegerProperty nursingChargeProperty() {
        return nursingCharge;
    }

    @Column(name = "advance")
    public Integer getAdvance() {
        return advance.get();
    }

    public void setAdvance(Integer value) {
        advance.set(value);
    }

    public IntegerProperty advanceProperty() {
        return advance;
    }

    @Column(name = "labCharge")
    public Integer getLabCharge() {
        return labCharge.get();
    }

    public void setLabCharge(Integer value) {
        labCharge.set(value);
    }

    public IntegerProperty labChargeProperty() {
        return labCharge;
    }

    @Column(name = "total")
    public Integer getTotal() {
        return total.get();
    }

    public void setTotal(Integer value) {
        total.set(value);
    }

    public IntegerProperty totalProperty() {
        return total;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "pid")
    public Inpatient getInpatient() {
        return inpatient;
    }

    public void setInpatient(Inpatient inpatient) {
        this.inpatient = inpatient;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "pid")
    public Outpatient getOutpatient() {
        return outpatient;
    }

    public void setOutpatient(Outpatient outpatient) {
        this.outpatient = outpatient;
    }
}
