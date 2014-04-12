/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributionFitter;

import DistributionEstimator.DistributionEstimator;
import Distributions.Distribution;
import Distributions.EmpiricalDistribution;
import Distributions.NormalDistribution;
import GoodnessOfFitEstimators.PearsonsChiSquareTest;
import Utility.Descriptives;
import Variables.ContinuousNumericalVariable;
import Variables.DiscreteNumericalVariable;
import Variables.NominalVariable;
import java.util.HashMap;

import java.util.Random;

/**
 *
 * @author Zlati
 */
public class DistributionFitter {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int num_obs = 100;
        
        String[] observations = new String[num_obs];
        Double[] observations_d = new Double[num_obs];
        
        //generator function
        Random r = new Random();
        double stdev = 10.0;
        double mean = 10.0;
        
        r.setSeed(1000);
        
        
        for(int i=0; i<observations_d.length; i++){
            observations_d[i] = (stdev*r.nextGaussian()+mean);
            
            //observations[i] = Double.toString(observations_d[i]);
            observations[i] = Integer.toString((int)Math.round(observations_d[i]));
        }
        
        Distribution d = DistributionEstimator.estimateDistribution(observations);

        //d.GOF(observations_d, "Pearson");
        
        if(d != null)
            System.out.println("Distribution:"+d.getDistributionType()+", params:"+d.getParameters().toString()+", GOF:"+d.getLastGOFTest());

        
    }
    
    
}
