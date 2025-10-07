import json
from ai_module import analyze_text

# This is the AWS Lambda entry point
def lambda_handler(event, context):
    """
    Simulated Lambda handler for CRUD + AI processing.
    Can handle 'create', 'delete', 'list', or 'analyze' actions.
    """

    # Parse input event
    action = event.get("action", "list")
    data = event.get("data", {})

    response = {}

    if action == "list":
        # Example: List Lambda functions (mocked)
        response = [
            {"FunctionName": "process_logs", "Runtime": "python3.9", "Description": "Process log files"},
            {"FunctionName": "email_notifier", "Runtime": "python3.9", "Description": "Send emails"}
        ]

    elif action == "create":
        # Example: Create Lambda function (mocked)
        function_name = data.get("FunctionName", "new_function")
        description = data.get("Description", "")
        category = analyze_text(description)
        response = {
            "status": "created",
            "function": {
                "FunctionName": function_name,
                "Description": description,
                "Category": category
            }
        }

    elif action == "delete":
        # Example: Delete Lambda function (mocked)
        function_name = data.get("FunctionName", "")
        if function_name:
            response = {"status": "deleted", "function": function_name}
        else:
            response = {"error": "FunctionName is required for delete"}

    elif action == "analyze":
        # AI analyze text
        text = data.get("text", "")
        category = analyze_text(text)
        response = {"text": text, "category": category}

    else:
        response = {"error": f"Unknown action '{action}'"}

    return {
        "statusCode": 200,
        "body": json.dumps(response)
    }


# Optional: Run locally for testing
if __name__ == "__main__":
    test_event = {
        "action": "create",
        "data": {"FunctionName": "email_alert", "Description": "Send alert emails to users"}
    }
    result = lambda_handler(test_event, None)
    print(result)
