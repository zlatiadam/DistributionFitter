/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;

import GoodnessOfFitEstimators.GoodnessOfFit;
import java.util.HashMap;

/**
 *
 * @author Zlati
 */
public interface IDistribution {
    public HashMap<String,Double> estimateDistributionParameters(Double[] observations); //ML method by default
    public HashMap<String,Double> estimateDistributionParameters(Double[] observations, String method);
    public HashMap<String,Double> estimateDistributionParameters(Double[] observations, HashMap<String,Double> params, String method);
    
    //same as above, just a shorter name
    public HashMap<String,Double> fit(Double[] observations);
    public HashMap<String,Double> fit(Double[] observations, String method);
    public HashMap<String,Double> fit(Double[] observations, HashMap<String,Double> params, String method);
    
    public HashMap<String,Double> getParameters();
    public Double getParameter(String name);
    
    public Double ProbabilityDensityFunction(Double value);
    public Double P(Double value); //same as above
    
    public Double CumulativeDistributionFunction(Double value);
    public Double F(Double value); //same as above
    
    public Double getError(Double[] observations, String type, Integer histogramColumnNuber);
    public Double E(Double[] observations, String type, Integer histogramColumnNuber); //same as above

    
    public Double GoodnessOfFit(Double[] observations, String type);
    public Double GOF(Double[] observations, String type); //same as above
    
    public GoodnessOfFit getGOF(); //returns the last calculated GOF
    public Double getLastGOFTest();
    
    public String getDistributionType();
    
}
