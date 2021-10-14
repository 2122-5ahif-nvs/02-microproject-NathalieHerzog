package at.htl.eventmanager.repository;

import at.htl.eventmanager.entity.Customer;
import at.htl.eventmanager.entity.Sponsorship;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {
    public CustomerRepository() {
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        return listAll();
    }

    @Transactional
    public void create(Customer customer) {
        try {
            this.getEntityManager().merge(customer);
        }
        catch(NullPointerException ex) {
            System.out.println("Customer is null!");
        }
    }

    @Transactional
    public Sponsorship createSponsorship(Sponsorship sponsorship) {
        try {
            this.getEntityManager().merge(sponsorship);
        }
        catch(NullPointerException ex) {
            System.out.println("Sponsorship is null!");
        }

        return sponsorship;
    }

    @Transactional
    public long countCustomer() {
        return ((Number)getEntityManager().createQuery("SELECT COUNT(c) FROM jpa_customer c " ).getSingleResult()).intValue();
    }

    @Transactional
    public String customerStaffName(long id) {
        return ((String)getEntityManager().createQuery("SELECT c.userName, s.name FROM jpa_customer c, jpa_staff s WHERE c.id = ?1 AND s.id = ?1" )
                .setParameter(1, id)
                .getSingleResult()).toString();
    }

    @Transactional
    public Customer getByUsername(String searchUsername) {
        return find("username", searchUsername).singleResult();
    }

    @Transactional
    public Customer getById(Long id) {
        return findById(id);
    }

    @Transactional
    public void updateUserName(Customer customer) {
       update("username = ?1, email = ?2 where id = ?3", customer.getUserName(), customer.getEmail(), customer.getId());
       //this.getEntityManager().merge(customer);
    }

    @Transactional
    public void updateEmail(Customer customer) {
        update("username = ?1, email = ?2 where id = ?3", customer.getUserName(), customer.getEmail(), customer.getId());
        //this.getEntityManager().merge(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        delete(findById(id));
    }

    @Transactional
    public void clearTable() {
       //this.getEntityManager().createQuery("DELETE FROM jpa_customer WHERE id > 0");
        deleteAll();
        this.getEntityManager()
                .createNativeQuery("ALTER TABLE jpa_customer ALTER COLUMN customer_id RESTART WITH 1")
                .executeUpdate();
    }
}
