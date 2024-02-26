package mainapplication;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AgenteRecomendacion extends Agent {

    // Mapa para almacenar las recomendaciones
    private Map<String, String> recomendaciones = new HashMap<>();

    protected void setup() {
        cargarRecomendaciones(); // Cargar las recomendaciones desde el archivo

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                // Espera recibir un mensaje del AgenteUsuario
                ACLMessage mensaje = receive();
                if (mensaje != null) {
                    // Extrae la descripción del mensaje
                    String descripcion = mensaje.getContent();

                    // Realiza la solicitud al servidor Flask
                    String respuestaServidor = obtenerRespuestaServidor(descripcion);

                    // Limpiar la respuesta del servidor
                    respuestaServidor = respuestaServidor.replaceAll("[\\[\\]\"]", "").trim();

                    // Obtener la recomendación correspondiente
                    String recomendacion = recomendaciones.get(respuestaServidor);

                    // Imprimir la recomendación correspondiente
                    System.out.println("Recomendación: " + recomendacion);

                    // Envía la respuesta al AgenteUsuario
                    ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
                    respuesta.addReceiver(mensaje.getSender());
                    respuesta.setContent(recomendacion);
                    send(respuesta);
                } else {
                    // Si no hay mensaje, bloquea el comportamiento hasta recibir uno
                    block();
                }
            }
        });
    }

    // Método para cargar las recomendaciones desde el archivo de texto
    private void cargarRecomendaciones() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\luisb250\\Documents\\NetBeansProjects\\MainApplication\\src\\mainapplication\\recomendaciones.txt"));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                recomendaciones.put(partes[0].trim(), partes[1].trim());
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error al cargar las recomendaciones: " + e.getMessage());
        }
    }

    private String obtenerRespuestaServidor(String descripcion) {
        try {
            // Formatear la descripción en formato JSON
            JSONObject jsonObject = new JSONObject(descripcion);

            // Imprimir los datos justo antes de enviar la solicitud
            System.out.println("Datos enviados al servidor Flask: " + jsonObject.toString());

            // Establecer la conexión HTTP con el servidor de Flask
            URL url = new URL("http://localhost:5000/predict");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // Enviar la solicitud JSON al servidor
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonObject.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Leer la respuesta del servidor
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Imprimir la respuesta del servidor
            System.out.println("Respuesta del servidor Flask: " + response.toString());

            // Devolver la respuesta como una cadena
            return response.toString();
        } catch (Exception e) {
            // Capturar cualquier excepción y devolver un mensaje de error
            e.printStackTrace();
            return "Error al obtener la respuesta del servidor: " + e.getMessage();
        }
    }
}
