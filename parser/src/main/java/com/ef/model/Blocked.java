package com.ef.model;


import javax.persistence.*;

/**
 * Created by sergio.leottau on 1/10/17.
 */
@Entity
@Table(name = "blocked", schema = "parser"
        , uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class Blocked {

    /**
     * Row identification
     */
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    /**
     * IP
     */
    @Column(name = "ip", nullable = false)
    private String ip;

    /**
     * Reason of why the IP address is blocked
     */
    @Column(name = "observations", nullable = false)
    private String observations;

    /**
     * Default constructor
     */
    public Blocked() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
