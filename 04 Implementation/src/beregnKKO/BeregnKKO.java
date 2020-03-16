package beregnKKO;

import entities.Aendringstype;
import entities.KKO;
import entities.exceptions.*;

import java.util.ArrayList;

public interface BeregnKKO {
void angivKKO(String navn, double beloeb, String foraeldersNavn) throws NegativBeloebException, NavnEksistererException, ForaelderEksistererIkkeException, ManglendeForaelderNavnException, ManglendeNavnException;
void angivKKO(String navn, double beloeb, String foraeldersNavn, Aendringstype aendringstype, double aendringssats) throws NegativBeloebException, NavnEksistererException, ForaelderEksistererIkkeException, ManglendeForaelderNavnException, ManglendeNavnException;
void angivKKO(String navn, String foraeldersNavn) throws NavnEksistererException, ForaelderEksistererIkkeException, ManglendeForaelderNavnException, ManglendeNavnException;
KKO hentRodKKO();
ArrayList<KKO> hentAlleKKO();
void angivSum(double sum);
double hentAlleBeloeb();
}
