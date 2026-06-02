package com.campusreserve.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SlotDTO {
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");

    private long slotId;
    private long resourceId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String status;

    public long getSlotId() {
        return slotId;
    }

    public void setSlotId(long slotId) {
        this.slotId = slotId;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartLabel() {
        return startAt == null ? "" : startAt.format(DATE_TIME);
    }

    public String getTimeLabel() {
        if (startAt == null || endAt == null) {
            return "";
        }
        return startAt.format(TIME) + " - " + endAt.format(TIME);
    }
}
