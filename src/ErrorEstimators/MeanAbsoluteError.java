/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ErrorEstimators;

/**
 *
 * @author Zlati
 */
public class MeanAbsoluteError {
    public static double meanAbsoluteError(Double[] observationsA, Double[] observationsB){
        Double error_sum = 0.0;
        
        for(int i=0; i<Math.min(observationsA.length, observationsB.length); i++){
            error_sum += Math.abs(observationsA[i]-observationsB[i]);
            //System.out.println(Math.abs(observationsA[i]-observationsB[i]));
        }
        
        return error_sum / Math.min(observationsA.length, observationsB.length);
    }
    
    public static Error getError(Double[] observationsA, Double[] observationsB){
        Double error_sum = 0.0;
        
        for(int i=0; i<Math.min(observationsA.length, observationsB.length); i++){
            error_sum += Math.abs(observationsA[i]-observationsB[i]);
        }
        
        return new Error(error_sum / Math.min(observationsA.length, observationsB.length),"MAE");
    }
}
