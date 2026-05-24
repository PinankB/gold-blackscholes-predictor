# Gold Price Prediction System (Black-Scholes Model)

An intelligent Java application that estimates the probability of the SPDR Gold Trust ETF (GLD) ending above or below a target price by a specified day of the week. The application utilizes live and historical market data from the Alpha Vantage API and applies the Black-Scholes pricing framework to calculate historical volatility and statistical probabilities.

---

##  Financial & Mathematical Foundations

While the traditional Black-Scholes model is widely used to price European financial options, this engine utilizes the underlying mathematics to calculate the **risk-neutral probability** that the asset price ($S$) will exceed a target threshold ($K$) by a specific time ($T$).

### How the Probability is Calculated

The probability that the price ends *above* the target price is determined by the cumulative distribution function (CDF) of the standard normal distribution, denoted as $N(d_2)$:

$$\text{Probability}_{\text{Above}} = N(d_2)$$

Where $d_2$ is defined as:

$$d_2 = \frac{\ln(S / K) + (r - 0.5\sigma^2)T}{\sigma\sqrt{T}}$$

* **$S$ (Current Price):** Fetched dynamically using the daily close of GLD.
* **$K$ (Target Price):** User-defined target price.
* **$T$ (Time to Maturity):** Number of remaining trading days in the week divided by 252 (annualized).
* **$r$ (Risk-free Interest Rate):** Modeled dynamically inside the engine (currently set to a baseline of 4.39%).
* **$\sigma$ (Volatility):** Annualized standard deviation of log returns calculated over a rolling 30-day window.

---

## Features

* **Live Data Integration:** Seamlessly connects to Alpha Vantage to pull real-time and historical daily closing prices for the `GLD` ETF.
* **Smart In-Memory Caching:** Limits outbound API requests by caching historical and current prices for 1 hour to prevent hitting free-tier API limits.
* **Dynamic Volatility Engine:** Calculates historical annualized volatility ($\sigma$) on-the-fly using logarithmic daily returns.
* **Trading Calendar Normalization:** Automatically adjusts weekend inputs to the nearest trading day and computes annualized time horizons based on standard market operating structures.

---

## Getting Started

### Prerequisites

* Java Development Kit (JDK) 17 or higher
* Maven 3.6+
* An Alpha Vantage API Key ([Get a free key here](https://www.alphavantage.co/support/#api-key))

### 1. Environment Configuration

The application reads your API key from your system's environment variables. Set it up before running the program:

**On Linux/macOS:**

```bash
export alpha_vantage_api_key="YOUR_ACTUAL_API_KEY"

```

**On Windows (Command Prompt):**

```cmd
set alpha_vantage_api_key=YOUR_ACTUAL_API_KEY

```

**On Windows (PowerShell):**

```powershell
$env:alpha_vantage_api_key="YOUR_ACTUAL_API_KEY"

```

### 2. Building the Project

Navigate to the root directory containing your `pom.xml` and run:

```bash
mvn clean package

```

### 3. Execution

Run the main Black-Scholes interactive engine via:

```bash
mvn exec:java -Dexec.mainClass="com.pinank.goldpredictor.BlackScholesEngine"

```

---

##  Usage Example

When you start `BlackScholesEngine`, the application guides you through an interactive console session:

```text
=== Gold Price Prediction System ===

Fetching fresh data from Alpha Vantage...
Current gold price: $218.50
Fetched 30 days of historical data

What is your predicted price? $222.00
What day is today? (MONDAY/TUESDAY/WEDNESDAY/THURSDAY/FRIDAY): TUESDAY
Will price end above or below target? (ABOVE/BELOW): ABOVE

==================================================
PREDICTION RESULTS
==================================================
Current price (SPDR Gold Trust):    $218.50
Target price:     $222.00
Direction:        ABOVE
Days remaining:   4
Volatility (σ):   14.25%
Risk-free rate:   4.39%
--------------------------------------------------
PROBABILITY:      18.42%
==================================================

```

---

## Project Architecture & Layout

* `BlackScholesEngine.java` — Core execution orchestrator containing the core probability calculations using $N(d_2)$.
* `service/AlphaVantageService.java` — Handles network interactions with the Alpha Vantage REST endpoint, parses incoming JSON arrays, and maintains local data caching.
* `VolatilityCalculator.java` — Converts historical raw prices into daily log returns, extracts statistical variance, and handles annualization transformations ($\times\sqrt{252}$).
* `NormalDistribution.java` — High-precision numerical evaluation of the Cumulative Distribution Function (CDF) using the *Abramowitz and Stegun* approximation.
* `TradingCalendar.java` — Normalizes standard weekdays into structural remaining wall-clock market trading sessions.

---

##  Disclaimer

*This tool is intended purely for educational and analytical purposes. It does not constitute financial or investment advice. Options and asset behaviors can deviate widely from standard geometric Brownian motion assumptions.*
