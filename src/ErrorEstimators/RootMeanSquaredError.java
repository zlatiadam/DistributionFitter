/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ErrorEstimators;

/**
 *
 * @author Zlati
 */
public class RootMeanSquaredError {
    public static Double rootMeanSquaredError(Double[] observationsA, Double[] observationsB){
        Double error = 0.0;
        
        for(int i=0; i<Math.min(observationsA.length, observationsB.length); i++){
            error += (observationsA[i]-observationsB[i])*(observationsA[i]-observationsB[i]);
        }
        
        return Math.sqrt(error/Math.min(observationsA.length, observationsB.length));
    }
    
    public static Error getError(Double[] observationsA, Double[] observationsB){
        Double error = 0.0;
        
        for(int i=0; i<Math.min(observationsA.length, observationsB.length); i++){
            error += (observationsA[i]-observationsB[i])*(observationsA[i]-observationsB[i]);
        }
        
        return new Error(Math.sqrt(error/Math.min(observationsA.length, observationsB.length)), "RMSE");
    }
}
