package com.ai.aws.lambda.apigateway.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LambdaRepository {
	
	private static final String BASE_URL = "http://localhost:5000";

	public static List<String> getAllFunctions() {
		List<String> result = new ArrayList<>();
		try {
			URL url = new URL(BASE_URL + "/lambda/functions");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder json = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
			reader.close();

			// Remove brackets and quotes
			String[] items = json.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
			result.addAll(Arrays.asList(items));

		} catch (Exception e) {
			result.add("Error: " + e.getMessage());
		}
		return result;
	}

	public static String analyzeText(String text) {
		try {
			URL url = new URL(BASE_URL + "/ai/analyze");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			String jsonInput = "{\"text\":\"" + text + "\"}";
			conn.getOutputStream().write(jsonInput.getBytes());

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String response = reader.readLine();
			reader.close();

			if (response.contains("category")) {
				return response.split("category\":\"")[1].split("\"")[0];
			}
		} catch (Exception e) {
			return "AI Error";
		}
		return "Unknown";
	}
}
