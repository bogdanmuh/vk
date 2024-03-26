package vk.controller.exception;

public class UserNofFoundException extends Exception {

    public static final String UserWithIdNotFound = "User c id - %s не найден.";
    public static final String UserWithUsernameNotFound = "User c username - %s не найден.";

    public UserNofFoundException(String message) {
        super(message);
    }
}
