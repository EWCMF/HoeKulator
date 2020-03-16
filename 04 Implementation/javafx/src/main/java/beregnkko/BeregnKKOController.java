package beregnkko;

import beregnKKO.BeregnKKO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import start.GrundUIController;

import java.io.IOException;

public class BeregnKKOController {
    private GrundUIController grundUIController;
    private AendringstypeController aendringstypeController;
    private BeregnKKO beregnKKO;
    private String nuvaerendeAendringstype;

    @FXML
    private Pane aendringPane;

    @FXML
    private ChoiceBox<String> aendringChoiceBox;

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

    public void setGrundUIController(GrundUIController grundUIController) {
        this.grundUIController = grundUIController;
    }

    public void setBeregnKKO(BeregnKKO beregnKKO) {
        this.beregnKKO = beregnKKO;
    }
}
