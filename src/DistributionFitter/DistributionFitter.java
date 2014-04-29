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
import org.apache.commons.math3.distribution.*;


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
        int num_obs = 1000;
        String[] observations = new String[num_obs];
        Double[] observations_d = new Double[num_obs];
        
        /*
         * Normal Distribution
         */
        double stdev = 10.0;
        double mean = 10.0;
        org.apache.commons.math3.distribution.NormalDistribution nd = new org.apache.commons.math3.distribution.NormalDistribution(mean, stdev);
        nd.reseedRandomGenerator(1000);
        
        for(int i=0; i<observations_d.length; i++){
            observations_d[i] = nd.sample();
            observations[i] = Double.toString(observations_d[i]);
        }
        
        Distribution norm = DistributionEstimator.estimateDistribution(observations);
        
        if(norm != null)
            System.out.println("Distribution:"+norm.getDistributionType()+", params:"+norm.getParameters().toString()+", GOF:"+norm.getLastGOFTest());
        
        
        /*
         * Exponential Distribution
         */
        Double lambda = 10.0;
        org.apache.commons.math3.distribution.ExponentialDistribution ed = new ExponentialDistribution(lambda);
        ed.reseedRandomGenerator(1000);
        
        for(int i=0; i<observations_d.length; i++){
            observations_d[i] = ed.sample();
            
            //observations[i] = Double.toString(observations_d[i]);
            observations[i] = Double.toString(observations_d[i]);
        }
        
        Distribution exp = DistributionEstimator.estimateDistribution(observations);
        
        if(exp != null)
            System.out.println("Distribution:"+exp.getDistributionType()+", params:"+exp.getParameters().toString()+", GOF:"+exp.getLastGOFTest());
        
        
    }
    
    
}
