package beregnmarkedsfoeringsbidrag;

import beregnsfo.BeregnSFO;
import entities.Bruttofortjeneste;
import entities.Markedsfoeringsbidrag;
import entities.exceptions.NegativBeloebException;

public interface BeregnMarkedsfoeringsbidrag {
    void angivSSO(BeregnSFO sso);
    void angivBruttofortjeneste(Bruttofortjeneste bruttofortjeneste);
    void angivBruttofortjenesteOgSSO(double bruttofortjeneste, double sso) throws NegativBeloebException;
    void beregnMarkedsfoeringsbidrag();
    Markedsfoeringsbidrag hentMarkedsfoeringsbidrag();
}
