/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GoodnessOfFitEstimators;

import Distributions.Distribution;
import Utility.Descriptives;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Zlati
 */
public class PearsonsChiSquareTest {
    
    public static Double calculateTestStatistic(Double[] observations, int numOfObservedFrequencies, Distribution theoreticalDistribution){
        
        Double chi2 = 0.0;
        
        SortedMap<Double, Integer> empiricalFrequencies = Descriptives.Histogram(observations, numOfObservedFrequencies);
        
        Double min = empiricalFrequencies.firstKey();
        Double max = empiricalFrequencies.lastKey();
        
        double range = (max-min)/(empiricalFrequencies.keySet().size()-1);
        
        for(Map.Entry<Double, Integer> entry : empiricalFrequencies.entrySet()){
            
            double rangeProb = theoreticalDistribution.F(entry.getKey()+range/2)-theoreticalDistribution.F(entry.getKey()-range/2);
            
            chi2 += ((entry.getValue() - rangeProb*observations.length)*
                    (entry.getValue() - rangeProb*observations.length))/
                    (rangeProb*observations.length);
 
        }
        
        // /numOfObservedFrequencies
        return chi2;
    }
    
}
