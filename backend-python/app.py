# app.py

from flask import Flask, jsonify, request
from lambda_crud import list_lambda_functions, create_lambda_function

app = Flask(__name__)

@app.route('/')
def index():
    return "✅ AI + AWS Lambda + API Gateway Flask Backend is Running"

@app.route('/lambda/functions', methods=['GET'])
def get_lambda_functions():
    return jsonify(list_lambda_functions())

@app.route('/lambda/create', methods=['POST'])
def create_lambda():
    data = request.json
    return jsonify(create_lambda_function(data))

if __name__ == '__main__':
    app.run(debug=True)
