package entities;

import entities.exceptions.NegativBeloebException;

public class BruttofortjenesteImpl implements Bruttofortjeneste, Observable {
    private double beloeb;
    private Omsaetning omsaetning;
    private  Vareforbrug vareforbrug;
    ObserverManager observerManager;

    public BruttofortjenesteImpl() {
        observerManager = newObserverManager();
    }


    @Override
    public void angivBeloeb(double beloeb) throws NegativBeloebException {
        if (beloeb < 0) {
            throw new NegativBeloebException("Beløbet må ikke være negativt");
        }
        this.beloeb = beloeb;
        observerManager.notificerObservere(this);
    }

    public void beregnBeloeb() {
        beloeb = omsaetning.hentBeloeb() - vareforbrug.hentBeloeb();
        observerManager.notificerObservere(this);
    }
  
    @Override
    public void anvendOmsaetningOgVareforbrug(Omsaetning omsaetning, Vareforbrug vareforbrug) {
        this.omsaetning = omsaetning;
        this.vareforbrug = vareforbrug;
        observerManager.notificerObservere(this);
    }

    @Override
    public double hentBeloeb() {
        return beloeb;
    }

    @Override
    public void tilmeldObserver(Observer observer) {
        observerManager.tilmeldObserver(observer);
    }

    @Override
    public void afmeldObserver(Observer observer) {
        observerManager.afmeldObserver(observer);
    }

    protected ObserverManager newObserverManager() {
        return new ObserverManagerImpl();
    }
}
