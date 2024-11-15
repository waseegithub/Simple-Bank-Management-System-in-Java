package BankingSystem;

public class InvalidAccountNumberException extends Exception {
    public InvalidAccountNumberException(String msg){
        super(msg);
    }
}
