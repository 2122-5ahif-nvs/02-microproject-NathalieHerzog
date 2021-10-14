package at.htl.eventmanager.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "jpa_sponsorship")
@Schema(description = "The sponsorship for an event")
public class Sponsorship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sponsorship_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EVENT")
    private Event event;
    @OneToOne
    @JoinColumn(name = "CUSTOMER")
    private Customer customer;
    private BigDecimal budget;

    public Sponsorship(Event event, Customer customer, BigDecimal budget) {
        this.event = event;
        this.customer = customer;
        this.budget = budget;
    }

    public Sponsorship(Long id) {
        this.id = id;
    }

    public Sponsorship() {
    }

    public Long getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "[{\"event\":\"" + event + "\",\"customer\":" + customer + ",\"budget\":\"" + budget + "\"}]";
    }
}
