public class Vagonek {

    private VagonekType type;
    private int umisteni;
    private Vagonek predchozi;
    private Vagonek nasledujici;

    public Vagonek(VagonekType type) {
        this.type = type;
    }

    public VagonekType getType() {
        return type;
    }

    public void setType(VagonekType type) {
        this.type = type;
    }

    public int getUmisteni() {
        return umisteni;
    }

    public void setUmisteni(int umisteni) {
        this.umisteni = umisteni;
    }

    public Vagonek getPredchozi() {
        return predchozi;
    }

    public void setPredchozi(Vagonek predchozi) {
        this.predchozi = predchozi;
    }

    public Vagonek getNasledujici() {
        return nasledujici;
    }

    public void setNasledujici(Vagonek nasledujici) {
        this.nasledujici = nasledujici;
    }
}
