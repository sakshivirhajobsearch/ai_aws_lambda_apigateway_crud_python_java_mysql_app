from flask import Flask, jsonify, request
from flask_cors import CORS
from lambda_crud import list_lambda_functions, create_lambda_function, delete_lambda_function
from ai_module import analyze_text

app = Flask(__name__)
CORS(app)

# Root route
@app.route('/')
def index():
    return jsonify({
        "message": "✅ AI + AWS Lambda + API Gateway Flask Backend is Running",
        "endpoints": [
            "/lambda/functions",
            "/lambda/create",
            "/lambda/delete/<name>",
            "/ai/analyze"
        ]
    })

# List Lambda functions
@app.route('/lambda/functions', methods=['GET'])
def get_functions():
    return jsonify(list_lambda_functions())

# Create Lambda function
@app.route('/lambda/create', methods=['POST'])
def create_function():
    data = request.get_json()
    return jsonify(create_lambda_function(data))

# Delete Lambda function
@app.route('/lambda/delete/<name>', methods=['DELETE'])
def delete_function(name):
    return jsonify(delete_lambda_function(name))

# AI analyze
@app.route('/ai/analyze', methods=['POST'])
def ai_analyze():
    data = request.get_json()
    text = data.get("text", "")
    category = analyze_text(text)
    return jsonify({"text": text, "category": category})

# Run Flask
if __name__ == '__main__':
    print("Starting Flask backend on http://127.0.0.1:5000")
    app.run(debug=True)
