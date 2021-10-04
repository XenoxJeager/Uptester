package application;

public class NotAValidFile extends RuntimeException{
	public NotAValidFile(String errorMessage) {
        super(errorMessage);
    }
}
