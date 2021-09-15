public class Lexeme extends Symbol {

    private Token token;

    public Lexeme(final String label, final Token token) {
        super(label);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "(label:" + label + ", token:" + token + ")";
    }

    public static void main(String[] args) {
        Lexeme lexeme = new Lexeme("+", Token.ADDITION);
        System.out.println(lexeme);
    }
}
