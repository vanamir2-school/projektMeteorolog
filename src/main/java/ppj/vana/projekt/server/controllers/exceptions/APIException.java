package ppj.vana.projekt.server.controllers.exceptions;

public class APIException extends RuntimeException {

    public APIException(String message) {
        super(message);
    }
}
