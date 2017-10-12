/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lab")
public class Lab {

    private StringProperty labno = new SimpleStringProperty();
    private StringProperty pid = new SimpleStringProperty();
    private StringProperty drid = new SimpleStringProperty();
    private LocalDate date;
    private StringProperty category = new SimpleStringProperty();
    private Doctor doctor;
    private IntegerProperty amount = new SimpleIntegerProperty();
    private Patient patient;
    private Set<Inpatient> inpatient = new HashSet<>();
    private Set<Outpatient> outpatient = new HashSet<>();

    public Lab() {
    }

    public Lab(String labno, String pid, String drid, LocalDate date, String category, Integer amount) {
        this.labno.set(labno);
        this.pid.set(pid);
        this.drid.set(drid);
        this.date = date;
        this.category.set(category);

        this.amount.set(amount);
    }

    @Id
    @Column(name = "labno")
    public String getLabno() {
        return labno.get();
    }

    public void setLabno(String labno) {
        this.labno.set(labno);
    }

    @Column(name = "pid")
    public String getPid() {
        return pid.get();
    }

    public void setPid(String pid) {
        this.pid.set(pid);
    }

    @Column(name = "drid")
    public String getDrid() {
        return drid.get();
    }

    public void setDrid(String drid) {
        this.drid.set(drid);
    }

    @Column(name = "date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Column(name = "category")
    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    @Column(name = "amount")
    public Integer getAmount() {
        return amount.get();
    }

    public void setAmount(Integer amount) {
        this.amount.set(amount);
    }

    @OneToMany(mappedBy = "lab", fetch = FetchType.EAGER)
    public Set<Inpatient> getInpatient() {
        return inpatient;
    }

    public void setInpatient(Set<Inpatient> inpatient) {
        this.inpatient = inpatient;
    }

    @OneToMany(mappedBy = "lab", fetch = FetchType.EAGER)
    public Set<Outpatient> getOutpatient() {
        return outpatient;
    }

    public void setOutpatient(Set<Outpatient> outpatient) {
        this.outpatient = outpatient;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "pid")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "drid")
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

}
