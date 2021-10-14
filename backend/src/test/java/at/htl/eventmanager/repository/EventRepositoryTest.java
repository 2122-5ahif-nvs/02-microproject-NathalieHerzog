package at.htl.eventmanager.repository;

import at.htl.eventmanager.entity.Engagement;
import at.htl.eventmanager.entity.Event;
import at.htl.eventmanager.entity.Sponsorship;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setMaxElementsForPrinting;

@QuarkusTest
public class EventRepositoryTest {
    @Inject
    EventRepository eventRepository;

    List<Sponsorship> sponsorships = new LinkedList<>();
    List<Engagement> engagements = new LinkedList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Test
    public void testCreateEvent() {
        eventRepository.clearTable();

        LocalDate startDate = LocalDate.parse("04-07-2020", formatter);
        LocalDate endDate = LocalDate.parse("05-07-2020", formatter);

        Event event = new Event("title1", 1, startDate, endDate, sponsorships, engagements);
        eventRepository.create(event);
        assertThat(eventRepository.countEvent()).isEqualTo(1);
    }

    @Test
    public void testUpdateEvent() {
        eventRepository.clearTable();

        LocalDate startDate = LocalDate.parse("04-07-2020", formatter);
        LocalDate endDate = LocalDate.parse("05-07-2020", formatter);

        LocalDate updatedStartDate = LocalDate.parse("06-11-2020", formatter);
        LocalDate updatedEndDate = LocalDate.parse("12-11-2021", formatter);

        Event event = new Event("toUpdateTitle1", 1, startDate, endDate, sponsorships, engagements);
        Event newEvent = new Event("updatedTitle1", 1, updatedStartDate, updatedEndDate, sponsorships, engagements);

        event.setId((long) 1);
        newEvent.setId((long) 1);

        eventRepository.create(event);
        eventRepository.updateEvent(newEvent);
        assertThat(eventRepository.getByTitle("updatedTitle1").getManagerId()).isEqualTo(newEvent.getManagerId());
    }

    @Test
    public void testDeleteEvent() {
        eventRepository.clearTable();

        LocalDate startDate = LocalDate.parse("04-07-2020", formatter);
        LocalDate endDate = LocalDate.parse("05-07-2020", formatter);

        Event event = new Event("deleteTitle1", 1, startDate, endDate, sponsorships, engagements);
        eventRepository.create(event);
        assertThat(eventRepository.count()).isEqualTo(1);
        eventRepository.deleteEvent(eventRepository.getByTitle(event.getTitle()).getId());
        assertThat(eventRepository.count()).isEqualTo(0);
    }

    @Test
    public void testGetAllEvents() {
        eventRepository.clearTable();

        LocalDate startDate1 = LocalDate.parse("04-07-2020", formatter);
        LocalDate endDate1 = LocalDate.parse("05-07-2020", formatter);

        LocalDate startDate2 = LocalDate.parse("20-01-2021", formatter);
        LocalDate endDate2 = LocalDate.parse("15-12-2021", formatter);

        LocalDate startDate3 = LocalDate.parse("06-07-2022", formatter);
        LocalDate endDate3 = LocalDate.parse("11-09-2022", formatter);

        Event event = new Event("title4", 1, startDate1, endDate1, sponsorships, engagements);
        Event event2 = new Event("title5", 2, startDate2, endDate2, sponsorships, engagements);
        Event event3 = new Event("title6", 3, startDate3, endDate3, sponsorships, engagements);
        LinkedList<Event> list = new LinkedList<>();
        list.add(event);
        list.add(event2);
        list.add(event3);
        eventRepository.create(event);
        eventRepository.create(event2);
        eventRepository.create(event3);
        assertThat(eventRepository.getAll().size()).isEqualTo(list.size());
    }

    @Test
    public void testGetEventByTitle() {
        eventRepository.clearTable();

        LocalDate startDate1 = LocalDate.parse("04-07-2020", formatter);
        LocalDate endDate1 = LocalDate.parse("05-07-2020", formatter);

        LocalDate startDate2 = LocalDate.parse("20-01-2021", formatter);
        LocalDate endDate2 = LocalDate.parse("15-12-2021", formatter);

        Event event = new Event("getTitle1", 1, startDate1, endDate1, sponsorships, engagements);
        Event event2 = new Event("getTitle2", 2, startDate2, endDate2, sponsorships, engagements);
        eventRepository.create(event);
        eventRepository.create(event2);
        assertThat(eventRepository.getByTitle("getTitle2").getManagerId()).isEqualTo(event2.getManagerId());
    }
}
