package entities;

import entities.exceptions.ForaelderEksistererIkkeException;
import entities.exceptions.NavnEksistererException;

import java.util.ArrayList;

public interface SFO extends Observable {
    boolean navnEksisterer(String navn);
    boolean foraelderEksisterer(String foraeldersNavn);
    void tilfoejSFO(SFO nySFO) throws NavnEksistererException, ForaelderEksistererIkkeException;
    String hentNavn();
    String hentForaeldersNavn();
    SFO hentSFO(String navn);
    ArrayList<SFO> hentEfterfoelgere();
    double hentBeloeb();
    Aendringstype hentAendringstype();
    double hentAendringssats();
}
