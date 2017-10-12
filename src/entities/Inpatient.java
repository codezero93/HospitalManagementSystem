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
@Table(name = "inpatient")
public class Inpatient {

    private Room room;
    private LocalDate dateofadm;
    private LocalDate dateofdis;
    private StringProperty pid = new SimpleStringProperty();
    private StringProperty roomno = new SimpleStringProperty();
    private StringProperty labno = new SimpleStringProperty();
    private Set<Bill> bills = new HashSet<>();
    private Patient patient;

    private Lab lab;

    public Inpatient() {

    }

    public Inpatient(String pid, String roomno, String labno, LocalDate dateofadm, LocalDate dateofdis) {
        this.pid.set(pid);
        this.roomno.set(roomno);
        this.labno.set(labno);
        this.dateofadm = dateofadm;
        this.dateofdis = dateofdis;
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

    @Column(name = "roomno")
    public String getRoomno() {
        return roomno.get();
    }

    public void setRoomno(String value) {
        roomno.set(value);
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

    @Column(name = "dateofadm")
    public LocalDate getDateofadm() {
        return dateofadm;
    }

    public void setDateofadm(LocalDate dateofadm) {
        this.dateofadm = dateofadm;
    }

    @Column(name = "dateofdis")
    public LocalDate getDateofdis() {
        return dateofdis;
    }

    public void setDateofdis(LocalDate dateofdis) {
        this.dateofdis = dateofdis;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "roomno")
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room rooms) {
        this.room = room;
    }

    @OneToMany(mappedBy = "inpatient", fetch = FetchType.EAGER)
    public Set<Bill> getBills() {
        return this.bills;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
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
