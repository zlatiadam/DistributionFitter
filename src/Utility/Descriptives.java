/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Zlati
 */
public class Descriptives {
    
    /*
     * returns the frequency table (histogram) of the values in the observations
     */
    public static SortedMap<Double,Integer> Histogram(Double[] observations, Integer histogramColumnNumber){
        
        SortedMap<Double, Integer> histogram = new TreeMap<Double, Integer>();
        int[] base_histogram = new int[histogramColumnNumber];
        
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
            base_histogram[i] = 0;
        }
        

        int column_index = 0;
        
        for(int i=0; i<observations.length; i++){
            
            //calculate to which column the value belongs
            if((observations[i] - min) < 0.001){
                column_index = 0;
            }else if((max - observations[i]) < 0.001){
                column_index = histogramColumnNumber-1;
            }else{
                column_index = (int)Math.floor((observations[i]-min)/rangeSize);
            }
            
            //add the value to the column
            base_histogram[column_index]++;
        }
        
        //generate a histogram from the base
        for(int i=0; i<histogramColumnNumber; i++){
            histogram.put(min+i*rangeSize+rangeSize/2, base_histogram[i]);
        }
        
        return histogram;
    }
    
    public static Double Mean(Double[] observations){
        double sum = 0.0;
        
        for(int i=0; i<observations.length; i++){
            sum += observations[i];
        }
        
        return sum/observations.length;
    }
    
    public static Double Std(Double[] observations, Double mean){
        //if the mean is not provided calculate it
        if(mean == null){
            double sum = 0.0;
        
            for(int i=0; i<observations.length; i++){
                sum += observations[i];
            }
            
            mean = sum/observations.length;
        }
        
        //calculate the standard deviation
        double sum = 0.0;
        
        for(int i=0; i<observations.length; i++)
            sum += (observations[i]-mean)*(observations[i]-mean);
        
        
        return Math.sqrt(sum/(observations.length-1));
    }
    
    public static Double Std(Double[] observations){
        return Std(observations, null);
    }
}
