package ftn.uns.ac.rs.naucnaCentrala.exceptions;

public class MyAlreadyExistsException extends RuntimeException {

    public MyAlreadyExistsException() {
        super();
    }

    public MyAlreadyExistsException(String message) {
        super(message);
    }
}
