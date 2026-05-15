Gold Price Prediction Engine (Black-Scholes Based)

Overview
This project is a Java-based quantitative finance engine that applies the Black-Scholes model to estimate the probability of gold reaching a specified target price within a given time horizon.
It is designed as a modular system that will eventually integrate real-time market data and perform volatility-based financial forecasting.

Objective

The goal of this project is to: 
1. Implement a Black-Scholes based probability model
2. Analyze gold price movement using statistical methods
3 .Integrate live market data through external APIs
4. Build a structured backend-style Java application for financial computation

Current Status
This project is actively under development.

Completed:

1. Core project structure setup using Maven
2. Black-Scholes mathematical model implementation
3. Volatility calculation module
4. Basic application flow design

In Progress:

1. Integration with Yahoo Finance API for live gold price data
2. JSON parsing layer using Jackson
3. Service layer refinement for API handling

Planned:

1. Improved volatility calibration using historical data
2.Data visualization layer
3. Performance optimization for real-time computation

Tech Stack
Java 17
Maven
Jackson (JSON parsing)
Java HttpClient (API requests)
Black-Scholes financial model

The project is intended to use live market data from Yahoo Finance:

Gold Futures: GC=F

API integration is currently under development and will be used to replace static or simulated inputs.

Methodology

The system uses the Black-Scholes framework to compute:
1. Probability of price movement above or below a target
2. Volatility derived from historical price data
3. Time-adjusted financial forecasting using risk-free rate assumptions

Disclaimer

This project is intended strictly for educational and research purposes. It does not provide financial advice and should not be used for real trading decisions.
