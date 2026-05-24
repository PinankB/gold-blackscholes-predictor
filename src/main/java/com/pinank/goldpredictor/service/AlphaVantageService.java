package com.pinank.goldpredictor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlphaVantageService {

    private static final String API_KEY =  System.getenv("alpha_vantage_api_key");
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private double[] cachedPrices = null;
    private double cachedCurrentPrice = 0;
    private long cacheTime = 0;
    private static final long CACHE_DURATION = 60 * 60 * 1000; // 1 hour

    public AlphaVantageService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Fetches historical gold prices (GLD ETF)
     *  array of closing prices (oldest to newest)
     */
    public double[] fetchHistoricalPrices() throws Exception {
        // Return cached data if fresh
        if (cachedPrices != null &&
                System.currentTimeMillis() - cacheTime < CACHE_DURATION) {
            System.out.println("Using cached data (saves API calls)");
            return cachedPrices;
        }

        System.out.println("Fetching fresh data from Alpha Vantage...");

        String url = BASE_URL +
                "?function=TIME_SERIES_DAILY" +
                "&symbol=GLD" +
                "&apikey=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "API call failed with status: " + response.statusCode()
            );
        }

        // Parse JSON
        JsonNode root = objectMapper.readTree(response.body());

        // Check for errors
        if (root.has("Error Message")) {
            throw new RuntimeException(
                    "API Error: " + root.get("Error Message").asText()
            );
        }

        if (root.has("Note")) {
            throw new RuntimeException(
                    "API Rate Limit: You've hit the 25 calls/day limit. " +
                            "Wait until tomorrow or upgrade your plan."
            );
        }

        // Extract time series
        JsonNode timeSeries = root.get("Time Series (Daily)");

        if (timeSeries == null) {
            throw new RuntimeException(
                    "No time series data found. API response: " +
                            response.body().substring(0, 200)
            );
        }

        // Extract prices from last 30 days
        List<Double> prices = new ArrayList<>();
        Iterator<String> dates = timeSeries.fieldNames();
        int count = 0;

        while (dates.hasNext() && count < 30) {
            String date = dates.next();
            JsonNode dayData = timeSeries.get(date);
            double closePrice = dayData.get("4. close").asDouble();
            prices.add(closePrice);
            count++;
        }

        // Alpha Vantage returns newest first, we need oldest first
        double[] result = new double[prices.size()];
        for (int i = 0; i < prices.size(); i++) {
            result[i] = prices.get(prices.size() - 1 - i);
        }

        // Cache the results
        cachedPrices = result;
        cachedCurrentPrice = prices.get(0); // Most recent = current price
        cacheTime = System.currentTimeMillis();

        return result;
    }


     // Fetches current gold price
    public double fetchCurrentPrice() throws Exception {
        // If and only if we have cached current price then use it
        if (cachedCurrentPrice > 0 &&
                System.currentTimeMillis() - cacheTime < CACHE_DURATION) {
            return cachedCurrentPrice;
        }

        // fetch historical and extract most recent
        double[] prices = fetchHistoricalPrices();
        return prices[prices.length - 1];
    }

    /**
     * Clear cache (for testing with fresh data)
     */
    public void clearCache() {
        cachedPrices = null;
        cachedCurrentPrice = 0;
        cacheTime = 0;
        System.out.println("Cache cleared - next call will fetch fresh data");
    }
}