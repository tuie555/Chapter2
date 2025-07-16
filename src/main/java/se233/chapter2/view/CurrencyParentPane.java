package se233.chapter2.view;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import se233.chapter2.model.Currency;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CurrencyParentPane extends FlowPane {
    public CurrencyParentPane(List<Currency> currencyList) throws ExecutionException, InterruptedException {
        this.setPadding(new Insets(0));
        // ป้องกัน null
        if (currencyList == null) {
            currencyList = List.of(); // หรือ new ArrayList<>()
        }
        refreshPane(currencyList);
    }
    public void refreshPane(List<Currency> currencyList) throws ExecutionException, InterruptedException {
        // ป้องกัน null
        if (currencyList == null) {
            currencyList = List.of(); // หรือ new ArrayList<>()
        }
        this.getChildren().clear();
        for (int i = 0; i < currencyList.size(); i++) {
            CurrencyPane cp = new CurrencyPane(currencyList.get(i));
            this.getChildren().add(cp);
        }
    }
}