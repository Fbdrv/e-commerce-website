package spring.projects.e_commerce.website.exception;

public class WrongLoginDetails extends RuntimeException {
    public WrongLoginDetails() {
        super("Wrong username or password");
    }
}
