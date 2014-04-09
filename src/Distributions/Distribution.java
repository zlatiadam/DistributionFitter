/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;

import GoodnessOfFitEstimators.GoodnessOfFit;
import ErrorEstimators.Error;
import java.util.HashMap;

/**
 *
 * @author Zlati
 */
public abstract class Distribution implements IDistribution{
    HashMap<String,Double> parameters = null;
    String type = null;
    Error error = null;
    GoodnessOfFit gof = null;
}
