package vk.controller.exception;

public class EmailIsExistException extends Exception {

    public static final String emailIsExist = "User c email - %s занят. попробуйте другой";

    public EmailIsExistException(String message) {
        super(message);
    }

}
