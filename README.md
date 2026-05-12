# Mobile Reliability & Monitoring Suite

## ?? Mobile SRE & Observability Architecture
A production-grade demonstration of Mobile Site Reliability Engineering (SRE), correlating user sentiment with deep technical observability (Metrics, Logs, and Traces).

### Key Features
1. **Android Client (Kotlin):** Instruments Firebase Performance and Sentry to emit telemetry, traces, and crash data under specific failure modes (Network Timeouts, NPEs).
2. **Sentiment Engine (Python):** An early-warning system that converts qualitative user reviews into structured reliability incidents using VADER sentiment analysis.
3. **Incident Correlation:** Links negative user sentiment directly to backend latency spikes and Sentry fingerprints for rapid triage and auto-remediation alerting.

## ??? Tech Stack
*   **Mobile:** Kotlin, Android SDK, Gradle
*   **Observability:** Sentry, Firebase Performance
*   **Data / Backend:** Python, Pandas, VADER Sentiment Analysis
