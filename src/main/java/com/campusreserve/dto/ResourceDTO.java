package com.campusreserve.dto;

public class ResourceDTO {
    private long resourceId;
    private String resourceType;
    private String name;
    private String location;
    private int capacity;
    private int pricePerSlot;
    private String status;

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getTypeLabel() {
        return "LOCKER".equals(resourceType) ? "사물함" : "스터디룸";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPricePerSlot() {
        return pricePerSlot;
    }

    public void setPricePerSlot(int pricePerSlot) {
        this.pricePerSlot = pricePerSlot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
