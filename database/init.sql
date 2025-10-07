CREATE DATABASE IF NOT EXISTS ai_aws_lambda_api_gateway;

USE ai_aws_lambda_api_gateway;

CREATE TABLE IF NOT EXISTS lambda_functions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    runtime VARCHAR(100),
    handler VARCHAR(255),
    role VARCHAR(500)
);
