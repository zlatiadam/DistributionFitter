/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;

/**
 *
 * @author Zlati
 */
public class Empirical {
    
    Double[] observations = null;
    
    public Empirical(Double[] observations){
        this.observations = observations;
    }
    
    public Double CumulativeDistributionFunction(Double x){
        int n = 0;
        
        for(int i=0; i<observations.length; i++){
            if(observations[i] <= x) n++;
        }

        Double p = (double)n / observations.length;
        
        return p;
    }
    
    public Double F(Double x){
        return CumulativeDistributionFunction(x);
    }
    
}
