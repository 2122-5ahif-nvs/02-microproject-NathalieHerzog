package enitity;

import at.htl.eventmanager.entity.Customer;
import at.htl.eventmanager.entity.Sponsorship;
import at.htl.eventmanager.repository.CustomerRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class CustomerTest {
    @Inject
    CustomerRepository customerRepository;

    Sponsorship sponsorship;

    @Test
    public void testCustomerToString()
    {
        customerRepository.clearTable();
        Customer customer = new Customer("user1", "mail1@gmail.com", sponsorship);
        customerRepository.create(customer);
        assertThat(customerRepository.getById((long) 1).toString()).isEqualTo("[{\"email\":\"mail1@gmail.com\",\"id\":1,\"userName\":\"user1\"}]");
    }
}
