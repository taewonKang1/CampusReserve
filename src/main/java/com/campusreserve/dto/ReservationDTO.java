package com.campusreserve.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationDTO {
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private long reservationId;
    private long userId;
    private long resourceId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private String userName;
    private String loginId;
    private String resourceName;
    private String resourceType;
    private String paymentMethod;
    private String paymentStatus;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusLabel() {
        return switch (status) {
            case "RESERVED" -> "예약 완료";
            case "CANCEL_REQUESTED" -> "취소 요청";
            case "CANCELLED" -> "취소 완료";
            default -> status;
        };
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedLabel() {
        return createdAt == null ? "" : createdAt.format(DATE_TIME);
    }

    public String getPeriodLabel() {
        if (startAt == null || endAt == null) {
            return "";
        }
        return startAt.format(DATE_TIME) + " - " + endAt.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public boolean isCancelable() {
        return "RESERVED".equals(status);
    }

    public boolean isCancelRequested() {
        return "CANCEL_REQUESTED".equals(status);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
