package at.htl.eventmanager.boundary;

import at.htl.eventmanager.entity.Engagement;
import at.htl.eventmanager.entity.Event;
import at.htl.eventmanager.entity.Sponsorship;
import at.htl.eventmanager.repository.EventRepository;
import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.inject.DozerBeanContainer;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Path("api")
public class EventService {
    @Inject
    EventRepository eventRepository;

    public EventService() {
    }

    @Operation(
            summary = "Gets one Event title",
            description = "Gets the title of the Event that is searched for with the PathParam"
    )
    @GET
    @Path("event/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findEvent(@PathParam int id) {
        return Response.ok(eventRepository.getById((long) id))
                .build();
    }

    @Operation(
            summary = "Creates a new Event",
            description = "Creates and inserts a new Event with given Params, the Event object is being returned"
    )
    @POST
    @Path("event/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postTitle(Event event) {
        eventRepository.create(event);
        return Response.accepted(event).build();
    }

    @Operation(
            summary = "Gets all Events",
            description = "Gets all Events from a list in the EventRepository"
    )
    @GET
    @Path("event")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Event> eventArray() {
        return eventRepository.getAll();
    }

    @Operation(
            summary = "Deletes Event",
            description = "Deletes a specific Event and return noContent"
    )
    @DELETE
    @Path("event/{id}")
    public Response deleteEvent(@PathParam int id) {
        eventRepository.deleteEvent((long) id);
        return Response.noContent().build();
    }

    @Operation(
            summary = "Updates title",
            description = "Updates the title of a specific Event"
    )
    @PUT
    @Path("event/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEvent(@PathParam String title, Event event) {
        if(eventRepository.getByTitle(title) != null) {
            eventRepository.updateEvent(event);
            return Response.ok().build();
        } else {
            return Response.status(400).header("reason", "this event/title doesn't exist yet").build();
        }
    }
}
