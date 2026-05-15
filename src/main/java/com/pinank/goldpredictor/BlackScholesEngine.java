package com.pinank.goldpredictor;

import java.util.Scanner;

public class BlackScholesEngine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double S = 3300;

        System.out.println("What is the predicted price?");
        double K = sc.nextDouble();

        System.out.println("What day is today?");
        String dayString = sc.next().toUpperCase();
        Day day = Day.valueOf(dayString);
        int remainingDays = TradingCalendar.getRemainingTradingDays(day);
        double T = remainingDays / 252.0;

        double[] prices = {3250.0, 3270.0, 3260.0, 3300.0, 3285.0, 3310.0};
        double sigma = VolatilityCalculator.calculateSigma(prices);

        double r = 0.0439;

        System.out.println("Will price end above or below target? (ABOVE or BELOW):");
        String dirInput = sc.next().toUpperCase();
        Direction direction = Direction.valueOf(dirInput);

        double prediction = probability(S, K, T, r, sigma, direction);

        System.out.println("\n=== RESULTS ===");
        System.out.println("Current price: $" + S);
        System.out.println("Target price: $" + K);
        System.out.println("Days remaining: " + remainingDays);
        System.out.println("Volatility (sigma): " + (sigma * 100) + "%");
        System.out.println("Probability: " + (prediction * 100) + "% risk-neutral probability");

        sc.close();
    }

    public static double probability(double S, double K, double T,
                                     double r, double sigma,
                                     Direction direction) {
        double d2 = (Math.log(S / K) + (r - 0.5 * sigma * sigma) * T)
                / (sigma * Math.sqrt(T));

        double probAbove = NormalDistribution.cdf(d2);

        if (direction == Direction.ABOVE) {
            return probAbove;
        } else {
            return 1 - probAbove;
        }
    }
}


