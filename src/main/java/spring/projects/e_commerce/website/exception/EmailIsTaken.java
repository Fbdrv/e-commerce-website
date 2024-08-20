package spring.projects.e_commerce.website.exception;

public class EmailIsTaken extends RuntimeException {
    public EmailIsTaken() {
        super("User with this email already exists");
    }
}
