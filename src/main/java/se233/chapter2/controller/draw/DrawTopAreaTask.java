package se233.chapter2.controller.draw;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.util.concurrent.Callable;

public class DrawTopAreaTask implements Callable<HBox> {
    private Button watch;
    private Button unwatch;
    private Button delete;
    private Button changeBase;

    public DrawTopAreaTask(Button watch, Button unwatch, Button delete, Button changeBase) {
        this.watch = watch;
        this.unwatch = unwatch;
        this.delete = delete;
        this.changeBase = changeBase;
    }

    @Override
    public HBox call() throws Exception {
        HBox topArea = new HBox(10);
        topArea.setPadding(new Insets(5));
        topArea.getChildren().addAll(watch, unwatch, delete, changeBase);
        topArea.setAlignment(Pos.CENTER_RIGHT);
        return topArea;
    }
}