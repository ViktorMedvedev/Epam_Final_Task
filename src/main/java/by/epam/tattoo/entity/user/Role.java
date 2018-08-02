package main.java.by.epam.tattoo.entity.user;

import java.util.HashMap;

public enum Role {
    USER(1), ADMIN(2);
    private int roleNumber;

    public int getRoleNumber() {
        return roleNumber;
    }

    Role(int roleNumber) {
        this.roleNumber = roleNumber;
    }

    private static final HashMap<Integer, Role> ROLE_HASH_MAP = new HashMap<>();

    static {
        for (Role role : Role.values()) {
            ROLE_HASH_MAP.put(role.getRoleNumber(), role);
        }
    }

    public static Role getRoleByNumber(int roleNumber) {
        return ROLE_HASH_MAP.get(roleNumber);
    }
}


