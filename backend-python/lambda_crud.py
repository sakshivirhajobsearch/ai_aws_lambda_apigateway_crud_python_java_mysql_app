# lambda_crud.py

import boto3
import os
from dotenv import load_dotenv
from ai_module import analyze_text
from botocore.exceptions import ClientError

load_dotenv()

AWS_ACCESS_KEY = os.getenv("AWS_ACCESS_KEY")
AWS_SECRET_KEY = os.getenv("AWS_SECRET_KEY")
AWS_REGION = os.getenv("AWS_REGION")

if not AWS_ACCESS_KEY or not AWS_SECRET_KEY or not AWS_REGION:
    raise ValueError("AWS credentials or region not found in .env")

lambda_client = boto3.client(
    'lambda',
    aws_access_key_id=AWS_ACCESS_KEY,
    aws_secret_access_key=AWS_SECRET_KEY,
    region_name=AWS_REGION
)

print(f"✅ Connected to AWS Lambda in region {AWS_REGION}")

# -------------------------------
# CRUD Functions
# -------------------------------

def list_lambda_functions():
    """List all Lambda functions"""
    try:
        response = lambda_client.list_functions()
        functions = [
            {
                "FunctionName": f.get("FunctionName"),
                "Runtime": f.get("Runtime"),
                "Description": f.get("Description")
            }
            for f in response.get("Functions", [])
        ]
        return functions
    except ClientError as e:
        return {"error": str(e)}


def create_lambda_function(data):
    """Create a Lambda function (mocked)"""
    function_name = data.get("FunctionName", "new_function")
    description = data.get("Description", "")
    category = analyze_text(description)

    new_lambda = {
        "FunctionName": function_name,
        "Description": description,
        "Category": category
    }
    return {"status": "created", "function": new_lambda}


def delete_lambda_function(function_name):
    """Delete a Lambda function (mocked)"""
    try:
        # Uncomment for real deletion
        # lambda_client.delete_function(FunctionName=function_name)
        return {"status": "deleted", "function": function_name}
    except ClientError as e:
        return {"error": str(e)}
