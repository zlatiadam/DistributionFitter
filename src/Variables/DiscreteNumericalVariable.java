/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Variables;

/**
 *
 * @author Zlati
 */
public class DiscreteNumericalVariable extends Variable{

    private Integer[] observations = null;
    
    public DiscreteNumericalVariable(Integer[] observations){
        super();
        this.observations = observations;
    }
    
    public DiscreteNumericalVariable(String[] observations){
        super();
        
        this.observations = new Integer[observations.length];
        
        for(int i=0; i<observations.length; i++){
            this.observations[i] = Integer.parseInt(observations[i]);
        }
    }
    
    @Override
    public int getVariableType() {
        return 1;
    }

    public Integer[] getIntegerObservations(){
        return observations;
    }

    @Override
    public Double[] getObservations() {
        Double[] obs = new Double[observations.length];
        
        for(int i=0; i<observations.length; i++){
            obs[i] = (double)observations[i];
        }
        
        return obs;
    }
    
    
}
