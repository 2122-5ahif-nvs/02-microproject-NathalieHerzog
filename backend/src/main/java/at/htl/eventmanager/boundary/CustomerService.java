package at.htl.eventmanager.boundary;

import at.htl.eventmanager.entity.Customer;
import at.htl.eventmanager.entity.Engagement;
import at.htl.eventmanager.entity.Event;
import at.htl.eventmanager.entity.Sponsorship;
import at.htl.eventmanager.repository.CustomerRepository;
import cucumber.api.java.is.En;
import net.bytebuddy.asm.Advice;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Path("api")
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public CustomerService() {
    }

    @Operation(
            summary = "Gets one Customer id",
            description = "Gets the id of the Customer that is searched for with the PathParam"
    )
    @GET
    @Path("customer/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findCustomer(@PathParam int id) {
        return Response.ok(customerRepository.getById((long) id))
                .build();
    }

    @Operation(
            summary = "Updates username",
            description = "For updating the username of a specific Customer, but can also update e-mail"
    )
    @PATCH
    @Path("customer/username/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUsername(@PathParam int id, JsonObject eventJson) {
        Sponsorship sponsorship = new Sponsorship();
        Customer customer = new Customer(eventJson.getString("newUser"), eventJson.getString("newMail"), sponsorship);
        customer.setId((long) id);
        customerRepository.updateUserName(customer);
        return Response.ok().build();
    }

    @Operation(
            summary = "Updates e-mail",
            description = "For updating the e-mail of a specific Customer, but can also update username"
    )
    @PUT
    @Path("customer/email/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmail(@PathParam int id, JsonObject eventJson) {
        Sponsorship sponsorship = new Sponsorship();
        Customer customer = new Customer(eventJson.getString("newUser"), eventJson.getString("newMail"), sponsorship);
        customer.setId((long) id);
        customerRepository.updateEmail(customer);
        return Response.ok().build();
    }

    @Operation(
            summary = "Deletes Customer",
            description = "Deletes a specific Customer and return noContent"
    )
    @DELETE
    @Path("customer/{id}")
    public Response deleteCustomer(@PathParam long id) {
        customerRepository.deleteCustomer(id);
        return Response.noContent().build();
    }

    @Operation(
            summary = "Creates a new Customer",
            description = "Creates and inserts a new Customer with given Params, the Customer object is being returned"
    )
    @POST
    @Path("customer/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCustomer(Customer customer) {
        customerRepository.create(customer);
        return Response.accepted(customer).build();
    }

    @Operation(
            summary = "Creates a new Customer's Sponsorship",
            description = "Creates and inserts a new Sponsorship for a Customer"
    )
    @POST
    @Path("customer/sponsorship")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Sponsorship postSponsorship(JsonObject eventJson) {
        LocalDate startDate = LocalDate.parse(eventJson.getString("newStartDate"), formatter);
        LocalDate endDate = LocalDate.parse(eventJson.getString("newEndDate"), formatter);
        List<Sponsorship> sponsorships = new LinkedList<>();
        sponsorships.add(new Sponsorship(1L));
        List<Engagement> engagements = new LinkedList<>();
        engagements.add(new Engagement());

        Customer customer = new Customer(eventJson.getString("newCustomerUser"), eventJson.getString("newCustomerMail"));
        Event event = new Event(eventJson.getString("newEventTitle"), 2, startDate, endDate, sponsorships, engagements);

        return customerRepository.createSponsorship(new Sponsorship(event, customer, BigDecimal.valueOf(7000.0)));
    }

    @Operation(
            summary = "Gets all Customers",
            description = "Gets all Customers from a list in the CustomerRepository"
    )
    @GET
    @Path("customer/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Customer> customerArray() {
        return customerRepository.getAllCustomers();
    }

    @Operation(
            summary = "Gets Customer and Staff name",
            description = "Gets the name of the Customer and the Staff with given id"
    )
    @GET
    @Path("customer-staff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String customerStaff(@QueryParam("id") long id) {
        return customerRepository.customerStaffName(id);
    }
}
