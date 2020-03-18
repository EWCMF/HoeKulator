package beregnmarkedsfoeringsbidrag;

import beregnsfo.BeregnSFO;
import beregnsfo.BeregnSFOImpl;
import entities.*;
import entities.exceptions.NegativBeloebException;

public class BeregnMarkedsfoeringsbidragImpl implements BeregnMarkedsfoeringsbidrag {
    private BeregnSFO sfo;
    private Bruttofortjeneste bruttofortjeneste;
    private Markedsfoeringsbidrag markedsfoeringsbidrag;

    public BeregnMarkedsfoeringsbidragImpl() {
        sfo = new BeregnSFOImpl();
        bruttofortjeneste = new BruttofortjenesteImpl();
        markedsfoeringsbidrag = new MarkedsfoeringsbidragImpl();
    }

    @Override
    public void angivSSO(BeregnSFO sfo) {
        this.sfo = sfo;
    }

    @Override
    public void angivBruttofortjeneste(Bruttofortjeneste bruttofortjeneste) {
        this.bruttofortjeneste = bruttofortjeneste;
    }

    @Override
    public void angivBruttofortjenesteOgSSO(double bruttofortjeneste, double sfo) throws NegativBeloebException {
        this.bruttofortjeneste.angivBeloeb(bruttofortjeneste);
        this.sfo.angivSum(sfo);
        beregnMarkedsfoeringsbidrag();
    }

    @Override
    public void beregnMarkedsfoeringsbidrag() {
        if (sfo != null && bruttofortjeneste != null) {
            markedsfoeringsbidrag.angivBeloeb(bruttofortjeneste.hentBeloeb() - sfo.hentAlleBeloeb());
        }
    }

    @Override
    public Markedsfoeringsbidrag hentMarkedsfoeringsbidrag() {
        return markedsfoeringsbidrag;
    }
}
