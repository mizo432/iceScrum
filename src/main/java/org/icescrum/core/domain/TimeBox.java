package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@MappedSuperclass
public abstract class TimeBox implements Serializable, Comparable<TimeBox> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @Lob
    private String goal;

    @Lob
    private String description;

    private int orderNumber = 0;

    private LocalDate todoDate;
    private Instant dateCreated;
    private Instant lastUpdated;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orderNumber ASC")
    private Set<MetaData> metaDatas = new HashSet<>();

    public TimeBox() {
    }

    @PrePersist
    public void onPrePersist() {
        if (dateCreated == null) {
            dateCreated = Instant.now();
        }
        if (lastUpdated == null) {
            lastUpdated = Instant.now();
        }
        if (todoDate == null) {
            todoDate = LocalDate.now();
        }
    }

    @PreUpdate
    public void onPreUpdate() {
        lastUpdated = Instant.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(LocalDate todoDate) {
        this.todoDate = todoDate;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<MetaData> getMetaDatas() {
        return metaDatas;
    }

    public void setMetaDatas(Set<MetaData> metaDatas) {
        this.metaDatas = metaDatas;
    }

    public Integer getDuration() {
        if (startDate != null && endDate != null) {
            return (int) (java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1);
        }
        return 0;
    }

    @Override
    public int compareTo(TimeBox o) {
        if (o == null) return 1;
        int cmp = Integer.compare(this.orderNumber, o.orderNumber);
        if (cmp != 0) return cmp;
        return this.id.compareTo(o.id);
    }

    public static List<String> validateStartDate(LocalDate newStartDate, LocalDate endDate) {
        List<String> errors = new ArrayList<>();
        if (newStartDate != null && endDate != null) {
            if (newStartDate.equals(endDate)) {
                errors.add("equals.endDate");
            } else if (newStartDate.isAfter(endDate)) {
                errors.add("after.endDate");
            }
        }
        return errors;
    }

    public static List<String> validateEndDate(LocalDate startDate, LocalDate newEndDate) {
        List<String> errors = new ArrayList<>();
        if (startDate != null && newEndDate != null) {
            if (newEndDate.equals(startDate)) {
                errors.add("equals.startDate");
            } else if (newEndDate.isBefore(startDate)) {
                errors.add("before.startDate");
            }
        }
        return errors;
    }
}
