package se233.chapter2.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import se233.chapter2.controller.AllEventHandlers;
import se233.chapter2.controller.draw.DrawGraphTask;
import se233.chapter2.controller.draw.DrawInfoPaneTask;
import se233.chapter2.controller.draw.DrawTopAreaTask;
import se233.chapter2.model.Currency;

import java.util.concurrent.*;

public class CurrencyPane extends BorderPane {
    private Currency currency;
    private Button watch;
    private Button unwatch;
    private Button delete;
    private Button ChangeBase;
    public CurrencyPane(Currency currency) {
        this.watch = new Button("Watch");
        this.unwatch = new Button("UnWatch");
        this.ChangeBase = new Button("Change Base");
        this.delete = new Button("Delete");
        this.watch. setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onWatch(currency.getShortCode());
            }
        });
        this.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onDelete(currency.getShortCode());
            }
        });
        this.unwatch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onUnwatch(currency.getShortCode());
            }
        });
        this.ChangeBase. setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onChange();
            }
        });
        this.setPadding(new Insets(0));
        this.setPrefSize(640, 300);
        this.setStyle("-fx-border-color: black");
        try {
            this.refreshPane(currency);
        } catch (ExecutionException e) {
            System. out.println("Encountered an execution exception.");
        } catch (InterruptedException e) {
            System.out.println("Encountered an interupted exception.");
        }
    }
    public void refreshPane(Currency currency) throws ExecutionException, InterruptedException {
        this.currency = currency;
        
        // Create tasks
        FutureTask<Pane> infoPaneTask = new FutureTask<>(new DrawInfoPaneTask(currency));
        FutureTask<VBox> graphTask = new FutureTask<>(new DrawGraphTask(currency));
        FutureTask<HBox> topAreaTask = new FutureTask<>(new DrawTopAreaTask(watch, unwatch, delete, ChangeBase));
        
        // Create thread pool with fixed number of threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        try {
            // Submit all tasks for execution
            executor.execute(infoPaneTask);
            executor.execute(graphTask);
            executor.execute(topAreaTask);
            
            // Wait for all tasks to complete and get results
            Pane currencyInfo = infoPaneTask.get();
            VBox currencyGraph = graphTask.get();
            HBox topArea = topAreaTask.get();
            
            // Update UI on JavaFX Application Thread
            this.setTop(topArea);
            this.setLeft(currencyInfo);
            this.setCenter(currencyGraph);
        } finally {
            // Shutdown the executor service
            executor.shutdown();
        }
    }
    // Removed genInfoPane() and genTopArea() as they are now handled by Callable tasks
}
