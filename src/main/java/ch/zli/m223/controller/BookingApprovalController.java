package ch.zli.m223.controller;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.Operation;

import ch.zli.m223.service.BookingApprovalService;
import ch.zli.m223.model.BookingApproval;

@Path("/bookingapproval")
@Tag(name = "BookingApproval", description = "Handling of approvals")
@RolesAllowed({ "Admin" })
public class BookingApprovalController {
    
    @Inject
    BookingApprovalService bookingApprovalService;

    @Path("/{id}")
    @PUT
    @Operation(
        summary = "Update an approval",
        description = "Update an approval by its id"
    )
    public BookingApproval update(@PathParam("id") Long id, BookingApproval bookingApproval) {
        return bookingApprovalService.updateBookingApproval(id, bookingApproval);
    }
}
