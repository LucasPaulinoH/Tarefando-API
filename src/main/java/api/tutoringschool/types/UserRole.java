package api.tutoringschool.types;

public enum UserRole {
    TUTOR("TUTOR"),
    GUARDIAN("GUARDIAN");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
