package main.java.by.epam.tattoo.entity;

import java.util.Arrays;
import java.util.Objects;

public class Offer extends Entity {
    private int offerId;
    private int userId;
    private byte[] imageBytes;

    public Offer(int userId, byte[] imageBytes) {
        this.userId = userId;
        this.imageBytes = imageBytes;
    }

    public Offer(int offerId, int userId, byte[] imageBytes) {
        this.offerId = offerId;
        this.userId = userId;
        this.imageBytes = imageBytes;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return offerId == offer.offerId &&
                userId == offer.userId &&
                Arrays.equals(imageBytes, offer.imageBytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(offerId, userId);
        result = 31 * result + Arrays.hashCode(imageBytes);
        return result;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offerId=" + offerId +
                ", userId=" + userId +
                ", imageBytes=" + Arrays.toString(imageBytes) +
                '}';
    }
}
