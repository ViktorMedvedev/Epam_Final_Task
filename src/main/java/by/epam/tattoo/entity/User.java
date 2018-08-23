package main.java.by.epam.tattoo.entity;

import java.util.Objects;

public class User extends Entity {
    private int id;
    private String email;
    private String login;
    private String password;
    private Role role;
    private byte discountPct;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public User(int id, String email, String login, String password, Role role, byte discountPct) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
        this.discountPct = discountPct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public byte getDiscountPct() {
        return discountPct;
    }

    public void setDiscountPct(byte discountPct) {
        this.discountPct = discountPct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                discountPct == user.discountPct &&
                Objects.equals(email, user.email) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                role == user.role;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, login, password, role, discountPct);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", discountPct=" + discountPct +
                '}';
    }


}
