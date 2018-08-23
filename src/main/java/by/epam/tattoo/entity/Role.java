package main.java.by.epam.tattoo.entity;

public enum Role {
    USER, ADMIN, MODERATOR;

    public static Role findRoleByName(String roleName) {
        return Role.valueOf(roleName.toUpperCase());
    }
}


