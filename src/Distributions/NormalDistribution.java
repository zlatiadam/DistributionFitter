/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;

import ErrorEstimators.MeanAbsoluteError;
import ErrorEstimators.MeanSquaredError;
import ErrorEstimators.RootMeanSquaredError;
import GoodnessOfFitEstimators.CramerVonMisesTest;
import GoodnessOfFitEstimators.GoodnessOfFit;
import GoodnessOfFitEstimators.PearsonsChiSquareTest;
import Utility.Descriptives;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;

/**
 *
 * @author Zlati
 */
public class NormalDistribution extends Distribution {

    public NormalDistribution(HashMap<String,Double> params){
        super();
        this.parameters = params;
    }

    @Override
    public HashMap<String,Double> estimateDistributionParameters(Double[] observations) {
        return estimateDistributionParameters(observations, null, "ML");
    }

    @Override
    public HashMap<String,Double> estimateDistributionParameters(Double[] observations, String method) {
        return estimateDistributionParameters(observations, null, method);
    }

    @Override
    public HashMap<String,Double> estimateDistributionParameters(Double[] observations, HashMap<String,Double> params, String method) {
        
        HashMap<String,Double> estimatedParameters = new HashMap<String,Double>();
        
        Double mu = null;
        Double sigma = null;
        
        if(params != null){
            //there are user defined parameters, only the remaining should be estimated
            
            mu = params.get("mu");
            sigma = params.get("sigma");
            
        }
        
        switch(method){
                case "ML":
                    if(mu==null) mu = mleMu(observations);
                    if(sigma==null) sigma = mleSigma(observations,mu);
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
        
        estimatedParameters.put("mu", mu);
        estimatedParameters.put("sigma", sigma);
        
        this.parameters = estimatedParameters;
        
        return estimatedParameters;
    }
    
    public Double mleMu(Double[] observations){
        
        double sum = 0.0;
        
        for(int i=1; i<observations.length; i++){
            sum += observations[i];
        }
        
        return sum/observations.length;
    }
    
    public Double mleSigma(Double[] observations, Double mean){
        
        //if the mean is not provided calculate it
        if(mean == null){
            double sum = 0.0;
        
            for(int i=1; i<observations.length; i++){
                sum += observations[i];
            }
            
            mean = sum/observations.length;
        }
        
        //calculate the standard deviation
        double sum = 0.0;
        
        for(int i=0; i<observations.length; i++)
            sum += Math.pow(observations[i]-mean,2);
        
        
        return Math.sqrt(sum/observations.length);
    }

    @Override
    public HashMap<String,Double> fit(Double[] observations) {
        return estimateDistributionParameters(observations, null, "ML");
    }

    @Override
    public HashMap<String,Double> fit(Double[] observations, String method) {
        return estimateDistributionParameters(observations, null, method);
    }

    @Override
    public HashMap<String,Double> fit(Double[] observations, HashMap<String,Double> params, String method) {
        return estimateDistributionParameters(observations, params, method);
    }

    @Override
    public HashMap<String,Double> getParameters() {
        return this.parameters;
    }

    @Override
    public Double getParameter(String name) {
        return this.parameters.get(name);
    }
    
    public static Double _ProbabilityDensityFunction(Double value, Double mu, Double sigma) {
        return 1/(Math.sqrt(2*Math.PI)*sigma)*Math.exp(-0.5*((value-mu)/(sigma))*((value-mu)/(sigma)));
    }

    @Override
    public Double ProbabilityDensityFunction(Double value) {
        double mu = parameters.get("mu");
        double sigma = parameters.get("sigma");
        return _ProbabilityDensityFunction(value, mu, sigma);
    }
    

    @Override
    public Double P(Double value) {
        double mu = parameters.get("mu");
        double sigma = parameters.get("sigma");
        return _ProbabilityDensityFunction(value,mu,sigma);
    }


    /*
     * Source: http://introcs.cs.princeton.edu/java/22library/Gaussian.java.html
     * Approximation error: less than 8E-16
     */
    public static Double _CumulativeDistributionFunction(Double value, Double mu, Double sigma) {
        
        double z = (value - mu)/sigma;
                
        if (z < -8.0) return 0.0;
        if (z >  8.0) return 1.0;
        double sum = 0.0, term = z;
        for (int i = 3; sum + term != sum; i += 2) {
            sum  = sum + term;
            term = term * z * z / i;
        }
        return 0.5 + sum * _ProbabilityDensityFunction(z,0.0,1.0);
    }
    
    @Override
    public Double CumulativeDistributionFunction(Double value) {
        double mu = this.parameters.get("mu");
        double sigma = this.parameters.get("sigma");
        
        return _CumulativeDistributionFunction(value, mu, sigma);
    }
    
    @Override
    public Double F(Double value) {
        double mu = this.parameters.get("mu");
        double sigma = this.parameters.get("sigma");
        
        return _CumulativeDistributionFunction(value,mu,sigma);
    }
    

    @Override
    public Double getError(Double[] observations, String type, Integer histogramColumnNumber) {
        
        Double error = 0.0;
        
        SortedMap empiricalHistogram = Descriptives.sortedFrequencies(observations, histogramColumnNumber);
        
        Double[] empiricalDensity = new Double[empiricalHistogram.size()];
        
        Double[] theoreticalDensity = new Double[empiricalHistogram.size()];
        
        Iterator it = empiricalHistogram.entrySet().iterator();
        SortedMap.Entry entry = null;
        
        
        int i=0;
        
        Double mu = this.parameters.get("mu");
        Double sigma = this.parameters.get("sigma");
        
        Double columnWidth = (Double)empiricalHistogram.lastKey()-(Double)empiricalHistogram.firstKey();
        columnWidth /= empiricalHistogram.keySet().size();
        
        //generate the histogram of the theoretical distribution
        while(it.hasNext()){
            entry = (SortedMap.Entry)it.next();
            empiricalDensity[i] = new Double((Integer)entry.getValue());
            
            Double columnProbability = 0.0;
            Double lP = _CumulativeDistributionFunction((Double)entry.getKey()-columnWidth/2, mu, sigma);
            Double uP = _CumulativeDistributionFunction((Double)entry.getKey()+columnWidth/2, mu, sigma);
            
            theoreticalDensity[i] = (uP-lP)*observations.length;
            
            i++;
        }
        

        switch(type){
            case "MAE":
                error = MeanAbsoluteError.meanAbsoluteError(theoreticalDensity, empiricalDensity);
                break;
            case "MSE":
                error = MeanSquaredError.meanSquaredError(theoreticalDensity, empiricalDensity);
                break;
            case "RMSE":
                error = RootMeanSquaredError.rootMeanSquaredError(theoreticalDensity, empiricalDensity);
                break;
            default:
                throw new UnsupportedOperationException("The "+type+" error estimation is not yet supported.");
        }
        
        return error;
    }


    
    
    @Override
    public Double E(Double[] observations, String type, Integer histogramColumnNuber) {
        return getError(observations, type, histogramColumnNuber);
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
                gof = PearsonsChiSquareTest.calculateTestStatistic(observations, (int) Math.sqrt(observations.length), this);
                this.gof = new GoodnessOfFit(gof, "Pearson");
                break;
            case "Pearson":
                gof = PearsonsChiSquareTest.calculateTestStatistic(observations, (int) Math.sqrt(observations.length), this);
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
    public Double getLastGOFTest(){
        return this.gof.getValue();
    }

    @Override
    public String getDistributionType() {
        return "Normal";
    }
    
}
