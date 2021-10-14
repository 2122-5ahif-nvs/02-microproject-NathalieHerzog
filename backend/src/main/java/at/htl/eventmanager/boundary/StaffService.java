package at.htl.eventmanager.boundary;

import at.htl.eventmanager.entity.Staff;
import at.htl.eventmanager.repository.StaffRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("api")
public class StaffService {
    @Inject
    StaffRepository staffRepository;

    public StaffService() {
    }

    @Operation(
            summary = "Gets one Staff id",
            description = "Gets the id of the Staff that is searched for with the PathParam"
    )
    @GET
    @Path("staff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findStaff(@PathParam int id) {
        return Response.ok(staffRepository.getById((long) id))
                .build();
    }

    @Operation(
            summary = "Creates a new Staff",
            description = "Creates and inserts a new Staff with given Params, the Staff object is being returned"
    )
    @POST
    @Path("staff/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postStaff(Staff staff) {
        staffRepository.create(staff);
        return Response.ok(staff).build();
    }

    @Operation(
            summary = "Gets all Staffs",
            description = "Gets all Staff from a list in the StaffRepository"
    )
    @GET
    @Path("staff/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Staff> getAllStaff() {
        return staffRepository.getAll();
    }

    @Operation(
            summary = "Deletes Staff",
            description = "Deletes a specific Staff and return noContent"
    )
    @DELETE
    @Path("staff/{id}")
    public Response deleteStaff(@PathParam int id) {
        if(staffRepository.findById((long) id) != null) {
            staffRepository.deleteStaff((long) id);
            return Response.noContent().build();
        }else {
            return Response.status(400).header("reason", "id couldn't be found").build();
        }
    }

    @Operation(
            summary = "Updates id",
            description = "Updates the id of a specific Staff"
    )
    @PUT
    @Path("staff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStaff(@PathParam int id, Staff staff) {
        staffRepository.updateStaff(staff);
        return Response.ok(staff).build();
    }
}
