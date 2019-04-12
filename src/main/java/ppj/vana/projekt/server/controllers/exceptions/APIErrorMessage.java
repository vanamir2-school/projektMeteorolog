package ppj.vana.projekt.server.controllers.exceptions;

public class APIErrorMessage {

    private final String message;

    public APIErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
