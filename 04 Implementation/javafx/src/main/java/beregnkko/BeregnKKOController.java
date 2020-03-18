package beregnkko;

import beregnKKO.BeregnKKO;
import entities.Aendringstype;
import entities.KKO;
import entities.Observable;
import entities.Observer;
import entities.exceptions.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import start.GrundUIController;

import java.io.IOException;
import java.util.ArrayList;

public class BeregnKKOController {
    private GrundUIController grundUIController;
    private AendringstypeController aendringstypeController;
    private BeregnKKO beregnKKO;
    private String nuvaerendeAendringstype;
    private TreeItem<String> kkoTraeRod;

    @FXML
    private TextField navnTf, beloebTf, kkoTf;

    @FXML
    private TreeView<String> kkoTreeView;

    @FXML
    private Pane aendringPane;

    @FXML
    private ChoiceBox<String> aendringChoiceBox;

    @FXML
    private ComboBox<String> gruppeComboBox;

    public void initialize() {
        aendringChoiceBox.getItems().addAll("Procentændring", "Beløbmæssig ændring", "Ingen ændring");
        aendringChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
                String aendringstype = aendringChoiceBox.getItems().get((Integer) newValue);
                try {
                    skiftAendringstype(aendringstype);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        aendringChoiceBox.getSelectionModel().select(0);

        gruppeComboBox.getItems().add("Ingen");
        gruppeComboBox.getSelectionModel().select(0);
    }

    public void skiftAendringstype(String aendringstype) throws IOException {
        FXMLLoader loader = null;
        Node node;
        switch (aendringstype) {
            case "Procentændring":
                nuvaerendeAendringstype = "Procentændring";
                loader = new FXMLLoader(getClass().getResource("procentaendring.fxml"));
                break;
            case "Beløbmæssig ændring":
                nuvaerendeAendringstype = "Beløbmæssig ændring";
                loader = new FXMLLoader(getClass().getResource("beloebsaendring.fxml"));
                break;
            case "Ingen ændring":
                nuvaerendeAendringstype = "Ingen ændring";
                break;
        }
        if (!nuvaerendeAendringstype.equals("Ingen ændring")) {
            assert loader != null;
            node = loader.load();
            aendringstypeController = loader.getController();
            aendringPane.getChildren().setAll(node);
        }
        else {
            aendringstypeController = null;
            Pane pane = new Pane();
            aendringPane.getChildren().setAll(pane);
        }
    }

    public void tilfoejTilKKOListe() throws NavnEksistererException, ForaelderEksistererIkkeException, ManglendeNavnException, ManglendeForaelderNavnException, NegativBeloebException {
        String foraelderNavn;
        if (!gruppeComboBox.getValue().equals("Ingen")) {
            foraelderNavn = gruppeComboBox.getValue();
        }
        else {
            foraelderNavn = "Kontante kapacitetsomkostninger";
        }
        switch (nuvaerendeAendringstype) {
            case "Procentændring":
                String navn = navnTf.getText();
                double beloeb = Double.parseDouble(beloebTf.getText());
                Aendringstype aendringstype = Aendringstype.PROCENTAENDRING;
                double aendringssats = Double.parseDouble(aendringstypeController.getProcentTf().getText());
                beregnKKO.angivKKO(navn, beloeb, foraelderNavn, aendringstype, aendringssats);
                break;
            case "Beløbmæssig ændring":
                navn = navnTf.getText();
                beloeb = Double.parseDouble(beloebTf.getText());
                aendringstype = Aendringstype.BELOEBMAESSIG_AENDRING;
                aendringssats = Double.parseDouble(aendringstypeController.getBeloebTF().getText());
                beregnKKO.angivKKO(navn, beloeb, foraelderNavn, aendringstype, aendringssats);
                break;
            case "Ingen ændring":
                navn = navnTf.getText();
                beloeb = Double.parseDouble(beloebTf.getText());
                beregnKKO.angivKKO(navn, beloeb, foraelderNavn);
                break;
        }
        updateTree();
    }

    public void tilfoejGruppe() throws NavnEksistererException, ForaelderEksistererIkkeException, ManglendeNavnException, ManglendeForaelderNavnException {
        if (gruppeComboBox.getValue().equals("Ingen")) {
            return;
        }
        beregnKKO.angivKKO(gruppeComboBox.getValue(), "Kontante kapacitetsomkostninger");
        updateTree();
    }

    public void opdaterResultatBudget() {
        grundUIController.opdaterKKO();
    }

    public void updateTree() {
        ArrayList<KKO> listeAfKKO = beregnKKO.hentAlleKKO();
        kkoTraeRod.getChildren().clear();
        for (KKO kko : listeAfKKO) {
            TreeItem<String> kkoLeaf = lavKKOUI(kko);
            boolean found = false;
            if (kko.hentForaeldersNavn().equals(kkoTraeRod.getValue())) {
                kkoTraeRod.getChildren().add(kkoLeaf);
            }
            else {
                for (TreeItem<String> gruppeNode : kkoTraeRod.getChildren()) {
                    if (gruppeNode.getValue().contentEquals(kko.hentForaeldersNavn())) {
                        gruppeNode.getChildren().add(kkoLeaf);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    TreeItem<String> gruppeNode = new TreeItem<>(kko.hentForaeldersNavn());
                    kkoTraeRod.getChildren().add(gruppeNode);
                    gruppeNode.getChildren().add(kkoLeaf);
                }
            }
        }
    }

    private TreeItem<String> lavKKOUI(KKO kko) {
        if (kko.hentAendringstype() != null) {
            Label labelMedPadding = new Label(kko.hentNavn());
            labelMedPadding.setPadding(new Insets(0, 16, 0, 0));
            HBox hBox = new HBox(labelMedPadding, new Label(String.format("%.2f", kko.hentBeloeb())));
            TreeItem<String> treeItem = new TreeItem<>("");
            treeItem.setGraphic(hBox);
            return treeItem;
        }
        return new TreeItem<>(kko.hentNavn());
    }

    public void setGrundUIController(GrundUIController grundUIController) {
        this.grundUIController = grundUIController;
    }

    public void setBeregnKKO(BeregnKKO beregnKKO) {
        this.beregnKKO = beregnKKO;
        kkoTraeRod = new TreeItem<> (this.beregnKKO.hentRodKKO().hentNavn());
        kkoTraeRod.setExpanded(true);
        kkoTreeView.setRoot(kkoTraeRod);

        beregnKKO.tilmeldObserver(new Observer() {
            @Override
            public void opdater(Observable observable) {
                kkoTf.setText(String.format("%.2f", beregnKKO.hentAlleBeloeb()));
                opdaterResultatBudget();
            }
        });
    }
}
