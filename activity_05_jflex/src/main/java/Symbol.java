public class Symbol {

    protected String label;

    public Symbol(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "(label:" + label + ")";
    }
}
