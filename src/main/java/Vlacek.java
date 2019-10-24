import jdk.dynalink.linker.support.CompositeGuardingDynamicLinker;

import java.util.LinkedList;
import java.util.List;

public class Vlacek {

    private Vagonek lokomotiva = new Vagonek(VagonekType.LOKOMOTIVA);
    private Vagonek posledni = new Vagonek(VagonekType.POSTOVNI);
    private int delka = 2;

    public Vlacek(){
        lokomotiva.setNasledujici(posledni);
        lokomotiva.setUmisteni(1);
        posledni.setPredchozi(lokomotiva);
        posledni.setUmisteni(2);
    }

    /**
     * Přidávejte vagonky do vlaku
     * Podmínka je že vagonek první třídy musí být vždy řazen za předchozí vagonek toho typu, pokud žádný takový není je řazen rovnou za lokomotivu
     * vagonek 2 třídy musí být vždy řazen až za poslední vagonek třídy první
     * Poštovní vagonek musí být vždy poslední vagonek lokomotivy
     * Při vkládání vagonku nezapomeňte vagonku přiřadit danou pozici ve vlaku
     * !!!!!!! POZOR !!!!!! pokud přidáváte vagonek jinak než na konec vlaku musíte všem následujícím vagonkům zvýšit jejich umístění - doporučuji si pro tento účel vytvořit privátní metodu
     * @param type
     */
    public void pridatVagonek(VagonekType type) {

        Vagonek temp = new Vagonek(type);
        switch (type) {
            case PRVNI_TRIDA:

                lokomotiva.getNasledujici().setPredchozi(temp);
                temp.setNasledujici(lokomotiva.getNasledujici());
                temp.setPredchozi(lokomotiva);
                lokomotiva.setNasledujici(temp);

                nastavUmisteni();

                delka++;
                break;

            case DRUHA_TRIDA:

                posledni.getPredchozi().setNasledujici(temp);
                temp.setPredchozi(posledni.getPredchozi());
                temp.setNasledujici(posledni);
                posledni.setPredchozi(temp);

                nastavUmisteni();

                delka++;
                break;

            case JIDELNI:

                pridatJidelniVagonek();

                break;
        }
    }

    public void nastavUmisteni() {

        Vagonek temp = lokomotiva.getNasledujici();

        for (int i = 0; i < delka; i++) {

            temp.setUmisteni(temp.getPredchozi().getUmisteni() + 1);
            temp = temp.getNasledujici();

        }

    }

    public Vagonek getVagonekByIndex(int index) {
        int i = 1;
        Vagonek atIndex = lokomotiva;
        while(i < index) {
            atIndex = atIndex.getNasledujici();
            i++;
        }
        return atIndex;
    }


    /**
     * Touto metodou si můžete vrátit poslední vagonek daného typu
     * Pokud tedy budu chtít vrátit vagonek typu lokomotiva dostanu hned první vagonek
     * @param type
     * @return
     */
    public Vagonek getLastVagonekByType(VagonekType type) {

        Vagonek temp = posledni;

        switch (type) {

            case POSTOVNI:

                for (int i = 0; i < delka; i++) {

                    if (temp.getType() == VagonekType.POSTOVNI) {
                        return temp;
                    }


                }

            break;

            case LUZKOVY: //...... možná někdy
            break;
        }

        return temp;
    }

    /**
     * Tato funkce přidá jídelní vagonek za poslední vagonek první třídy, pokud jídelní vagonek za vagonkem první třídy již existuje
     * tak se další vagonek přidá nejblíže středu vagonků druhé třídy
     * tzn: pokud budu mít č osobních vagonků tak zařadím jídelní vagonek za 2 osobní vagónek
     * pokud budu mít osobních vagonků 5 zařadím jídelní vagonek za 3 osobní vagonek
     */
    public void pridatJidelniVagonek() {

        Vagonek jidelni = new Vagonek(VagonekType.JIDELNI);
        Vagonek temp = lokomotiva;

    for (int i = 0; i < delka; i++) {

        if (temp.getType() == VagonekType.PRVNI_TRIDA && temp.getNasledujici().getType() == VagonekType.DRUHA_TRIDA) {

            temp.getNasledujici().setPredchozi(jidelni);
            jidelni.setNasledujici(temp.getNasledujici());
            jidelni.setPredchozi(temp);
            temp.setNasledujici(jidelni);

            nastavUmisteni();

            delka++;

            break;

        }else if (temp.getType() == VagonekType.DRUHA_TRIDA && temp.getUmisteni() == posledni.getUmisteni() - getPolovinuDruhychTrid() - 1) {

            temp.getNasledujici().setPredchozi(jidelni);
            jidelni.setNasledujici(temp.getNasledujici());
            jidelni.setPredchozi(temp);
            temp.setNasledujici(jidelni);

            nastavUmisteni();

            delka++;

            break;
        }

        else {
            temp = temp.getNasledujici();
        }
    }
        }

