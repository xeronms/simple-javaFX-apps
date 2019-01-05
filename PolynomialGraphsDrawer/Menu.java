package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Menu {

    public static VBox getMenu(){
        ArrayList<Double> factors = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();

        // Title Labels
        final Label label1 = new Label("POLYNOMIALS");
        Label label2 = new Label("Give factor for: x^"+i);
        Label label3 = new Label();

        // Text Field for factors
        final TextField text1 = new TextField();
        text1.setPromptText( "Enter the factor" );
        text1.setMaxSize(200,50);

        final TextField text2 = new TextField();
        text2.setPromptText( "Enter the start point" );
        text2.setMaxSize(200,50);

        final TextField text3 = new TextField();
        text3.setPromptText( "Enter the end point" );
        text3.setMaxSize(200,50);

        final TextField text4 = new TextField();
        text4.setPromptText( "Enter the step" );
        text4.setMaxSize(200,50);

        // Button
        Button button1 = new Button();
        button1.setText("OK");
        button1.setOnAction(e -> {
            if ( text1.getText()!=null && !text1.getText().isEmpty()) {
                factors.add(new Double(text1.getText()));
                // polynomial equation
                label3.setText( label3.getText() +" + "+ factors.get(i.intValue())+"x^"+ i);

                i.incrementAndGet();
                label2.setText("Give factor for: x^"+i);
                text1.clear();
            }
        });

        // Button 2
        Button button2 = new Button();
        button2.setText("Apply");
        button2.setOnAction(e -> {
            Graph graph = new Graph( new Polynomial( factors ), text2.getText(), text3.getText(), text4.getText());
            graph.display();
        });

        // VBOX
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(
                label1, label2, text1, label3,
                text2, text3, text4,
                button1, button2
        );

        layout1.setAlignment(Pos.CENTER);

        return layout1;
    }
}
