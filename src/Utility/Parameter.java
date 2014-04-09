/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

/**
 *
 * @author Zlati
 */
public class Parameter {
    String name;
    Double value;
    
    public Parameter(String name, Double value){
        this.name = name;
        this.value = value;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Double getValue(){
        return this.value;
    }
}
