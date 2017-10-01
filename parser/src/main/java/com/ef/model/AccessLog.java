package com.ef.model;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sergio.leottau on 1/10/17.
 */
@Entity
@Table(name = "access_log", schema = "parser"
        , uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class AccessLog implements Serializable{

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
     * Acess log date
     */
    @Column(name = "date", nullable = false)
    private Date date;

    /**
     * Access log complete text
     */
    @Column(name = "log", nullable = false)
    private String log;

    public AccessLog() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
