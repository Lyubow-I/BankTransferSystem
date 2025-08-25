package exception;

public class TransferException extends Exception {
    public TransferException(String message) {
        super(message);
    }
    public static final String INVALID_ACCOUNT_FORMAT = "Некорректный формат счета";
    public static final String ACCOUNT_NOT_FOUND = "Счет не найден в базе";
    public static final String INVALID_AMOUNT = "Некорректная сумма перевода";
    public static final String INVALID_CHARACTERS = "В номере счета обнаружены недопустимые символы";
}

