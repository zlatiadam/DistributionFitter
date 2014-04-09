/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Zlati
 */
public class Descriptives {
    
    /*
     * returns the frequency table of the values in the observations
     * the values are stored in a HashMap and represent an unsorted histogram (for the sake of performance)
     */
    public static HashMap<Double,Integer> unsortedFrequencies(Double[] observations, Integer histogramColumnNumber){
        
        HashMap<Double, Integer> histogram = new HashMap<Double, Integer>();
        
        Integer num_temp = 0;
        Double min = observations[0], max = observations[0];
        
        //determine the max and min
        for(int i=0; i<observations.length; i++){
            if(observations[i] < min) min = observations[i];
            if(observations[i] > max) max = observations[i];
        }
        
        //define value range
        Double rangeSize = (max-min)/histogramColumnNumber;
        
        //initialize the histogram; assume every column has 0 frequency
        for(int i=0; i<histogramColumnNumber; i++){
            histogram.put(min+(i+0.5)*rangeSize, 0);
        }
        
        Double column_index = 0.0;
        
        for(int i=0; i<observations.length; i++){
            
            //calculate to which column the value belongs
            column_index = min + (Math.floor((observations[i]-min)/rangeSize)+0.5)*rangeSize;
            
            
            num_temp = histogram.get(column_index);
            
            if(num_temp == null) num_temp = 0;
            num_temp++;
            
            histogram.put(column_index, num_temp);
        }
        
        return histogram;
    }
    
    /*
     * sorted version of the above
     */
    public static SortedMap sortedFrequencies(Double[] observations, Integer histogramColumnNumber){
        //calculate the item frequencies
        HashMap<Double, Integer> frequencies = unsortedFrequencies(observations, histogramColumnNumber);
        
        //create a sorted
        SortedMap sortedFrequencies = new TreeMap(frequencies);
        
        return sortedFrequencies;
    }
    
}
