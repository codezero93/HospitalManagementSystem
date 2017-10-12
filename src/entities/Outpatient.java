/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Sunny
 */
@Entity
@Table(name = "outpatient")
public class Outpatient {

    private StringProperty pid = new SimpleStringProperty();
    private LocalDate date;
    private StringProperty labno = new SimpleStringProperty();
    private Lab lab;
    private Set<Bill> bill = new HashSet<>();
    private Patient patient;

    @OneToMany(mappedBy = "outpatient", fetch = FetchType.EAGER)
    public Set<Bill> getBill() {
        return bill;
    }

    public void setBill(Set<Bill> bill) {
        this.bill = bill;
    }

    public Outpatient() {

    }

    public Outpatient(String pid, String labno, LocalDate date) {
        this.pid.set(pid);
        this.date = date;
        this.labno.set(labno);
    }

    @Id
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

    @Column(name = "labno")
    public String getLabno() {
        return labno.get();
    }

    public void setLabno(String value) {
        labno.set(value);
    }

    public StringProperty labnoProperty() {
        return labno;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Column(name = "date")
    public LocalDate getDate() {
        return this.date;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "labno")
    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
