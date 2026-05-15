package com.pinank.goldpredictor;

public class VolatilityCalculator {
    public static void main(String[] args) {
        double[] prices = {3250.0, 3270.0, 3260.0, 3300.0, 3285.0, 3310.0};
        double sigma = calculateSigma(prices);
        System.out.println("Annual volatility: " + sigma);
        System.out.println("As percentage: " + (sigma * 100) + "%");
    }

    public static double calculateSigma(double[] prices) {
        int n = prices.length;
        double[] returns = new double[n - 1];

        for (int i = 0; i < n - 1; i++) {
            returns[i] = Math.log(prices[i + 1] / prices[i]);
        }

        double sum = 0;
        for (double r : returns) {
            sum += r;
        }
        double mean = sum / returns.length;

        double variance = 0;
        for (double r : returns) {
            variance += Math.pow(r - mean, 2);
        }
        variance = variance / returns.length;

        double dailyVol = Math.sqrt(variance);

        double annualVol = dailyVol * Math.sqrt(252);

        return annualVol;
    }
}