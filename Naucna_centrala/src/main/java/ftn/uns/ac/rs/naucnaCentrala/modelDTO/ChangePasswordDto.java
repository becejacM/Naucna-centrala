package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class ChangePasswordDto {
    private String currentPassword;
    private String newPassword;
    private String username;

    public ChangePasswordDto() {
    }

    public ChangePasswordDto(String currentPassword, String newPassword, String username) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.username = username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUserId(String username) {
        this.username = username;
    }
}
