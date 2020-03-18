package start;

import beregnKKO.BeregnKKO;
import beregnKKO.BeregnKKOImpl;
import beregnafskrivning.BeregnAfskrivningController;
import beregnafskrivning.BeregnAfskrivningImpl;
import beregnbruttofortjeneste.BeregnBruttofortjenesteImpl;
import beregnindtjeningsbidrag.BeregnIndtjeningsbidrag;
import beregnindtjeningsbidrag.BeregnIndtjeningsbidragController;
import beregnindtjeningsbidrag.BeregnIndtjeningsbidragImpl;
import beregnkko.BeregnKKOController;
import beregnmarkedsfoeringsbidrag.BeregnMarkedsfoeringsbidrag;
import beregnmarkedsfoeringsbidrag.BeregnMarkedsfoeringsbidragImpl;
import beregnmarkedsfoeringsbidrag.BeregnMarkedsfoeringsbidragController;
import beregnomsaetning.BeregnOmsaetningController;
import beregnomsaetning.BeregnOmsaetningImpl;
import beregnresultat.BeregnResultat;
import beregnresultat.BeregnResultatImpl;
import beregnresultat.BeregnSkatteprocentController;
import beregnresultatfoerrenter.BeregnResultatFoerRenterImpl;
import beregnresultatfoerskat.BeregnRenteindtaegterController;
import beregnresultatfoerskat.BeregnRenteomkostningerController;
import beregnresultatfoerskat.BeregnResultatFoerSkat;
import beregnresultatfoerskat.BeregnResultatFoerSkatImpl;
import beregnbruttofortjeneste.BeregnBruttofortjenesteController;
import beregnsfo.BeregnSFO;
import beregnsfo.BeregnSFOController;
import beregnsfo.BeregnSFOImpl;
import beregnvareforbrug.BeregnVareforbrugController;
import beregnvareforbrug.BeregnVareforbrugImpl;
import entities.Afskrivning;
import entities.Indtjeningsbidrag;
import entities.IndtjeningsbidragImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GrundUIController {
    private double afskrivningsPaneLayoutY = 38;
    private ArrayList<Node> afskrivninger;
    private BeregnOmsaetningImpl beregnOmsaetning;
    private BeregnVareforbrugImpl beregnVareforbrug;
    private BeregnBruttofortjenesteImpl beregnBruttofortjeneste;
    private BeregnSFO beregnSFO;
    private BeregnMarkedsfoeringsbidrag beregnMarkedsfoeringsbidrag;
    private BeregnKKO beregnKKO;
    private BeregnIndtjeningsbidrag beregnIndtjeningsbidrag;
    private BeregnAfskrivningImpl beregnAfskrivning;
    private BeregnResultatFoerRenterImpl beregnResultatFoerRenter;
    private BeregnResultatFoerSkat beregnResultatFoerSkat;
    private BeregnResultat beregnResultat;

    @FXML
    private Label omsaetningResultatLabel, vareforbrugResultatLabel, bruttofortjenesteResultatLabel,
            sfoResultatLabel, markedsfoeringsbidragResultatLabel, kkoResultatLabel,
            afskrivningResultatLabel, indtjeningsbidragResultatLabel, resultatFoerRenterResultatLabel,
            renteindtaegterResultatLabel, renteomkostningerResultatLabel, resultatFoerSkatResultatLabel,
            skatteprocentResultatLabel, resultatResultatLabel;

    @FXML
    private Pane omsaetningPane, vareforbrugPane, bruttofortjenestePane,
            afskrivningPane, indtjeningsbidragPane, renteindtaegterPane,
            renteomkostningerPane, skatteprocentPane, kkoPane, markedsfoeringsbidragPane,
            sfoPane;

    public GrundUIController() {
    }

    public void initialize() throws IOException {
        beregnOmsaetning = new BeregnOmsaetningImpl();
        beregnVareforbrug = new BeregnVareforbrugImpl();
        beregnBruttofortjeneste = new BeregnBruttofortjenesteImpl();
        beregnBruttofortjeneste.angivOmsaetning(beregnOmsaetning.hentOmsaetning());
        beregnBruttofortjeneste.angivVareforbrug(beregnVareforbrug.hentVareforbrug());
        beregnSFO = new BeregnSFOImpl();
        beregnMarkedsfoeringsbidrag = new BeregnMarkedsfoeringsbidragImpl();
        beregnMarkedsfoeringsbidrag.angivBruttofortjeneste(beregnBruttofortjeneste.hentBruttofortjeneste());
        beregnMarkedsfoeringsbidrag.angivSSO(beregnSFO);
        beregnKKO = new BeregnKKOImpl();
        beregnIndtjeningsbidrag = new BeregnIndtjeningsbidragImpl();
        beregnIndtjeningsbidrag.angivMarkedsfoeringsBidrag(beregnMarkedsfoeringsbidrag.hentMarkedsfoeringsbidrag());
        beregnIndtjeningsbidrag.angivKKO(beregnKKO);
        afskrivninger = new ArrayList<>();
        beregnAfskrivning = new BeregnAfskrivningImpl();
        beregnResultatFoerRenter = new BeregnResultatFoerRenterImpl();
        beregnResultatFoerSkat = new BeregnResultatFoerSkatImpl();
        beregnResultat = new BeregnResultatImpl();
        loadOmsaetning();
        loadVareforbrug();
        loadBruttofortjeneste();
        loadSalgsfremmendeOmkostninger();
        loadMarkedsfoeringsbidrag();
        loadKKO();
        loadIndtjeningsbidrag();
        loadAfskrivning();
        loadRenteintaegter();
        loadRenteomkostninger();
        loadSkatteprocent();
    }

    public void loadOmsaetning() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnomsaetning/Beregn_omsaetning.fxml"));
        Node node = fxmlLoader.load();
        BeregnOmsaetningController beregnOmsaetningController = fxmlLoader.getController();
        beregnOmsaetningController.setGrundUIController(this);
        beregnOmsaetningController.setBeregnOmsaetning(beregnOmsaetning);
        omsaetningPane.getChildren().add(node);
    }

    public void loadBruttofortjeneste() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnbruttofortjeneste/Beregn_bruttofortjeneste.fxml"));
        Node node = fxmlLoader.load();
        BeregnBruttofortjenesteController beregnBruttofortjenesteController = fxmlLoader.getController();
        beregnBruttofortjenesteController.setGrundUIController(this);
        beregnBruttofortjenesteController.setBeregnBruttofortjeneste(beregnBruttofortjeneste);
        bruttofortjenestePane.getChildren().add(node);
    }

    public void loadVareforbrug() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnvareforbrug/Beregn_vareforbrug.fxml"));
        Node node = fxmlLoader.load();
        BeregnVareforbrugController beregnVareforbrugController = fxmlLoader.getController();
        beregnVareforbrugController.setGrundUIController(this);
        beregnVareforbrugController.setBeregnVareforbrug(beregnVareforbrug);
        vareforbrugPane.getChildren().add(node);
    }

    public void loadSalgsfremmendeOmkostninger() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnsfo/salgsfremmende_omkostninger.fxml"));
        Node node = fxmlLoader.load();
        BeregnSFOController beregnSFOController = fxmlLoader.getController();
        beregnSFOController.setGrundUIController(this);
        beregnSFOController.setBeregnSFO(beregnSFO);
        sfoPane.getChildren().add(node);
    }

    public void loadMarkedsfoeringsbidrag() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnmarkedsfoeringsbidrag/Beregn_markedsfoeringsbidrag.fxml"));
        Node node = fxmlLoader.load();
        BeregnMarkedsfoeringsbidragController beregnMarkedsfoeringsbidragController = fxmlLoader.getController();
        beregnMarkedsfoeringsbidragController.setGrundUIController(this);
        beregnMarkedsfoeringsbidragController.setBeregnMarkedsfoeringsbidrag(beregnMarkedsfoeringsbidrag);
        markedsfoeringsbidragPane.getChildren().add(node);
    }

    public void loadKKO() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnkko/kontante_kapacitetsomkostninger.fxml"));
        Node node = fxmlLoader.load();
        BeregnKKOController beregnKKOController = fxmlLoader.getController();
        beregnKKOController.setGrundUIController(this);
        beregnKKOController.setBeregnKKO(beregnKKO);
        kkoPane.getChildren().add(node);
    }

    public void loadIndtjeningsbidrag() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnindtjeningsbidrag/Beregn_indtjeningsbidrag.fxml"));
        Node node = fxmlLoader.load();
        BeregnIndtjeningsbidragController beregnIndtjeningsbidragController = fxmlLoader.getController();
        beregnIndtjeningsbidragController.setGrundUIController(this);
        beregnIndtjeningsbidragController.setBeregnIndtjeningsbidrag(beregnIndtjeningsbidrag);
        indtjeningsbidragPane.getChildren().add(node);
    }

    public void loadAfskrivning() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnafskrivning/Beregn_afskrivning.fxml"));
        Node node = fxmlLoader.load();
        BeregnAfskrivningController beregnAfskrivningController;
        beregnAfskrivningController = fxmlLoader.getController();
        beregnAfskrivningController.setGrundUIController(this);
        beregnAfskrivningController.setBeregnAfskrivning(beregnAfskrivning);
        afskrivningPane.getChildren().add(node);
        afskrivninger.add(node);
        beregnAfskrivningController.setNode(node);
    }

    public void loadRenteintaegter() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnresultatfoerskat/Beregn_renteindtaegter.fxml"));
        Node node = fxmlLoader.load();
        BeregnRenteindtaegterController beregnRenteindtaegterController = fxmlLoader.getController();
        beregnRenteindtaegterController.setGrundUIController(this);
        beregnRenteindtaegterController.setBeregnResultatFoerSkat(beregnResultatFoerSkat);
        renteindtaegterPane.getChildren().add(node);
    }

    public void loadRenteomkostninger() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnresultatfoerskat/Beregn_renteomkostninger.fxml"));
        Node node = fxmlLoader.load();
        BeregnRenteomkostningerController beregnRenteomkostningerController = fxmlLoader.getController();
        beregnRenteomkostningerController.setGrundUIController(this);
        beregnRenteomkostningerController.setBeregnResultatFoerSkat(beregnResultatFoerSkat);
        renteomkostningerPane.getChildren().add(node);
    }

    public void loadSkatteprocent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnresultat/Beregn_skatteprocent.fxml"));
        Node node = fxmlLoader.load();
        BeregnSkatteprocentController beregnSkatteprocentController = fxmlLoader.getController();
        beregnSkatteprocentController.setGrundUIController(this);
        beregnSkatteprocentController.setBeregnResultat(beregnResultat);
        skatteprocentPane.getChildren().add(node);
    }

    @FXML
    public void tilfoejNyAfskrivning() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnafskrivning/Beregn_afskrivning.fxml"));
        Node node = fxmlLoader.load();
        afskrivninger.add(node);
        afskrivningsPaneLayoutY += 68;
        BeregnAfskrivningController beregnAfskrivningController;
        beregnAfskrivningController = fxmlLoader.getController();
        beregnAfskrivningController.setGrundUIController(this);
        beregnAfskrivningController.setBeregnAfskrivning(beregnAfskrivning);
        node.setLayoutY(afskrivningsPaneLayoutY);
        afskrivningPane.setPrefHeight(afskrivningPane.getPrefHeight() + 68);
        afskrivningPane.getChildren().add(node);
        beregnAfskrivningController.setNode(node);
        changeLayout();
    }

    @FXML
    public void fjernAfkrivning(Node node, String string) {
        if (afskrivninger.size() <= 0){
            return;
        }
        if (!string.isEmpty()) {
            if (beregnAfskrivning.hentAfskrivninger().containsKey(string)) {
                beregnAfskrivning.hentAfskrivninger().remove(string);
                opdaterAfskrivninger();
            }
        }
        afskrivningPane.getChildren().remove(node);
        afskrivninger.remove(node);
        changeLayout();
        afskrivningPane.setPrefHeight(afskrivningPane.getPrefHeight() - 68);
    }

    @FXML
    public void changeLayout() {
        for (int i = 0; i < afskrivninger.size(); i++) {
            afskrivninger.get(i).setLayoutY(i * 68 + 38);
        }
    }

    @FXML
    public void opdaterAfskrivninger() {
        double sum = 0;
        for (Map.Entry<String, Afskrivning> entry : beregnAfskrivning.hentAfskrivninger().entrySet()) {
            sum += entry.getValue().hentAfskrivningsvaerdi();
        }
        String formatted = String.format("%.2f", sum);
        afskrivningResultatLabel.setText(formatted);
        opdaterResultatFoerRenter();
    }
    public void opdaterOmsaetning() {
        double tal = beregnOmsaetning.hentOmsaetning().hentBeloeb();
        String formatted = String.format("%.2f", tal);
        omsaetningResultatLabel.setText(formatted);
        opdaterBruttofortjeneste();
    }

    public void opdaterVareforbrug() {
        double tal = beregnVareforbrug.hentVareforbrug().hentBeloeb();
        String formatted = String.format("%.2f", tal);
        vareforbrugResultatLabel.setText(formatted);
        opdaterBruttofortjeneste();
    }

    public void advarselOmsaetningOgVareforbrug() {
//        omsaetningResultatLabel.setText(omsaetningResultatLabel.getText() + "!");
//        vareforbrugResultatLabel.setText(vareforbrugResultatLabel.getText() + "!");
    }

    public void opdaterBruttofortjeneste() {
        beregnBruttofortjeneste.beregnBruttofortjeneste();
        double tal = beregnBruttofortjeneste.hentBruttofortjeneste().hentBeloeb();
        String formatted = String.format("%.2f", tal);
        bruttofortjenesteResultatLabel.setText(formatted);
        beregnMarkedsfoeringsbidrag.beregnMarkedsfoeringsbidrag();
        opdaterMarkedsfoeringsbidrag();
    }

    public void opdaterSFO() {
        double tal = beregnSFO.hentAlleBeloeb();
        String formatted = String.format("%.2f", tal);
        sfoResultatLabel.setText(formatted);
        beregnMarkedsfoeringsbidrag.beregnMarkedsfoeringsbidrag();
        opdaterMarkedsfoeringsbidrag();
    }

    public void advarselBruttofortjenesteOgSSO() {
//        ssoResultatLabel.setText(ssoResultatLabel.getText() + "!");
//        bruttofortjenesteResultatLabel.setText(bruttofortjenesteResultatLabel.getText() + "!");
    }

    public void opdaterMarkedsfoeringsbidrag() {
        double tal = beregnMarkedsfoeringsbidrag.hentMarkedsfoeringsbidrag().hentBeloeb();
        String formatted = String.format("%.2f", tal);
        markedsfoeringsbidragResultatLabel.setText(formatted);
        opdaterIndtjeningsbidrag();
    }

    public void opdaterKKO() {
        double tal = beregnKKO.hentAlleBeloeb();
        String formatted = String.format("%.2f", tal);
        kkoResultatLabel.setText(formatted);
        opdaterIndtjeningsbidrag();
    }

    public void opdaterIndtjeningsbidrag() {
        beregnIndtjeningsbidrag.beregnIndtjeningsbidrag();
        double tal = beregnIndtjeningsbidrag.hentIndtjeningsbidrag().hentBeloeb();
        String formatted = String.format("%.2f", tal);
        indtjeningsbidragResultatLabel.setText(formatted);
        opdaterResultatFoerRenter();
    }

    public void opdaterResultatFoerRenter() {
        Indtjeningsbidrag indtjeningsbidrag;
        if (beregnIndtjeningsbidrag.hentIndtjeningsbidrag() != null) {
            indtjeningsbidrag = beregnIndtjeningsbidrag.hentIndtjeningsbidrag();
        }
        else {
            indtjeningsbidrag = new IndtjeningsbidragImpl();
            indtjeningsbidrag.angivBeloeb(0);
        }
        beregnResultatFoerRenter.angivAfskrivningerOgIndtjeningsbidrag(beregnAfskrivning.hentAfskrivninger(), indtjeningsbidrag);
        double tal = beregnResultatFoerRenter.hentResultat().hentResultatFoerRenter();
        String formatted = String.format("%.2f", tal);
        resultatFoerRenterResultatLabel.setText(formatted);
        beregnResultatFoerSkat.angivResultatFoerRenter(beregnResultatFoerRenter.hentResultat());
        opdaterResultatFoerSkat();
    }

    public void opdaterRenteindtaegter() {
        double tal = beregnResultatFoerSkat.hentRenteindtaegter().hentRenteindtaegter();
        String formatted = String.format("%.2f", tal);
        renteindtaegterResultatLabel.setText(formatted);
    }

    public void opdaterRenteomkostninger() {
        double tal = beregnResultatFoerSkat.hentRenteomkostninger().hentRenteomkostninger();
        String formatted = String.format("%.2f", tal);
        renteomkostningerResultatLabel.setText(formatted);
    }


    public void opdaterResultatFoerSkat() {
        beregnResultatFoerSkat.beregnResultat();
        double tal = beregnResultatFoerSkat.HentResultat().hentResultatFoerSkat();
        String formatted = String.format("%.2f", tal);
        resultatFoerSkatResultatLabel.setText(formatted);
        beregnResultat.angivResultatFoerSkat(beregnResultatFoerSkat.HentResultat());
        opdaterResultat();
    }

    public void opdaterSkatteprocent() {
        double tal = beregnResultat.hentSkatteprocent().hentVaerdi();
        String formatted = String.format("%.1f", tal);
        skatteprocentResultatLabel.setText(formatted + "%");
        opdaterResultat();
    }

    public void opdaterResultat() {
        beregnResultat.beregnResultat();
        double tal = beregnResultat.hentResultat().hentBeloeb();
        String formatted = String.format("%.2f", tal);
        resultatResultatLabel.setText(formatted);
    }
}
