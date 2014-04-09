/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Variables;

/**
 *
 * @author Zlati
 */
public class ContinuousNumericalVariable extends Variable {
    
    private Double[] observations = null;
    
    
    public ContinuousNumericalVariable(Double[] observations){
        this.observations = observations;
    }
    
    public ContinuousNumericalVariable(String[] observations){
        super();
        
        this.observations = new Double[observations.length];
        
        for(int i=0; i<observations.length; i++){
            this.observations[i] = Double.parseDouble(observations[i]);
        }
    }
            
            
    @Override
    public int getVariableType() {
        return 2;
    }
    
    @Override
    public Double[] getObservations(){
        return observations;
    }
    
}
