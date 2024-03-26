package vk.controller.exception;

public class ChatNotFoundException extends Exception {

    public static final String NotFound = "Chat c id - %s не найден.";

    public ChatNotFoundException(String message) {
        super(message);
    }

}
