/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GoodnessOfFitEstimators;

import Distributions.Distribution;
import Distributions.EmpiricalDistribution;
import java.util.Arrays;

/**
 *
 * @author Zlati
 * Class should not be used since it's incomplete
 */
public class CramerVonMisesTest {
    
    /*
     * Source: http://www.math.u-szeged.hu/~stacho/HTM2/CIT/cs-faraw96.pdf
     */
    private static Double[][] CvMTable = new Double[][]{
        //columns (p-values)
        //dummy(0.0) 0.01     0.025,   0.05,    0.1,     0.15,    0.2,     0.25,    0.5,     0.75,    0.8,     0.85,    0.9,     0.95,    0.975,   0.99,    0.999    dummy (1.0)
        { 0.0,       0.03618, 0.04052, 0.04609, 0.05516, 0.06330, 0.07123, 0.07925, 0.12749, 0.21415, 0.24358, 0.29227, 0.33746, 0.43069, 0.51872, 0.62131, 0.77772, Double.POSITIVE_INFINITY}, //n=2
        { 0.0,       0.02481, 0.03037, 0.03658, 0.04603, 0.05428, 0.06224, 0.07027, 0.11889, 0.20938, 0.24123, 0.28403, 0.34724, 0.46119, 0.58026, 0.74262, 1.16120, Double.POSITIVE_INFINITY}, //n=1000
        { 0.0,       0.02480, 0.03035, 0.03656, 0.04601, 0.05426, 0.06222, 0.07026, 0.11888, 0.20939, 0.24124, 0.28406, 0.34730, 0.46136, 0.58061, 0.74346, 1.16204, Double.POSITIVE_INFINITY} //n=inf
    }; 
    
    private static double[] CvMTable_Pvalues = new double[]{
        0.0, 0.01, 0.025, 0.05, 0.1, 0.15, 0.2, 0.25, 0.5, 0.75, 0.8, 0.85, 0.9, 0.95, 0.975, 0.99, 0.999, 1.0
    };
    
    private static double[] CvMTable_n = new double[]{
        2, 1000, Double.POSITIVE_INFINITY
    };
    
    
    /*
     * for precise comparison of goodness of fit estimates
     * this function should be used, since the P-values returned by pValue
     * are forced into a few discrete categories
     * 
     * Although this function doesn't seem to be working, use the GOF function!!!!!!!!!!!!!!!!!!!!!
     */
    public static Double w2(Distribution distribution, Double[] observations){
        Arrays.sort(observations);
        
        Double T = 0.0;
        
        for(int i=1; i<=observations.length; i++){
            T += ((2*i-1)/(2*observations.length) - distribution.F(observations[i-1]))*
                    ((2*i-1)/(2*observations.length) - distribution.F(observations[i-1]));
        }
        
        T = 1/(12*observations.length)+T;
        
        Double w2 = T/observations.length;
        
        return w2;
    }
    
    public static Double pValue(Double[] observations, Distribution distribution){
        
        double w2 = w2(distribution, observations);

        //find the proper row in the table
        double min_dif = Math.abs(CvMTable_n[0]-observations.length);
        int min_dif_index = 0;
        
        for(int i=1; i<CvMTable_n.length; i++){
            if(Math.abs(CvMTable_n[i]-observations.length) <= min_dif){
                min_dif = Math.abs(CvMTable_n[i]-observations.length);
                min_dif_index = i;
            }
        }
        
        
        //find the proper column in the table
        int column_index = 0;
        for(int i=1; i<CvMTable[min_dif_index].length; i++){
            if(CvMTable[min_dif_index][i-1] < w2 && w2 <= CvMTable[min_dif_index][i]){
                column_index = i;
                break;
            }
        }
        
        return CvMTable_Pvalues[column_index];
    }
    
    /*
     * The only used function...
     */
    public static Double calculateTestStatistic(Double[] observations, Distribution distribution){
        
        double sum = 0.0;
        
        EmpiricalDistribution empDist = new EmpiricalDistribution(observations);
        
        Double areaUnderEmpiricalCurve, areaUnderTheoreticalCurve;
        
        for(int i=0; i<observations.length; i++){
            areaUnderEmpiricalCurve = empDist.F(observations[i]);
            areaUnderTheoreticalCurve = distribution.F(observations[i]);
            sum += (areaUnderEmpiricalCurve - areaUnderTheoreticalCurve)*(areaUnderEmpiricalCurve - areaUnderTheoreticalCurve);
        }
        
        return sum;
    }
    
}
