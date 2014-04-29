/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;

import GoodnessOfFitEstimators.CramerVonMisesTest;
import GoodnessOfFitEstimators.GoodnessOfFit;
import GoodnessOfFitEstimators.PearsonsChiSquareTest;
import Utility.Descriptives;
import java.util.HashMap;

/**
 *
 * @author Zlati
 */
public class ExponentialDistribution extends Distribution{
    
    public ExponentialDistribution(HashMap<String,Double> params){
        super();
        this.parameters = params;
    }
    
    @Override
    public HashMap<String, Double> estimateDistributionParameters(Double[] observations) {
        return estimateDistributionParameters(observations, null, "ML");
    }

    @Override
    public HashMap<String, Double> estimateDistributionParameters(Double[] observations, String method) {
        return estimateDistributionParameters(observations, null, method);
    }

    @Override
    public HashMap<String, Double> estimateDistributionParameters(Double[] observations, HashMap<String, Double> params, String method) {
        
        HashMap<String,Double> estimatedParameters = new HashMap<String,Double>();
        
        Double lambda = null;
        
        if(params != null){
            //there are user defined parameters, only the remaining should be estimated
            
            lambda = params.get("lambda");
            
        }
        
        switch(method){
                case "ML":
                    if(lambda==null) lambda = 1/Descriptives.Mean(observations);
                    break;
                case "OLS":
                    throw new UnsupportedOperationException("OLS estimation for Normal distributions is not yet implemented!");
                case "GMM":
                    throw new UnsupportedOperationException("GMM estimation for Normal distributions is not yet implemented!");
                case "quantile":
                    throw new UnsupportedOperationException("Quantile-based estimation for Normal distributions is not yet implemented!");
                default:
                    throw new UnsupportedOperationException(method+" is not implemented.");
            }
        
        estimatedParameters.put("lambda", lambda);
        
        this.parameters = estimatedParameters;
        
        return estimatedParameters;
        
    }

    @Override
    public HashMap<String, Double> fit(Double[] observations) {
        return estimateDistributionParameters(observations, null, "ML");
    }

    @Override
    public HashMap<String, Double> fit(Double[] observations, String method) {
        return estimateDistributionParameters(observations, null, method);
    }

    @Override
    public HashMap<String, Double> fit(Double[] observations, HashMap<String, Double> params, String method) {
        return estimateDistributionParameters(observations, params, method);
    }

    @Override
    public HashMap<String, Double> getParameters() {
        return this.parameters;
    }

    @Override
    public Double getParameter(String name) {
        return this.parameters.get(name);
    }
    
    public static Double _ProbabilityDensityFunction(Double value, Double lambda){
        
        Double p = 0.0;
        
        if(value >= 0.0){
            p = lambda*Math.exp(-1*lambda*value);
        }else{
            p = 0.0;
        }
        
        return p;
    }
    
    @Override
    public Double ProbabilityDensityFunction(Double value) {
        Double lambda = this.parameters.get("lambda");
        return _ProbabilityDensityFunction(value, lambda);
    }

    @Override
    public Double P(Double value) {
        Double lambda = this.parameters.get("lambda");
        return _ProbabilityDensityFunction(value, lambda);
    }

    public static Double _CumulativeDistributionFunction(Double value, Double lambda){
        
        Double p = 0.0;
        
        if(lambda != null){
            p = 1 - Math.exp(-1*value*lambda);
        }
        
        return p;
    }
    
    @Override
    public Double CumulativeDistributionFunction(Double value) {
        Double lambda = this.parameters.get("lambda");
        return _CumulativeDistributionFunction(value, lambda);
    }

    @Override
    public Double F(Double value) {
        Double lambda = this.parameters.get("lambda");
        return _CumulativeDistributionFunction(value, lambda);
    }

    @Override
    public Double getError(Double[] observations, String type, Integer histogramColumnNuber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double E(Double[] observations, String type, Integer histogramColumnNuber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double GoodnessOfFit(Double[] observations, String type) {
        Double gof = 0.0;
        
        switch(type){
            case "AkaikeInformationCriterion":
                throw new UnsupportedOperationException(type+" not yet implemented.");
            case "AIC":
                throw new UnsupportedOperationException(type+" not yet implemented.");
                
            case "AndersonDarling":
                throw new UnsupportedOperationException(type+" not yet implemented.");
            case "AD":
                throw new UnsupportedOperationException(type+" not yet implemented.");
                
            case "BayesianInformationCriterion":
                throw new UnsupportedOperationException(type+" not yet implemented.");
            case "BIC":
                throw new UnsupportedOperationException(type+" not yet implemented.");
                
            case "CramerVonMises":
                gof = CramerVonMisesTest.calculateTestStatistic(observations, this);
                this.gof = new GoodnessOfFit(gof, "CvM");
                break;
            case "CvM":
                gof = CramerVonMisesTest.calculateTestStatistic(observations, this);
                this.gof = new GoodnessOfFit(gof, "CvM");
                break;
            
            case "KolmogorovSmirnov":
                throw new UnsupportedOperationException(type+" not yet implemented.");
            case "KS":
                throw new UnsupportedOperationException(type+" not yet implemented.");
                
            case "PearsonsChiSquare":
                gof = PearsonsChiSquareTest.calculateTestStatistic(observations, 10, this);
                this.gof = new GoodnessOfFit(gof, "Pearson");
                break;
            case "Pearson":
                gof = PearsonsChiSquareTest.calculateTestStatistic(observations, 10, this);
                this.gof = new GoodnessOfFit(gof, "Pearson");
                break;
            default:
                throw new UnsupportedOperationException(type+" not yet implemented.");
                
        }
        
        return gof;
    }

    @Override
    public Double GOF(Double[] observations, String type) {
        return GoodnessOfFit(observations, type);
    }

    @Override
    public GoodnessOfFitEstimators.GoodnessOfFit getGOF() {
        return this.gof;
    }

    @Override
    public Double getLastGOFTest() {
        return this.gof.getValue();
    }

    @Override
    public String getDistributionType() {
        return "Exponential";
    }
    
}
