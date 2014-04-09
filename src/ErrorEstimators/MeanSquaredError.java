/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ErrorEstimators;

/**
 *
 * @author Zlati
 */
public class MeanSquaredError {
    public static Double meanSquaredError(Double[] observationsA, Double[] observationsB){
        Double error = 0.0;
        
        for(int i=0; i<Math.min(observationsA.length, observationsB.length); i++){
            error += (observationsA[i]-observationsB[i])*(observationsA[i]-observationsB[i]);
        }
        
        return error/Math.min(observationsA.length, observationsB.length);
    }
    
    public static Error getError(Double[] observationsA, Double[] observationsB){
        Double error = 0.0;
        
        for(int i=0; i<Math.min(observationsA.length, observationsB.length); i++){
            error += (observationsA[i]-observationsB[i])*(observationsA[i]-observationsB[i]);
        }
        
        return new Error(error/Math.min(observationsA.length, observationsB.length), "MSE");
    }
}
