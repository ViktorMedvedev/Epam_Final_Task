package main.java.by.epam.tattoo.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Order extends Entity {
    private int orderId;
    private int userId;
    private int tattooId;
    private BigDecimal totalPrice;
    private String status;

    public Order(int userId, int tattooId, BigDecimal totalPrice) {
        this.userId = userId;
        this.tattooId = tattooId;
        this.totalPrice = totalPrice;
    }

    public Order(int orderId, int userId, int tattooId, BigDecimal totalPrice, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.tattooId = tattooId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTattooId() {
        return tattooId;
    }

    public void setTattooId(int tattooId) {
        this.tattooId = tattooId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                userId == order.userId &&
                tattooId == order.tattooId &&
                Objects.equals(totalPrice, order.totalPrice) &&
                Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, userId, tattooId, totalPrice, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", tattooId=" + tattooId +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }
}
