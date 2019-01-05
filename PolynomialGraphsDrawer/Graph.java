package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.*;

public class Graph {

    private Polynomial p;
    private double start,end,step;
    private int width = 800;
    private int height = 800;
    private HashMap<Double, Double> X = new HashMap<>();
    private double min,max;

    public Graph( Polynomial p, String start, String end, String step){

        try {
            this.p = p;
            this.start = Double.parseDouble(start);
            this.end = Double.parseDouble(end);
            this.step = Double.parseDouble(step);
        }catch( NumberFormatException e){
            System.out.println("WRONG INPUT");
        }
    }

    public void display(){
            Stage window = new Stage();
            window.setTitle("GRAPH");


            Group root = new Group();
            Canvas canvas = new Canvas(width, height);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            draw(gc);
            paintAxis(gc);

            root.getChildren().add(canvas);
            window.setScene(new Scene(root));
            window.show();
    }

    private void paintAxis(GraphicsContext g) {
        g.strokeLine(0,height-2,width,height-2);
        g.strokeLine(0,height-1,width,height-1);
        g.strokeLine(0,height,width,height);
        g.strokeLine(0,0,0,height);
        g.strokeLine(1,0,1,height);
    }

    // drawing graph
    private void draw(GraphicsContext g){
        init();

        double space = end - start;
        double paramX = (double)width/space;

        double spaceY = max - min;
        double paramY = (double)height/spaceY;
        System.out.printf("%f %f",min,max);


        for ( double x = start; x < end; x+=step){
            g.strokeLine(
                    (x - start) * paramX,
                    height - ( (X.get(x) - min) * paramY),
                    ((x - start) + step) * paramX,
                    height - ( (X.get(x + step) - min) * paramY)
            );
            System.out.println( paramY);
        }
    }

    // initialization of polynomial and its values
    private void init(){
        min = p.value(start);
        max = p.value(start);

        for ( double x = start; x < end+step; x+=step) {
            X.put(x, p.value(x));
            System.out.println(X.get(x));

            if ( X.get(x) > max)
                max = X.get(x);
            if (X.get(x) < min)
                min = X.get(x);
        }
    }
}