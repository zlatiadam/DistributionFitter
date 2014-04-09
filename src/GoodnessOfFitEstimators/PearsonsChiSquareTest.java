/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GoodnessOfFitEstimators;

import Distributions.Distribution;
import Utility.Descriptives;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Zlati
 */
public class PearsonsChiSquareTest {
    
    public static Double calculateTestStatistic(Double[] observations, int numOfObservedFrequencies, Distribution theoreticalDistribution){
        
        Double chi2 = 0.0;
        
        HashMap<Double, Integer> empiricalFrequencies = Descriptives.unsortedFrequencies(observations, numOfObservedFrequencies);
        
        for(Map.Entry<Double, Integer> entry : empiricalFrequencies.entrySet()){
            
            //there's a slight modification I used to get lower test statistics since the p values are irrelevant for the 
            //distribution comparison
            
            chi2 += (entry.getValue() - theoreticalDistribution.P(entry.getKey())*observations.length/numOfObservedFrequencies*10)*
                    (entry.getValue() - theoreticalDistribution.P(entry.getKey())*observations.length/numOfObservedFrequencies*10)/
                    (theoreticalDistribution.P(entry.getKey())*observations.length/numOfObservedFrequencies*10);
            
            if(theoreticalDistribution.P(entry.getKey()) == 0.0){
                chi2 = Double.POSITIVE_INFINITY;
                break;
            }
        }
        
        return chi2/numOfObservedFrequencies;
    }
    
}
