package spring.projects.e_commerce.website.exception;

public class UsernameIsTaken extends RuntimeException {
    public UsernameIsTaken() {
        super("User with this username already exists");
    }
}
