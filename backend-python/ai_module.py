import re

# -------------------------------
# AI Module: Analyze Lambda description
# -------------------------------

def analyze_text(text):
    """
    Simple AI-based text analysis to categorize Lambda descriptions.
    Currently uses keyword-based rules.
    
    Parameters:
        text (str): Description of Lambda function
    
    Returns:
        category (str): AI-predicted category
    """
    if not text:
        return "Uncategorized"

    text = text.lower()

    # Keyword-based classification
    analytics_keywords = ["log", "metrics", "analyze", "analytics", "process"]
    notification_keywords = ["email", "notify", "alert", "message", "push"]
    security_keywords = ["auth", "token", "secure", "encryption", "security"]

    # Count keyword matches
    if any(word in text for word in analytics_keywords):
        return "Analytics"
    elif any(word in text for word in notification_keywords):
        return "Notification"
    elif any(word in text for word in security_keywords):
        return "Security"
    else:
        return "General"

# Optional: Add more functions for future AI processing
def detect_keywords(text):
    """
    Returns a list of detected keywords from predefined categories
    """
    categories = {
        "Analytics": ["log", "metrics", "analyze", "analytics", "process"],
        "Notification": ["email", "notify", "alert", "message", "push"],
        "Security": ["auth", "token", "secure", "encryption", "security"]
    }

    detected = []
    text = text.lower()
    for category, keywords in categories.items():
        for keyword in keywords:
            if re.search(r"\b" + re.escape(keyword) + r"\b", text):
                detected.append(category)
                break
    return list(set(detected))
