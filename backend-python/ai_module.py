def analyze_text(text):
    text_lower = text.lower()
    if any(word in text_lower for word in ['process', 'etl', 'transform']):
        return "Data Processing"
    elif any(word in text_lower for word in ['sms', 'notify', 'email']):
        return "Notification"
    elif any(word in text_lower for word in ['auth', 'secure', 'token']):
        return "Security"
    else:
        return "Unknown"
