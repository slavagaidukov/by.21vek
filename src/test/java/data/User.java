package data;

import lombok.Data;

@Data
public class User {

    private final static String EMAIL = "qautomation60@gmail.com";
    private final static String PASSWORD = "0ec873a1";

    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User getDefaultUser() {
        return new User(EMAIL, PASSWORD);
    }

}
