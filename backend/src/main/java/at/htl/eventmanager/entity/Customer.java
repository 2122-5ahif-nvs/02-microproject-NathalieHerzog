package at.htl.eventmanager.entity;

import javax.persistence.*;

@Entity(name = "jpa_customer")
@Schema(description = "A customer who can book events")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String userName;
    private String email;

    @OneToOne
    @JoinColumn(name = "SPONSORSHIP")
    private Sponsorship sponsorship;

    public Customer(String userName, String email, Sponsorship sponsorship) {
        this.userName = userName;
        this.email = email;
        this.sponsorship = sponsorship;
    }

    public Customer(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public Sponsorship getSponsorship() {
        return sponsorship;
    }

    public void setSponsorship(Sponsorship sponsorship) {
        this.sponsorship = sponsorship;
    }

    public Customer(Long id) {

    }

    public Customer() {
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "[{\"email\":\"" + email + "\",\"id\":" + id + ",\"userName\":\"" + userName + "\"}]";
    }
}
