package beregnindtjeningsbidrag;

import beregnKKO.BeregnKKO;
import entities.Indtjeningsbidrag;
import entities.Markedsfoeringsbidrag;

public interface BeregnIndtjeningsbidrag {
    void beregnIndtjeningsbidrag();
    void angivKKO(BeregnKKO kko);
    void angivMarkedsfoeringsBidrag(Markedsfoeringsbidrag markedsfoeringsbidrag);
    void angivMarkedsfoeringsbidragOgKKO(double markedsfoeringsbidrag, double kko);
    Indtjeningsbidrag hentIndtjeningsbidrag();
}
