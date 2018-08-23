package main.java.by.epam.tattoo.entity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class Tattoo extends Entity {
    private int id;
    private String style;
    private String size;
    private BigDecimal price;
    private byte[] imageBytes;
    private BigDecimal rating;

    public Tattoo(String style, String size, BigDecimal price, byte[] imageBytes) {
        this.style = style;
        this.size = size;
        this.price = price;
        this.imageBytes = imageBytes;
    }

    public Tattoo(int id, String style, String size, BigDecimal price, byte[] imageBytes, BigDecimal rating) {
        this.id = id;
        this.style = style;
        this.size = size;
        this.price = price;
        this.imageBytes = imageBytes;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tattoo tattoo = (Tattoo) o;
        return id == tattoo.id &&
                Objects.equals(style, tattoo.style) &&
                Objects.equals(size, tattoo.size) &&
                Objects.equals(price, tattoo.price) &&
                Arrays.equals(imageBytes, tattoo.imageBytes) &&
                Objects.equals(rating, tattoo.rating);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, style, size, price, rating);
        result = 31 * result + Arrays.hashCode(imageBytes);
        return result;
    }

    @Override
    public String toString() {
        return "Tattoo{" +
                "id=" + id +
                ", style='" + style + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", imageBytes=" + Arrays.toString(imageBytes) +
                ", rating=" + rating +
                '}';
    }
}
