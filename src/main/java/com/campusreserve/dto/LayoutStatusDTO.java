package com.campusreserve.dto;

public class LayoutStatusDTO {
    private long resourceId;
    private String resourceName;
    private String resourceType;
    private String location;
    private int availableCount;
    private int reservedCount;
    private int totalCount;

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public int getReservedCount() {
        return reservedCount;
    }

    public void setReservedCount(int reservedCount) {
        this.reservedCount = reservedCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getUsageRate() {
        if (totalCount == 0) {
            return 0;
        }
        return Math.round((reservedCount * 100.0f) / totalCount);
    }
}
