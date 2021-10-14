package at.htl.eventmanager.repository;

import at.htl.eventmanager.entity.Customer;
import at.htl.eventmanager.entity.Sponsorship;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class CustomerRepositoryTest {
    @Inject
    CustomerRepository customerRepository;

    Sponsorship sponsorship;

    @Test
    public void testCreateCustomer()
    {
        customerRepository.clearTable();
        Customer customer = new Customer("user1", "mail1", sponsorship);
        customerRepository.create(customer);
        assertThat(customerRepository.countCustomer()).isEqualTo(1);
    }

    @Test
    public void testUpdateUsername()
    {
        customerRepository.clearTable();
        Customer customer = new Customer("updateUser1", "updateMail1", sponsorship);
        Customer newCustomer = new Customer("changedUser1", "changedMail1", sponsorship);
        customerRepository.create(customer);
        customer.setId((long) 1);
        newCustomer.setId((long) 1);
        customerRepository.updateUserName(newCustomer);
        assertThat(customerRepository.getByUsername("changedUser1").getUserName()).isEqualTo(newCustomer.getUserName());
    }

    @Test
    public void testUpdateMail()
    {
        customerRepository.clearTable();
        Customer customer = new Customer("updateUser2", "updateMail2", sponsorship);
        Customer newCustomer = new Customer("changedUser2", "changedMail2", sponsorship);
        customer.setId((long) 1);
        newCustomer.setId((long) 1);
        customerRepository.create(customer);
        customerRepository.updateEmail(newCustomer);
        assertThat(customerRepository.getByUsername("changedUser2").getEmail()).isEqualTo(newCustomer.getEmail());
    }

    @Test
    public void testDeleteCustomer()
    {
        customerRepository.clearTable();
        Customer customer = new Customer("deleteUser1", "deleteMail1", sponsorship);
        customerRepository.create(customer);
        assertThat(customerRepository.countCustomer()).isEqualTo(1);
        customerRepository.deleteCustomer(customerRepository.getByUsername(customer.getUserName()).getId());
        assertThat(customerRepository.countCustomer()).isEqualTo(0);
    }

    @Test
    public void testGetAllCustomers()
    {
        customerRepository.clearTable();
        Customer customer = new Customer("testUser1", "testMail1", sponsorship);
        Customer customer2 = new Customer("testUser2", "testMail2", sponsorship);
        Customer customer3 = new Customer("testUser3", "testMail3", sponsorship);
        LinkedList<Customer> list = new LinkedList<>();
        list.add(customer);
        list.add(customer2);
        list.add(customer3);
        customerRepository.create(customer);
        customerRepository.create(customer2);
        customerRepository.create(customer3);
        assertThat(customerRepository.getAllCustomers().size()).isEqualTo(list.size());
    }

    @Test
    public void testGetCustomerByUsername()
    {
        customerRepository.clearTable();
        Customer customer = new Customer("username1", "email1", sponsorship);
        Customer customer2 = new Customer("username2", "email2", sponsorship);
        customerRepository.create(customer);
        customerRepository.create(customer2);
        assertThat(customerRepository.getByUsername("username2").getUserName()).isEqualTo(customer2.getUserName());
    }
}
