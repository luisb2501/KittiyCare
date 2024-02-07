/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainapplication;

/**
 *
 * @author luisb250
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class OpenAIClient {

    public static void main(String[] args) {
        String apiKey = "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";  // Reemplaza con tu clave de API real
        String modelId = "gpt-3.5-turbo-1106";  // ID del modelo GPT-3.5 Turbo

        try {
            String apiUrl = "https://api.openai.com/v1/chat/completions";

            // Construir la lista de mensajes
            List<Message> messages = new ArrayList<>();
            messages.add(new Message("system", "You are a helpful assistant."));
            messages.add(new Message("user", "Hello!"));

            // Construir el cuerpo de la solicitud
            String postData = String.format("{\"model\": \"%s\", \"messages\": %s}", modelId, messagesToJson(messages));

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(postData);
                wr.flush();
            }

            // Leer la respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Respuesta de OpenAI: " + response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Función para convertir la lista de mensajes a formato JSON
    private static String messagesToJson(List<Message> messages) {
        StringBuilder json = new StringBuilder("[");
        for (Message message : messages) {
            json.append(String.format("{\"role\": \"%s\", \"content\": \"%s\"},", message.role, message.content));
        }
        // Eliminar la coma extra al final
        if (messages.size() > 0) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("]");
        return json.toString();
    }

    // Clase para representar un mensaje
    private static class Message {
        String role;
        String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
