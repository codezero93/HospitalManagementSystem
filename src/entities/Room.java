package entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room {

    private String roomno;
    private String room_type;
    private String status;
    private Set<Inpatient> inpatient = new HashSet<>();

    public Room() {
    }

    public Room(String roomno, String room_type, String status) {
        this.roomno = roomno;
        this.room_type = room_type;
        this.status = status;
    }

    @Id
    @Column(name = "roomno")
    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    @Column(name = "room_type")
    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    public Set<Inpatient> getInpatient() {
        return inpatient;
    }

    public void setInpatient(Set<Inpatient> inpatients) {
        this.inpatient = inpatients;
    }

}
