package com.pinank.goldpredictor;

public class NormalDistribution {
    public static double cdf(double x) {
        // Abramowitz and Stegun approximation
        double a1 =  0.254829592;
        double a2 = -0.284496736;
        double a3 =  1.421413741;
        double a4 = -1.453152027;
        double a5 =  1.061405429;
        double p  =  0.3275911;

        int sign = x < 0 ? -1 : 1;
        x = Math.abs(x) / Math.sqrt(2.0);

        double t = 1.0 / (1.0 + p * x);
        double y = 1.0 - (((((a5 * t + a4) * t) + a3) * t + a2) * t + a1) * t
                * Math.exp(-x * x);

        return 0.5 * (1.0 + sign * y);
    }
}
