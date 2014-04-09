/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ErrorEstimators;

/**
 *
 * @author Zlati
 */
public class Error {
    Double value = null;
    String type = null;
    
    public Error(Double value, String type){
        this.value = value;
        this.type = type;
    }
    
    public Double getValue(){
        return this.value;
    }
    
    public String getType(){
        return this.type;
    }
}
