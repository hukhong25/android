package vn.haui.a260330_studentsm;

public class StudentModel {
    private String name;
    private String dob;
    private String phone;
    private String email;
    private int avatarResId; // ID của hình ảnh trong thư mục drawable

    public StudentModel(String name, String dob, String phone, String email, int avatarResId) {
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.avatarResId = avatarResId;
    }

    // --- Getters và Setters ---
    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getAvatarResId() {
        return avatarResId;
    }
}
