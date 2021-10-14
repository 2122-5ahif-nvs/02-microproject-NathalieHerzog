package at.htl.eventmanager.repository;

import at.htl.eventmanager.entity.Engagement;
import at.htl.eventmanager.entity.Staff;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StaffRepository implements PanacheRepository<Staff> {
    public StaffRepository() {

    }

    @Transactional
    public List<Staff> getAll() {
        return listAll();
    }

    @Transactional
    public void create(Staff staff) {
        try {
            this.getEntityManager().merge(staff);
        }
        catch(NullPointerException ex) {
            System.out.println("Staff is null!");
        }
    }

    @Transactional
    public Engagement createEngagement(Engagement engagement) {
        try {
            getEntityManager().merge(engagement);
        }
        catch(NullPointerException ex) {
            System.out.println("Engagement is null!");
        }

        return engagement;
    }

    @Transactional
    public long countStaff() {
        return ((Number)getEntityManager().createQuery("SELECT COUNT(s) FROM jpa_staff s " ).getSingleResult()).intValue();
    }

    @Transactional
    public Staff getByName(String searchName) {
        return find("name", searchName).singleResult();
    }

    @Transactional
    public Staff getById(Long id) {
        return findById(id);
    }

    @Transactional
    public void updateStaff(Staff staff) {
        update("name = ?1, isoccupied = ?2, salary = ?3 where id = ?4", staff.getName(), staff.getOccupied(), staff.getSalary(), staff.getId());
        //this.getEntityManager().merge(staff);
    }

    @Transactional
    public void deleteStaff(Long id) {
        delete(findById(id));
    }

    @Transactional
    public void clearTable() {
        deleteAll();
        this.getEntityManager()
                .createNativeQuery("ALTER TABLE jpa_staff ALTER COLUMN staff_id RESTART WITH 1")
                .executeUpdate();
    }
}
