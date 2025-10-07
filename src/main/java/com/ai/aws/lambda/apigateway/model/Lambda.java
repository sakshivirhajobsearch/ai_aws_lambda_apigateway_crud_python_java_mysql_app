package com.ai.aws.lambda.apigateway.model;

public class Lambda {
	
	private String functionName;
	private String runtime;
	private String handler;
	private String role;
	private String description;
	private String category; // AI-predicted category

	public Lambda(String functionName, String runtime, String handler, String role, String description,
			String category) {
		
		this.functionName = functionName;
		this.runtime = runtime;
		this.handler = handler;
		this.role = role;
		this.description = description;
		this.category = category;
	}

	// Getters and Setters
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	// To string
	@Override
	public String toString() {
		return "Function: " + functionName + " | Runtime: " + runtime + " | Handler: " + handler + " | Role: " + role
				+ " | Description: " + description + " | AI Category: " + category;
	}
}
