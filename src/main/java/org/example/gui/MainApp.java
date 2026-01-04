package org.example.gui;

public class MainApp {
    public static void main(String[] args) {
        try {
            // Launch JavaFX Application by reflection to avoid compile-time dependency issues in the IDE
            Class<?> appClass = Class.forName("javafx.application.Application");
            java.lang.reflect.Method launch = appClass.getMethod("launch", Class.class, String[].class);
            Class<?> runner = Class.forName("org.example.gui.GUIRunner");
            launch.invoke(null, runner, (Object) args);
        } catch (ClassNotFoundException e) {
            System.err.println("JavaFX not found on classpath. Please run with JavaFX.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
