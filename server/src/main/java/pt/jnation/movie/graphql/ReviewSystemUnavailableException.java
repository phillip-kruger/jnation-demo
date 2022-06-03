package pt.jnation.movie.graphql;

public class ReviewSystemUnavailableException extends Exception {

    public ReviewSystemUnavailableException() {
    }

    public ReviewSystemUnavailableException(String message) {
        super(message);
    }

    public ReviewSystemUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewSystemUnavailableException(Throwable cause) {
        super(cause);
    }

    public ReviewSystemUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