    public int getPolovinuDruhychTrid() {

        Vagonek temp = lokomotiva;
        int pocetDruhychTrid = 0;

        for (int i = 0; i < delka; i++) {

            if (temp.getType() == VagonekType.DRUHA_TRIDA) {
                pocetDruhychTrid = pocetDruhychTrid + 1;
            }
            temp = temp.getNasledujici();

        }
        int polovinaDruhychTrid;
        if (pocetDruhychTrid % 2 == 0) {
            polovinaDruhychTrid = pocetDruhychTrid / 2;
        }else {
            polovinaDruhychTrid = pocetDruhychTrid / 2;
        }
        return polovinaDruhychTrid;
    }



    /**
     * Funkce vrátí počet vagonků daného typu
     * Dobré využití se najde v metodě @method(addJidelniVagonek)
     * @param type
     * @return
     */
    public int getDelkaByType(VagonekType type) {

        Vagonek temp = lokomotiva;
        int pocetVagonku = 0;

        switch (type) {

            case LOKOMOTIVA:

                for (int i = 0; i < delka; i++) {

                    if (temp.getType() == VagonekType.LOKOMOTIVA) {
                        pocetVagonku++;
                    }
                    temp = temp.getNasledujici();
                }

                break;

            case PRVNI_TRIDA:

                for (int i = 0; i < delka; i++) {

                    if (temp.getType() == VagonekType.PRVNI_TRIDA) {
                        pocetVagonku++;
                    }
                    temp = temp.getNasledujici();
                }
                break;

            case DRUHA_TRIDA:

                for (int i = 0; i < delka; i++) {

                    if (temp.getType() == VagonekType.DRUHA_TRIDA) {
                        pocetVagonku++;

                    }
                    temp = temp.getNasledujici();
                }

                break;
        }
        return pocetVagonku;
    }

    /**
     * Hledejte jidelni vagonky
     * @return
     */
    public List<Vagonek> getJidelniVozy() {
        List<Vagonek> jidelniVozy = new LinkedList<>();
        Vagonek temp = lokomotiva;

        for (int i = 0; i < delka; i++) {

            if (temp.getType() == VagonekType.JIDELNI) {
                jidelniVozy.add(temp);
            }

            temp = temp.getNasledujici();
        }

        return jidelniVozy;
    }

    /**
     * Odebere poslední vagonek daného typu
     * !!!! POZOR !!!!! pokud odebíráme z prostředku vlaku musíme zbývající vagonky projít a snížit jejich umístění ve vlaku
     * @param type
     */
    public void odebratPosledniVagonekByType(VagonekType type) {
        Vagonek temp = posledni;

        switch (type) {

            case JIDELNI:

                for (int i = 0; i < delka; i++) {

                    if (temp.getType() == VagonekType.JIDELNI) {

                        temp.getPredchozi().setNasledujici(temp.getNasledujici());
                        temp.getNasledujici().setPredchozi(temp.getPredchozi());

                        delka--;

                        posledni.setUmisteni(delka);

                        Vagonek temp1 = posledni;

                        for (int z = 1; z < delka; z++) {

                            temp1.getPredchozi().setUmisteni(temp1.getUmisteni() - 1);
                            temp1 = temp1.getPredchozi();
                        }

                        break;

                    }
                    temp = temp.getPredchozi();
                }

                break;
            case DRUHA_TRIDA:

                for (int i = 0; i < delka; i++) {

                    if (temp.getType() == VagonekType.DRUHA_TRIDA) {

                        temp.getPredchozi().setNasledujici(temp.getNasledujici());
                        temp.getNasledujici().setPredchozi(temp.getPredchozi());

                        delka--;

                        posledni.setUmisteni(delka);

                        Vagonek temp1 = posledni;

                        for (int z = 1; z < delka; z++) {

                            temp1.getPredchozi().setUmisteni(temp1.getUmisteni() - 1);
                            temp1 = temp1.getPredchozi();
                        }

                        break;

                    }
                    temp = temp.getPredchozi();
                }


                break;


        }




    }

    public int getDelka() {
        return delka;
    }
}
