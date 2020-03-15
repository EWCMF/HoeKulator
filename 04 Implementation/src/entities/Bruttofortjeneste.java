package entities;

import entities.exceptions.NegativBeloebException;

public interface Bruttofortjeneste {
    void angivOmsaetning(Omsaetning omsaetning);
    void angivVareforbrug(Vareforbrug vareforbrug);
    void angivBeloeb(double beloeb) throws NegativBeloebException;
    void beregnBeloeb();
    double hentBeloeb();
    void tilmeldObserver(Observer observer);
    void afmeldObserver(Observer observer);
    void anvendOmsaetningOgVareforbrug(Omsaetning omsaetning, Vareforbrug vareforbrug);
}
