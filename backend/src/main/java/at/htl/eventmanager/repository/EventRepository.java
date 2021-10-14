package at.htl.eventmanager.repository;

import at.htl.eventmanager.entity.Event;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class EventRepository implements PanacheRepository<Event> {
    public EventRepository() {

    }

    @Transactional
    public List<Event> getAll() {
        return listAll();
    }

    @Transactional
    public void create(Event event) {
        try {
            getEntityManager().merge(event);
        }
        catch(NullPointerException ex) {
            System.out.println("Event is null!");
        }
    }

    @Transactional
    public long countEvent() {
        return ((Number)getEntityManager().createQuery("SELECT COUNT(e) FROM jpa_event e " ).getSingleResult()).intValue();
    }

    @Transactional
    public Event getById(Long id) {
        return findById(id);
    }

    @Transactional
    public Event getByTitle(String searchTitle) {
        return find("title", searchTitle).singleResult();
    }

    @Transactional
    public void updateEvent(Event event) {
        update("title = ?1, managerId = ?2, startDate = ?3, endDate = ?4 where id = ?5", event.getTitle(), event.getManagerId(), event.getStartDate(), event.getEndDate(), event.getId());
        //this.getEntityManager().merge(event);
    }

    @Transactional
    public void deleteEvent(Long id) {
        delete(findById(id));
    }

    @Transactional
    public void clearTable() {
        deleteAll();
        this.getEntityManager()
                .createNativeQuery("ALTER TABLE jpa_event ALTER COLUMN event_id RESTART WITH 1")
                .executeUpdate();
    }
}
