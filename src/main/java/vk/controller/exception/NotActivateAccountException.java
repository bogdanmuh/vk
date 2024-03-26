package vk.controller.exception;

public class NotActivateAccountException extends Exception {

    public static final String message = "User - %s is not activate. Activate you account.";

    public NotActivateAccountException(String message) {
        super(message);
    }

}
