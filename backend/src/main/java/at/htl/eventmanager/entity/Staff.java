package at.htl.eventmanager.entity;

import javax.inject.Inject;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "jpa_staff")
@Schema(description = "The staff working at the event")
public class Staff {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "staff_id")
     private Long id;

    private String name;
    private double salary;
    private boolean isOccupied;

    @JsonbTransient
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "jpa_staff_engagement",
            joinColumns = { @JoinColumn(name = "staff_id")},
            inverseJoinColumns = { @JoinColumn(name = "engagement_id")})
    private Set<Engagement> engagements = new HashSet<>();

    @Inject
    public Staff(String name, double salary, boolean isOccupied, Set<Engagement> engagements) {
        this.name = name;
        this.salary = salary;
        this.isOccupied = isOccupied;
        this.engagements = engagements;
    }

    public Staff() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Set<Engagement> getEngagements() {
        return engagements;
    }

    public void setEngagements(Set<Engagement> engagements) {
        this.engagements = engagements;
    }

    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
}
