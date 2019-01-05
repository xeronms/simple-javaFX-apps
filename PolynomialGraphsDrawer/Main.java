package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene1 = new Scene( Menu.getMenu(), 500, 500);

        primaryStage.setTitle("POLYNOMIALS");
        primaryStage.setScene( scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
