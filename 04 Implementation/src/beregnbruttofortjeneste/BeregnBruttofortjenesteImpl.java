package beregnbruttofortjeneste;

import entities.*;
import entities.exceptions.NegativBeloebException;

public class BeregnBruttofortjenesteImpl implements BeregnBruttofortjeneste {
    private Omsaetning omsaetning;
    private Vareforbrug vareforbrug;
    private Bruttofortjeneste bruttofortjeneste;

    public BeregnBruttofortjenesteImpl(){
        omsaetning = new OmsaetningImpl();
        vareforbrug = new VareforbrugImpl();
        bruttofortjeneste = new BruttofortjenesteImpl();
    }

    @Override
    public void angivOmsaetningOgVareforbrug(double omsaetning, double vareforbrug) throws NegativBeloebException {
        this.omsaetning.angivBeloeb(omsaetning);
        this.vareforbrug.angivBeloeb(vareforbrug);
        bruttofortjeneste.anvendOmsaetningOgVareforbrug(this.omsaetning, this.vareforbrug);
        bruttofortjeneste.beregnBeloeb();
    }

    @Override
    public void angivOmsaetning(Omsaetning omsaetning) {
        this.omsaetning = omsaetning;
        bruttofortjeneste.angivOmsaetning(this.omsaetning);
    }

    @Override
    public void angivVareforbrug(Vareforbrug vareforbrug) {
        this.vareforbrug = vareforbrug;
        bruttofortjeneste.angivVareforbrug(this.vareforbrug);
    }

    @Override
    public void beregnBruttofortjeneste() {
        bruttofortjeneste.beregnBeloeb();
    }

    @Override
    public Bruttofortjeneste hentBruttofortjeneste() {
        return bruttofortjeneste;
    }
}
