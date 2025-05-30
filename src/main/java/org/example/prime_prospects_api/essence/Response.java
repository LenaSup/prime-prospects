package org.example.prime_prospects_api.essence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;


@Entity
@Table(name = "response")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "in_id", nullable = false)
    private Long in;

    @Column(name = "out_id", nullable = false)
    private Long out;

    @Column(name = "application_date", nullable = false)
    private LocalDateTime applicationDate;

    @Column(name = "accept")
    private boolean accept;

    @Column(name = "status", nullable = false)
    private boolean status;

    public String toJson(){
        return  "{"
                + "\"id\":\"" + id + "\","
                + "\"status\":\"" + status + "\","
                +  "\"accept\":\"" + accept + "\","
                + "\"application_date\":\"" + applicationDate + "\","
                +  "\"in_id\":\"" + in + "\","
                + "\"out_id\":\"" + out + "\","
                + "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIn() {
        return in;
    }

    public void setIn(Long in) {
        this.in = in;
    }

    public Long getOut() {
        return out;
    }

    public void setOut(Long out) {
        this.out = out;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", inId=" + in +
                ", outId=" + out +
                ", applicationDate=" + applicationDate +
                ", status='" + status +
                '}';
    }
}
