package com.campusreserve.dto;

public class DashboardDTO {
    private int totalReservations;
    private int pendingCancelCount;
    private int activeResourceCount;
    private int totalSales;

    public int getTotalReservations() {
        return totalReservations;
    }

    public void setTotalReservations(int totalReservations) {
        this.totalReservations = totalReservations;
    }

    public int getPendingCancelCount() {
        return pendingCancelCount;
    }

    public void setPendingCancelCount(int pendingCancelCount) {
        this.pendingCancelCount = pendingCancelCount;
    }

    public int getActiveResourceCount() {
        return activeResourceCount;
    }

    public void setActiveResourceCount(int activeResourceCount) {
        this.activeResourceCount = activeResourceCount;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }
}
