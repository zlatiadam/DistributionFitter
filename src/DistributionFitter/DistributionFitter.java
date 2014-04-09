/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributionFitter;

import DistributionEstimator.DistributionEstimator;
import Distributions.Distribution;
import Distributions.NormalDistribution;
import GoodnessOfFitEstimators.PearsonsChiSquareTest;
import Utility.Descriptives;
import java.io.BufferedWriter;
import java.io.File;
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
        
        String[] observations = new String[1000000];
        Double[] observations_d = new Double[1000000];
        
        //generator function
        Random r = new Random();
        double stdev = 1.0;
        double mean = 10.0;
        
        r.setSeed(1000);
        
        
        for(int i=0; i<observations.length; i++){
            observations_d[i] = (stdev*r.nextGaussian()+mean);
            
            observations[i] = Double.toString(observations_d[i]);
            //observations[i] = Integer.toString((int)Math.round(observations_d[i]));
        }
        
        /*
        NormalDistribution d2 = new NormalDistribution(null);
        d2.fit(observations_d);
        */
        //System.out.println(PearsonsChiSquareTest.calculateTestStatistic(observations_d, 1000, d2));

        
        Distribution d = DistributionEstimator.estimateDistribution(observations);
        
        d.GOF(observations_d, "Pearson");
        
        if(d != null)
            System.out.println("Distribution:"+d.getDistributionType()+", params:"+d.getParameters().toString()+", GOF:"+d.getLastGOFTest());

        
    }
    
    
}
