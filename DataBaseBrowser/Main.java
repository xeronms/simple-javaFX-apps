import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Library Data Base");

        Menu menu = new Menu();
        Scene scene = new Scene( menu.getMenu());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}