package com.cognitionbox.petra.examples.mas.pedestriancrossing.external;

import com.cognitionbox.petra.ast.terms.External;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@External
public class LLM {
    public String askLLM(String prompt) throws IOException, InterruptedException {
        return !API_KEY.isEmpty()?askGemini(prompt):askSimulation(prompt);
    }

    private String askGemini(String prompt) throws IOException, InterruptedException {
        CustomLogger.log("LLM: prompt: "+prompt);
        String result = callGemini("user",prompt);
        CustomLogger.log("LLM: response: "+result);
        return result;
    }

    private String askSimulation(String prompt) throws IOException, InterruptedException {
        String result = Math.random()>=0.5?"SET_GREEN":"SET_RED";
        CustomLogger.log("LLM: responded with the following: "+result);
        return result;
    }

    // Use HTTP/1.1 to avoid ECONNRESET with LM Studio 0.3.36
    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ArrayNode conversationHistory = mapper.createArrayNode();

    private static void addMessage(String role, String content) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("role", role);
        msg.put("content", content);
        conversationHistory.add(msg);
    }

    private static final String API_KEY = "AIzaSyA-mpm2soKGeNUV40Jm_zNxuwbIEx9N7JM"; // Gemini API key goes here
    // OpenAI-compatible endpoint for Gemini
    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions";

    private static String callGemini(String role, String prompt) throws IOException, InterruptedException {
        conversationHistory.removeAll();
        addMessage(role, prompt);

        ObjectNode requestJson = mapper.createObjectNode();
        // Use "gemini-1.5-flash" for the fastest possible coding iterations
        requestJson.put("model", "gemini-3.1-flash-lite-preview");//gemini-3-flash-preview");//""gemini-3-flash-preview"); // "gemini-3.1-flash-lite-preview");
        requestJson.put("temperature", 0.0);
        requestJson.put("stream", false);
        requestJson.set("messages", conversationHistory);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GEMINI_URL))
                .header("Content-Type", "application/json")
                // Pass the API key as a Bearer token
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestJson)))
                .timeout(Duration.ofSeconds(60))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Gemini Error (" + response.statusCode() + "): " + response.body());
        }

        JsonNode jsonResponse = mapper.readTree(response.body());
        return jsonResponse.path("choices").get(0).path("message").path("content").asText();
    }
}