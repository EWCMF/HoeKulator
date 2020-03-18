package beregnsfo;

import entities.*;
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

public class BeregnSFOController {
    private GrundUIController grundUIController;
    private AendringstypeController aendringstypeController;
    private BeregnSFO beregnSFO;
    private String nuvaerendeAendringstype;
    private TreeItem<String> ssoTraeRod;

    @FXML
    private TextField navnTf, beloebTf, sfoTf;

    @FXML
    private TreeView<String> sfoTreeView;

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

    public void tilfoejTilSSOListe() throws NavnEksistererException, ForaelderEksistererIkkeException, ManglendeNavnException, ManglendeForaelderNavnException, NegativBeloebException {
        String foraelderNavn;
        if (!gruppeComboBox.getValue().equals("Ingen")) {
            foraelderNavn = gruppeComboBox.getValue();
        }
        else {
            foraelderNavn = "Salgsfremmende omkostninger";
        }
        switch (nuvaerendeAendringstype) {
            case "Procentændring":
                String navn = navnTf.getText();
                double beloeb = Double.parseDouble(beloebTf.getText());
                Aendringstype aendringstype = Aendringstype.PROCENTAENDRING;
                double aendringssats = Double.parseDouble(aendringstypeController.getProcentTf().getText());
                beregnSFO.angivSFO(navn, beloeb, foraelderNavn, aendringstype, aendringssats);
                break;
            case "Beløbmæssig ændring":
                navn = navnTf.getText();
                beloeb = Double.parseDouble(beloebTf.getText());
                aendringstype = Aendringstype.BELOEBMAESSIG_AENDRING;
                aendringssats = Double.parseDouble(aendringstypeController.getBeloebTF().getText());
                beregnSFO.angivSFO(navn, beloeb, foraelderNavn, aendringstype, aendringssats);
                break;
            case "Ingen ændring":
                navn = navnTf.getText();
                beloeb = Double.parseDouble(beloebTf.getText());
                beregnSFO.angivSFO(navn, beloeb, foraelderNavn);
                break;
        }
        updateTree();
    }

    public void tilfoejGruppe() throws NavnEksistererException, ForaelderEksistererIkkeException, ManglendeNavnException, ManglendeForaelderNavnException {
        if (gruppeComboBox.getValue().equals("Ingen")) {
            return;
        }
        beregnSFO.angivSFO(gruppeComboBox.getValue(), "Salgsfremmende omkostninger");
        updateTree();
    }

    public void opdaterResultatBudget() {
        grundUIController.opdaterSFO();
    }

    public void updateTree() {
        ArrayList<SFO> listeAfSFO = beregnSFO.hentAlleSFO();
        ssoTraeRod.getChildren().clear();
        for (SFO sfo : listeAfSFO) {
            TreeItem<String> kkoLeaf = lavSFOUI(sfo);
            boolean found = false;
            if (sfo.hentForaeldersNavn().equals(ssoTraeRod.getValue())) {
                ssoTraeRod.getChildren().add(kkoLeaf);
            }
            else {
                for (TreeItem<String> gruppeNode : ssoTraeRod.getChildren()) {
                    if (gruppeNode.getValue().contentEquals(sfo.hentForaeldersNavn())) {
                        gruppeNode.getChildren().add(kkoLeaf);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    TreeItem<String> gruppeNode = new TreeItem<>(sfo.hentForaeldersNavn());
                    ssoTraeRod.getChildren().add(gruppeNode);
                    gruppeNode.getChildren().add(kkoLeaf);
                }
            }
        }
    }

    private TreeItem<String> lavSFOUI(SFO sfo) {
        if (sfo.hentAendringstype() != null) {
            Label labelMedPadding = new Label(sfo.hentNavn());
            labelMedPadding.setPadding(new Insets(0, 16, 0, 0));
            HBox hBox = new HBox(labelMedPadding, new Label(String.format("%.2f", sfo.hentBeloeb())));
            TreeItem<String> treeItem = new TreeItem<>("");
            treeItem.setGraphic(hBox);
            return treeItem;
        }
        return new TreeItem<>(sfo.hentNavn());
    }


    public void setGrundUIController(GrundUIController grundUIController) {
        this.grundUIController = grundUIController;
    }

    public void setBeregnSFO(BeregnSFO beregnSFO) {
        this.beregnSFO = beregnSFO;
        ssoTraeRod = new TreeItem<> (this.beregnSFO.hentRodSFO().hentNavn());
        ssoTraeRod.setExpanded(true);
        sfoTreeView.setRoot(ssoTraeRod);

        beregnSFO.tilmeldObserver(new Observer() {
            @Override
            public void opdater(Observable observable) {
                sfoTf.setText(String.format("%.2f", beregnSFO.hentAlleBeloeb()));
                opdaterResultatBudget();
            }
        });
    }
}
