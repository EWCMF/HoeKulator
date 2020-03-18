package beregnsfo;

import entities.Aendringstype;
import entities.Observable;
import entities.SFO;
import entities.exceptions.*;

import java.util.ArrayList;

public interface BeregnSFO extends Observable {
    void angivSFO(String navn, double beloeb, String foraeldersNavn) throws NegativBeloebException, NavnEksistererException, ForaelderEksistererIkkeException, ManglendeForaelderNavnException, ManglendeNavnException;
    void angivSFO(String navn, double beloeb, String foraeldersNavn, Aendringstype aendringstype, double aendringssats) throws NegativBeloebException, NavnEksistererException, ForaelderEksistererIkkeException, ManglendeForaelderNavnException, ManglendeNavnException;
    void angivSFO(String navn, String foraeldersNavn) throws NavnEksistererException, ForaelderEksistererIkkeException, ManglendeForaelderNavnException, ManglendeNavnException;
    SFO hentRodSFO();
    ArrayList<SFO> hentAlleSFO();
    void angivSum(double sum);
    double hentAlleBeloeb();
}
