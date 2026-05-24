package com.pinank.goldpredictor;

import com.pinank.goldpredictor.service.AlphaVantageService;
import java.util.Scanner;

public class BlackScholesEngine {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            AlphaVantageService apiService = new AlphaVantageService();

            System.out.println("=== Gold Price Prediction System ===\n");
            System.out.println("Fetching live gold (GLD ETF) prices...\n");

            double S = apiService.fetchCurrentPrice();
            double[] prices = apiService.fetchHistoricalPrices();

            System.out.println("Current gold price: $" + String.format("%.2f", S));
            System.out.println("Fetched " + prices.length + " days of historical data\n");

            System.out.print("What is your predicted price? $");
            double K = sc.nextDouble();

            System.out.print("What day is today? (MONDAY/TUESDAY/WEDNESDAY/THURSDAY/FRIDAY): ");
            String dayString = sc.next().toUpperCase();
            Day day = Day.valueOf(dayString);
            int remainingDays = TradingCalendar.getRemainingTradingDays(day);
            double T = remainingDays / 252.0;

            double sigma = VolatilityCalculator.calculateSigma(prices);
            double r = 0.0439;

            System.out.print("Will price end above or below target? (ABOVE/BELOW): ");
            String dirInput = sc.next().toUpperCase();
            Direction direction = Direction.valueOf(dirInput);

            double prediction = probability(S, K, T, r, sigma, direction);

            System.out.println("\n" + "=".repeat(50));
            System.out.println("PREDICTION RESULTS");
            System.out.println("=".repeat(50));
            System.out.println("Current price (SPDR Gold Trust):    $" + String.format("%.2f", S));
            System.out.println("Target price:     $" + String.format("%.2f", K));
            System.out.println("Direction:        " + direction);
            System.out.println("Days remaining:   " + remainingDays);
            System.out.println("Volatility (σ):   " + String.format("%.2f%%", sigma * 100));
            System.out.println("Risk-free rate:   " + String.format("%.2f%%", r * 100));
            System.out.println("-".repeat(50));
            System.out.println("PROBABILITY:      " + String.format("%.2f%%", prediction * 100));
            System.out.println("=".repeat(50));

            sc.close();

        } catch (Exception e) {
            System.err.println(" Error: " + e.getMessage());
            e.printStackTrace();
        }
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


