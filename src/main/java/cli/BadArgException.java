package cli;

public class BadArgException extends RuntimeException {
    public BadArgException(String s){
        super(s);
    }

    @Override
    public String getMessage() {
        return "CLI Arg error: " + super.getMessage();
    }
}
