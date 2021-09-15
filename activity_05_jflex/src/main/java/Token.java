public enum Token {
    EOF(0),
    ADDITION(1),
    SUBTRACTION(2),
    MULTIPLICATION(3),
    DIVISION(4),
    IDENTIFIER(5),
    LITERAL(6);

    private int value;

   Token(int value) {
        this.value = value;
    }

    public int getValue() {
       return value;
    }
}
