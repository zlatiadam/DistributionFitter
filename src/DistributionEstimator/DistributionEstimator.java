/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributionEstimator;

import Distributions.Distribution;
import Distributions.DistributionTable;
import Distributions.NormalDistribution;
import Variables.ContinuousNumericalVariable;
import Variables.DiscreteNumericalVariable;
import Variables.NominalVariable;
import Variables.Variable;

/**
 *
 * @author Zlati
 */
public class DistributionEstimator {
    
    public static Distribution estimateDistribution(String[] observations){
        
        int level = detectMeasurementLevel(observations);
        
        Variable var = null;
        
        if(level == 0){
            //nominal variables should only be used by distribution tables
            return null;
        }else if(level == 1){
            var = new DiscreteNumericalVariable(observations);
        }else if(level == 2){
            var = new ContinuousNumericalVariable(observations);
        }
        
        Double[] d_observations = var.getObservations();
        
        //define known distributions
        Distribution[] knownDistributions = new Distribution[]{
            new NormalDistribution(null)
        };
        
        //fit the data with ML
        Double gof = null;
        
        Double min_dif = null;
        Integer min_dif_index = null;
        
        for(int i=0; i<knownDistributions.length; i++){

            //fit distribution to the data
            knownDistributions[i].fit(d_observations);
            
            //get goodness of fit
            gof = knownDistributions[i].GoodnessOfFit(d_observations, "Pearson");
            
            //initialize variables
            if(min_dif == null){
                min_dif = gof;
                min_dif_index = i;
            }
            
            //determine if current Goodness Of Fit value is better than any previous
            if(gof < min_dif){
                min_dif = gof;
                min_dif_index = i;
            }
            
        }
        
        if(min_dif_index == null){
            return null;
        }else{
            return knownDistributions[min_dif_index];
        }

    }
    
    public static int detectMeasurementLevel(String[] observations){
        
        int level = 0; //by default we assume only labels exist
        
        int numStrings = 0, numInts = 0, numDoubles = 0;
        
        for(int i=0; i<observations.length; i++){
            
            try{
                Integer.parseInt(observations[i]);
                numInts++;
            }catch(Exception notInt){
                //it's not an integer
                
                //check if it's a double
                try{
                    Double.parseDouble(observations[i]);
                    numDoubles++;
                }catch(Exception notDouble){
                    //it's neither int, nor double ==> it must be a nominal label ==> the cycle should be stopped
                    numStrings++;
                    break;
                }
            }
        }
        
        
        
        if(numStrings > 0){
            //there's at least one label ==> every value should be considered as labels
            //System.out.println("nominal");
            level = 0;
        }else if(numInts > 0 && numDoubles == 0){
            //there are only integers ==> the variable should be considered discrete
            //System.out.println("discrete");
            level = 1;
        }else if(numDoubles > 0){
            //there's at least one floating point value ==> the variable should be considered continuous
            //System.out.println("continuous");
            level = 2;
        }
        
        
        return level;
    }
    
    public DistributionTable generateDistributionTable(String[] observations){
        throw new UnsupportedOperationException("Distribution Tables are not yet implemented!");
    }
}
