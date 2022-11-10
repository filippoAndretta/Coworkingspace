package ch.zli.m223.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.BookingApproval;

@ApplicationScoped
public class BookingApprovalService {
    @Inject
    EntityManager entityManager;

    @Transactional
    public BookingApproval updateBookingApproval(Long id, BookingApproval bookingApproval) {
        bookingApproval.setId(id);
        return entityManager.merge(bookingApproval);
    }
}
