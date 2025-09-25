# lambda_crud.py

import os
import boto3
from dotenv import load_dotenv
from botocore.exceptions import ClientError

# Load environment variables from .env file
load_dotenv()

# Create Lambda client with environment variables
lambda_client = boto3.client(
    'lambda',
    aws_access_key_id=os.getenv("AWS_ACCESS_KEY_ID"),
    aws_secret_access_key=os.getenv("AWS_SECRET_ACCESS_KEY"),
    region_name=os.getenv("AWS_REGION")
)

def list_lambda_functions():
    try:
        response = lambda_client.list_functions()
        return [func['FunctionName'] for func in response['Functions']]
    except ClientError as e:
        print("❌ AWS ClientError:", e)
        return {"error": str(e)}

def create_lambda_function(data):
    # Simulated Lambda creation response
    return {
        "status": "Simulated creation",
        "function": data.get("FunctionName")
    }
