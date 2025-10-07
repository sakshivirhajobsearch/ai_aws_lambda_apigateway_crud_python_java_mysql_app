package com.ai.aws.lambda.apigateway.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ai.aws.lambda.apigateway.model.Lambda;

public class LambdaService {
	private final List<Lambda> lambdaList = new ArrayList<>();

	public void addLambda(Lambda lambda) {
		lambdaList.add(lambda);
	}

	public void updateLambda(int index, Lambda updatedLambda) {
		if (index >= 0 && index < lambdaList.size()) {
			lambdaList.set(index, updatedLambda);
		}
	}

	public void deleteLambda(int index) {
		if (index >= 0 && index < lambdaList.size()) {
			lambdaList.remove(index);
		}
	}

	public List<Lambda> getAllLambdas() {
		return lambdaList;
	}

	public void exportToJsonFile(String filename) {
		JSONArray jsonArray = new JSONArray();
		for (Lambda lambda : lambdaList) {
			JSONObject obj = new JSONObject();
			obj.put("functionName", lambda.getFunctionName());
			obj.put("runtime", lambda.getRuntime());
			obj.put("handler", lambda.getHandler());
			obj.put("role", lambda.getRole());
			obj.put("description", lambda.getDescription());
			obj.put("category", lambda.getCategory());
			jsonArray.put(obj);
		}

		try (FileWriter file = new FileWriter(filename)) {
			file.write(jsonArray.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
