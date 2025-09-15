package unit.br.unitnetwork.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String msg) {
        super(msg);
    }
}
