package se233.chapter2.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import se233.chapter2.Launcher;
import se233.chapter2.model.Currency;
import se233.chapter2.model.CurrencyEntity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AllEventHandlers {
    public static String EnteredCode = "THB";
    public static void onRefresh() {
        try {
            Launcher.refreshPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onAdd() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Currency");
            dialog.setContentText("Currency code:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            Optional<String> code = dialog.showAndWait().map(String::toUpperCase);

            if (code.isPresent()) {
                List<Currency> currencyList = Launcher.getCurrencyList();
                Currency c = new Currency(code.get());
                List<CurrencyEntity> cList = FetchData.fetchRange(c.getShortCode(), 30);
                c.setHistorical(cList);
                c.setCurrent(cList.get(cList.size() - 1));
                currencyList.add(c);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error"+ e);
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public static void onDelete(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                currencyList.remove(index);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void onWatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add Watch");
                dialog.setContentText("Rate:");
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                Optional<String> retrievedRate = dialog.showAndWait();
                if (retrievedRate.isPresent()) {
                    double rate = Double.parseDouble(retrievedRate.get());
                    currencyList.get(index).setWatch(true);
                    currencyList.get(index).setWatchRate(rate);
                    Launcher.setCurrencyList(currencyList);
                    Launcher.refreshPane();
                }
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void onUnwatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                    currencyList.get(index).setWatch(false);
                    Launcher.setCurrencyList(currencyList);
                    Launcher.refreshPane();
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public static void onChange() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Change Base Currency");
            dialog.setContentText("Currency code:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            Optional<String> code = dialog.showAndWait().map(String::toUpperCase);
            if (code.isPresent() && !code.get().trim().isEmpty()) {
                EnteredCode = code.get().trim();
                
                // Reload all currency data with the new base currency
                List<Currency> currencyList = Launcher.getCurrencyList();
                for (Currency currency : currencyList) {
                    List<CurrencyEntity> newRates = FetchData.fetchRange(currency.getShortCode(), 30);
                    if (!newRates.isEmpty()) {
                        currency.setHistorical(newRates);
                        currency.setCurrent(newRates.get(newRates.size() - 1));
                    }
                }
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error"+ e);
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}