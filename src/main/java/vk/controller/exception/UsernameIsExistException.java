package vk.controller.exception;

public class UsernameIsExistException extends Exception {

    public static final String usernameIsExist = "User c username - %s занят. попробуйте другой";

    public UsernameIsExistException(String message) {
        super(message);
    }

}
