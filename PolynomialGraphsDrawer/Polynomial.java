package sample;

import java.util.ArrayList;

public class Polynomial {

    private ArrayList<Double> factors;

    Polynomial(ArrayList<Double> factors){
        this.factors = factors;
    }

    public double value(double x){
        double v = 0;
        int i = 0;

        for ( double factor : factors ){
            v += factor * Math.pow( x , i );
            ++i;
        }

        return v;
    }

}