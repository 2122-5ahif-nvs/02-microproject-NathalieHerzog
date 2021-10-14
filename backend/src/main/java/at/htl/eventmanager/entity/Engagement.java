package at.htl.eventmanager.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "jpa_engagement")
@Schema(description = "The staff is engaged in a job at an event")
public class Engagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engagement_id")
    private Long id;

    @ManyToMany(mappedBy = "engagements", cascade = CascadeType.ALL)
    private Set<Staff> staffMemberList = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EVENT")
    private Event event;

    private String job;

    public Engagement(Event event, String job) {
        this.event = event;
        this.job = job;
    }

    public Engagement(Long id) {
        this.id = id;
    }

    public Engagement() {
    }

    public Set<Staff> getStaffMemberList() {
        return staffMemberList;
    }

    public void setStaffMember(Set<Staff> staffMemberList) {
        this.staffMemberList = staffMemberList;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "[{\"staff member\":\"" + staffMemberList + "\",\"event\":" + event + ",\"job\":\"" + job + "\"}]";
    }
}
