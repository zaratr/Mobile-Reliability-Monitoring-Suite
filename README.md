# Mobile Reliability & Monitoring Suite

A production-grade demonstration of Mobile Site Reliability Engineering (SRE), correlating user sentiment with deep technical observability (Metrics, Logs, and Traces).

## Architecture & Workflow

This suite is composed of two primary layers demonstrating the full incident lifecycle:

1. **Android Client (Kotlin)**: Instrumenting Firebase Performance and Sentry to emit telemetry, traces, and crash data under specific failure modes.
2. **Sentiment Engine (Python)**: A backend script acting as an early-warning system, converting qualitative user reviews into structured reliability incidents.

### The "SRE Dashboard" Scenario
This repo simulates a real-world triage pipeline:
1. **User Sentiment Drops**: The Python nalyze.py script parses reviews via VADER sentiment analysis. It flags "Payment is broken" (-0.8 score) and automatically generates a Jira-like ticket in 	ickets.json.
2. **Sentry Correlation**: The SRE team checks Sentry and sees a spike in NetworkException. Sentry groups these together using custom fingerprinting (pi-network-timeout), avoiding alert fatigue. Breadcrumbs show the last 10 actions leading up to the crash (e.g., MainActivity created -> User clicked Checkout button).
3. **Trace Metrics**: Firebase Performance and Sentry Traces confirm that the /api/v1/pay endpoint latency skyrocketed to 5000ms, resulting in a DEADLINE_EXCEEDED span status. 
4. **Resolution**: The engineering team isolates the slow backend dependency, deploying a fix to restore the Release Health (Session-Free Rate) above the 99% threshold.

## Components

### Phase 1 & 2: Mobile Observability
Located in ndroid-app/.
* **Traces & Spans**: Configured in FlakySimulator.kt to track network latency and slow background tasks.
* **Breadcrumbs**: Hooked into UI events in MainActivity.kt.
* **Contextual Tagging**: Appends membership_tier, device_model, and pp_version via the Sentry Scope in MonitoringApp.kt.
* **Symbolication**: uild.gradle.kts automates ProGuard/R8 mapping uploads so production stack traces are readable.

### Phase 3: Sentiment Engine
Located in sentiment-engine/.
* Analyzes raw CSV reviews.
* Reviews scoring below -0.5 are flagged as critical reliability incidents.
* Outputs actionable data to a structured JSON format to simulate an automated alerting queue.
