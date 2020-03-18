package beregnsfo;

import entities.*;
import entities.exceptions.*;

import java.util.ArrayList;

public class BeregnSFOImpl implements BeregnSFO {
    ObserverManager observerManager;
    SFO sfo;
    ArrayList<SFO> listeAfSFO = new ArrayList<>();
    double sum = 0;

    public BeregnSFOImpl() {
        observerManager = newObserverManager();
        sfo = new SFOImpl();
    }


    public void angivSFO(String navn, double beloeb, String foraeldersNavn) throws NegativBeloebException, NavnEksistererException, ForaelderEksistererIkkeException, ManglendeForaelderNavnException, ManglendeNavnException {
        if (beloeb < 0) {
            throw new NegativBeloebException("Beløbet må ikke være negativt");
        }

        if (sfo.navnEksisterer(navn)) {
            throw new NavnEksistererException();
        }

        if (!sfo.foraelderEksisterer(foraeldersNavn)) {
            throw new ForaelderEksistererIkkeException();
        }

        SFO nySFO = new SFOImpl(navn, beloeb, foraeldersNavn);
        sfo.tilfoejSFO(nySFO);
        listeAfSFO.add(nySFO);
        observerManager.notificerObservere(this);

    }

    public void angivSFO(String navn, double beloeb, String foraeldersNavn, Aendringstype aendringstype, double aendringssats) throws NegativBeloebException, NavnEksistererException, ForaelderEksistererIkkeException, ManglendeForaelderNavnException, ManglendeNavnException {
        if (beloeb < 0) {
            throw new NegativBeloebException("Beløbet må ikke være negativt");
        }

        if (sfo.navnEksisterer(navn)) {
            throw new NavnEksistererException();
        }

        if (!sfo.foraelderEksisterer(foraeldersNavn)) {
            throw new ForaelderEksistererIkkeException();
        }

        SFO nySFO = new SFOImpl(navn, beloeb, foraeldersNavn, aendringstype, aendringssats);
        sfo.tilfoejSFO(nySFO);
        listeAfSFO.add(nySFO);
        observerManager.notificerObservere(this);

    }

    public void angivSFO(String navn, String foraeldersNavn) throws NavnEksistererException, ForaelderEksistererIkkeException, ManglendeForaelderNavnException, ManglendeNavnException {
        if (sfo.navnEksisterer(navn)) {
            throw new NavnEksistererException();
        }

        if (!sfo.foraelderEksisterer(foraeldersNavn)) {
            throw new ForaelderEksistererIkkeException();
        }

        SFO nySFO = new SFOImpl(navn, foraeldersNavn);
        sfo.tilfoejSFO(nySFO);
        listeAfSFO.add(nySFO);
        observerManager.notificerObservere(this);
    }

    @Override
    public SFO hentRodSFO() {
        return sfo;
    }

    @Override
    public void angivSum(double sum) {
        this.sum = sum;
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

    @Override
    public ArrayList<SFO> hentAlleSFO() {
        return listeAfSFO;
    }

    @Override
    public double hentAlleBeloeb() {
        sum = 0;
        hentAlleBeloeb(sfo);
        return sum;
    }

    private void hentAlleBeloeb(SFO SFO) {
        for (SFO underSFO : SFO.hentEfterfoelgere()) {
            sum += underSFO.hentBeloeb();
            if (underSFO.hentEfterfoelgere().size() > 0) {
                for (SFO underUnderSFO : underSFO.hentEfterfoelgere()) {
                    sum += underUnderSFO.hentBeloeb();
                    hentAlleBeloeb(underUnderSFO);
                }
            }
        }
    }
}
