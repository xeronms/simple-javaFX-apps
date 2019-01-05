package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Gallery {
    private static ScrollPane scroll;
    private static TilePane tile;

    public static ScrollPane createGallery(){

        // Screen
        scroll = new ScrollPane();
        tile = new TilePane();

        // Path
        TextField pathField = new TextField();
        pathField.setPromptText( "Enter gallery path" );
        pathField.setPrefSize(500, 20);

        Button button = new Button("Load");
        button.setOnAction(e -> {
            fileLoad( pathField );
        });

        tile.getChildren().addAll( pathField, button );

        scroll.setContent(tile);

        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setFitToWidth(true);

        return scroll;
    }

    private static void fileLoad( TextField pathField ){
        final String path;
        if ( pathField.getText()!=null && !pathField.getText().isEmpty()){
            path = pathField.getText();
            System.out.println(path);

            File file = new File(path);
            File [] photos = file.listFiles();

            try {
                for (File photo : photos) {

                    ImageView imageView = createImage(photo);
                    tile.getChildren().addAll(imageView);

                }
            }
            catch ( NullPointerException|FileNotFoundException e){
                System.out.println(e.getMessage());
                e.fillInStackTrace();
            }
        }
    }

    private static ImageView createImage( File file ) throws FileNotFoundException {

        final Image image = new Image(new FileInputStream( file ), 200, 0, true, true);
        ImageView imageView = new javafx.scene.image.ImageView(image);
        imageView.setFitWidth(200);
        imageView.setOnMouseClicked(mouseEvent -> {
            try {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    BorderPane borderPane = new BorderPane();
                    ImageView imageView1 = new ImageView();

                    Image image1 = new Image(new FileInputStream(file));
                    imageView1.setImage(image1);
                    imageView1.setFitHeight(800);
                    imageView1.setPreserveRatio(true);
                    imageView1.setSmooth(true);
                    imageView1.setCache(true);

                    borderPane.setCenter(imageView1);

                    Stage newStage = new Stage();
                    newStage.setWidth(600);
                    newStage.setHeight(800);
                    newStage.setTitle(file.getName());

                    Scene scene = new Scene(borderPane);
                    newStage.setScene(scene);
                    newStage.show();
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        });

        return imageView;
    }
}
