package com.example.producerService.secure;




public final class EnumConverter {
    public static Role convertStringToRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role");
        }
    }
    public static GroupType convertStringToGroup(String Role) {
        try {
            return GroupType.valueOf(Role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role");
        }
    }
}