package ch.zli.m223.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(readOnly = true)
    private Long id;

    @Column(nullable = false)
    LocalDate date;

    @ManyToOne(optional = false)
    @Fetch(FetchMode.JOIN)
    private CwSUser cwSUser;

    @ManyToOne(optional = false)
    @Fetch(FetchMode.JOIN)
    private BookingApproval bookingApproval;

    public BookingApproval getBookingApproval() {
        return bookingApproval;
    }

    public void setBookingApproval(BookingApproval bookingApproval) {
        this.bookingApproval = bookingApproval;
    }

    public CwSUser getCwSUser() {
        return cwSUser;
    }

    public void setCwSUser(CwSUser cwSUser) {
        this.cwSUser = cwSUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setBookingApproval(boolean b) {
    }
}
