package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {
    private int id;
    private float latitude;
    private float longitude;

    public Location() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    @Column
    public float getLatitude() {
        return latitude;
    }

    @Column
    public float getLongitude() {
        return longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
