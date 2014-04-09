/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Variables;

import java.util.HashMap;

/**
 *
 * @author Zlati
 */
public class NominalVariable extends Variable{
    
    private String[] observations = null;
    private HashMap<String, Integer> enumTable = null;
    
    public NominalVariable(String[] observations){
        super();
        
        this.observations = observations;
        
        this.enumTable = mapObservedCategories(observations);
    }
    
    private HashMap<String, Integer> mapObservedCategories(String[] observations){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        Integer categoryCounter = 0;
        
        for(int i=0; i<observations.length; i++){
            if(!map.containsKey(observations[i])){
                map.put(observations[i], categoryCounter);
                categoryCounter++;
            }
        }
        
        return map;
    }

    @Override
    public int getVariableType() {
        return 0;
    }

    public String[] getStringObservations(){
        return observations;
    }
    
    //returns the strings coded with discrete numbers
    public Integer[] getMappedObservations() {
        
        Integer[] enumeratedObservations = new Integer[observations.length];
        
        for(int i=0; i<observations.length; i++){
            enumeratedObservations[i] = enumTable.get(observations[i]);
        }
        
        return enumeratedObservations;
    }

    @Override
    public Double[] getObservations(){
        Integer[] int_obs = getMappedObservations();
        
        Double[] obs = new Double[int_obs.length];
        
        for(int i=0; i<int_obs.length; i++){
            obs[i] = (double)int_obs[i];
        }
        
        return obs;
    }
    
}
