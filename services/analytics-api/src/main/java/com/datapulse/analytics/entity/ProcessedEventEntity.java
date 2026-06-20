package com.datapulse.analytics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProcessedEventEntity {

    @Id
    private String eventId;

    public ProcessedEventEntity() {
    }

    public ProcessedEventEntity(
            String eventId) {

        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }
}
