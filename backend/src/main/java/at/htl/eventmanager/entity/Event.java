package at.htl.eventmanager.entity;

import net.bytebuddy.asm.Advice;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity(name = "jpa_event")
@Schema(description = "The event booked by the customer")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String title;
    private int managerId;
    @JsonbDateFormat(value = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonbDateFormat(value = "dd-MM-yyyy")
    private LocalDate endDate;

    @JsonbTransient
    @OneToMany
    @JoinColumn(name = "event_id")
    private List<Sponsorship> sponsorshipList;

    @JsonbTransient
    @OneToMany
    @JoinColumn(name = "event_id")
    private List<Engagement> engagementList;

    public Event(String title, int managerId, LocalDate startDate, LocalDate endDate, List<Sponsorship> sponsorships, List<Engagement> engagements) {
        this.title = title;
        this.managerId = managerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sponsorshipList = sponsorships;
        this.engagementList = engagements;
    }

    public Event() {
    }

    public List<Sponsorship> getSponsorshipList() {
        return sponsorshipList;
    }

    public void setSponsorshipList(List<Sponsorship> sponsorshipList) {
        this.sponsorshipList = sponsorshipList;
    }

    public List<Engagement> getEngagementList() {
        return engagementList;
    }

    public void setEngagementList(List<Engagement> engagementList) {
        this.engagementList = engagementList;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getTitle() {
        return title;
    }
    public int getManagerId() {
        return managerId;
    }
}
