import pandas as pd
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer
import json
import uuid
from datetime import datetime

def analyze_reviews(input_file, output_file):
    analyzer = SentimentIntensityAnalyzer()
    df = pd.read_csv(input_file)
    
    incidents = []
    
    for index, row in df.iterrows():
        review_text = row['review_text']
        scores = analyzer.polarity_scores(review_text)
        compound_score = scores['compound']
        
        if compound_score < -0.5:
            incident = {
                "ticket_id": f"REL-{uuid.uuid4().hex[:8].upper()}",
                "timestamp": datetime.utcnow().isoformat() + "Z",
                "severity": "CRITICAL",
                "source": "user_sentiment",
                "score": compound_score,
                "description": review_text,
                "action_required": "Investigate Sentry / Firebase for correlated latency or crashes"
            }
            incidents.append(incident)
            
    with open(output_file, 'w') as f:
        json.dump(incidents, f, indent=4)
        
    print(f"Processed {len(df)} reviews. Generated {len(incidents)} critical incidents in {output_file}.")

if __name__ == "__main__":
    analyze_reviews("reviews.csv", "tickets.json")
