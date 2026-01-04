package org.example.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.Controller;
import org.example.model.stmt.IStmt;
import org.example.view.ExampleCreator;

public class GUIRunner extends Application {
    private GUIController ui;

    @Override
    public void start(Stage primaryStage) {
        IStmt[] examples = new IStmt[]{
                ExampleCreator.getExample1(),
                ExampleCreator.getExample2(),
                ExampleCreator.getExample3(),
                ExampleCreator.getExample4(),
                ExampleCreator.getExample5(),
                ExampleCreator.getExample6(),
                ExampleCreator.getExample7(),
                ExampleCreator.getExample8()
        };

        ui = new GUIController(examples);
        Scene scene = new Scene(ui.getRoot(), 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Toy Interpreter - JavaFX");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // attempt to shutdown controller executor(s)
        try {
            if (ui != null) {
                Controller c = ui.getController();
                if (c != null) c.shutdown();
            }
        } catch (Exception ignored) {
        }
        Platform.exit();
        super.stop();
    }
}
