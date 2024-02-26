package mainapplication;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AgenteUsuario extends Agent {

    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                // Solicitar al usuario que ingrese los datos del gato
                Map<String, Integer> datosGato = obtenerDatosGato();

                // Crear mensaje JSON con los datos del gato
                JSONObject jsonDatosGato = new JSONObject(datosGato);

                // Enviar mensaje al Agente de Recomendación
                enviarMensajeRecomendacion(jsonDatosGato);
            }
        });
    addBehaviour(new CyclicBehaviour() {
            public void action() {
                // Esperar la respuesta del Agente de Recomendación
                ACLMessage mensaje = receive();
                if (mensaje != null) {
                    // Imprimir la respuesta del Agente de Recomendación
                    System.out.println("Respuesta del Agente de Recomendación: " + mensaje.getContent());

                    // Preguntar al usuario si desea realizar otra consulta
                    if (realizarOtraConsulta()) {
                        // Solicitar al usuario que ingrese nuevamente los datos del gato
                        Map<String, Integer> datosGato = obtenerDatosGato();
                        JSONObject jsonDatosGato = new JSONObject(datosGato);
                        enviarMensajeRecomendacion(jsonDatosGato);
                    } else {
                        // Finalizar el agente usuario
                        System.out.println("Fin de las consultas.");
                        doDelete();
                    }
                } else {
                    // Si no hay mensaje, bloquear el comportamiento hasta recibir uno
                    block();
                }
            }
        });
    }

    // Método para solicitar los datos del gato al usuario y convertirlos en valores numéricos
    private Map<String, Integer> obtenerDatosGato() {
        Map<String, Integer> datosGato = new HashMap<>();

        datosGato.put("Edad", solicitarOpcion("Edad", "Cachorro", "Adulto", "Mayor"));
        datosGato.put("Raza", solicitarOpcion("Raza", "Mestizo", "Siames", "Maine Coon", "Angora", "Persa", "British Shorthair", "Sphynx", "Scottish Fold"));
        datosGato.put("Peso", solicitarOpcion("Peso", "Bajo", "Normal", "Alto"));
        datosGato.put("Sexo", solicitarOpcion("Sexo", "Macho", "Hembra"));
        datosGato.put("Tipo_pelaje", solicitarOpcion("Tipo de pelaje", "Corto", "Largo", "Desnudo"));
        datosGato.put("Ambiente", solicitarOpcion("Ambiente", "Interior", "Exterior"));
        datosGato.put("Historial_medico", solicitarOpcion("Historial médico", "Ninguno", "Vacunado", "Desparasitado"));
        datosGato.put("Nivel_actividad", solicitarOpcion("Nivel de actividad", "Alto", "Medio", "Bajo"));
        datosGato.put("Estado_esterilizacion", solicitarOpcion("Estado de esterilización", "No", "Sí"));
        datosGato.put("Vacunacion_previa", solicitarOpcion("Vacunación previa", "No", "Sí"));
        datosGato.put("Condiciones_salud", solicitarOpcion("Condiciones de salud", "Desatendida", "Regular", "Excelente"));
        datosGato.put("Comportamiento", solicitarOpcion("Comportamiento", "Amigable", "Tímido", "Agresivo"));
        datosGato.put("Historial_alimentacion", solicitarOpcion("Historial de alimentación", "Comida seca", "Comida húmeda"));
        datosGato.put("Estrés", solicitarOpcion("Nivel de estrés", "Alto", "Medio", "Bajo"));
        datosGato.put("Acceso_exterior", solicitarOpcion("Acceso exterior", "No", "Sí"));
        datosGato.put("Entorno_domestico", solicitarOpcion("Entorno doméstico", "Sin otros animales", "Con otros animales"));
        datosGato.put("Frecuencia_veterinario", solicitarOpcion("Frecuencia del veterinario", "Anual", "Semestral", "Mensual", "Bianual", "Trimestral"));
        datosGato.put("Historial_cuidado", solicitarOpcion("Historial de cuidado", "Cuidado Regular", "Cuidado Preventivo", "Cuidado Dental"));

        return datosGato;
    }

    // Método para solicitar una opción al usuario y convertirla en valor numérico
    private int solicitarOpcion(String nombreVariable, String... opciones) {
        Scanner scanner = new Scanner(System.in);
        int valor = -1;
        while (valor == -1) {
            System.out.println("Seleccione " + nombreVariable + ":");
            for (int i = 0; i < opciones.length; i++) {
                System.out.println((i+1) + ". " + opciones[i]);
            }
            System.out.print("Opción: ");
            int opcion = scanner.nextInt();
            if (opcion >= 1 && opcion <= opciones.length) {
                valor = opcion - 1;
            } else {
                System.out.println("Opción inválida. Por favor, seleccione una de las opciones proporcionadas.");
            }
        }
        return valor;
    }

    // Método para enviar mensaje al Agente de Recomendación
    private void enviarMensajeRecomendacion(JSONObject jsonDatosGato) {
        ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
        mensaje.addReceiver(new AID("AgenteRecomendacion", AID.ISLOCALNAME));
        mensaje.setContent(jsonDatosGato.toString());
        send(mensaje);
    }
 // Método para preguntar al usuario si desea realizar otra consulta
    private boolean realizarOtraConsulta() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("¿Desea realizar otra consulta? (sí/no)");
            String respuesta = scanner.nextLine().toLowerCase();
            if (respuesta.equals("sí")||respuesta.equals("si")) {
                return true;
            } else if (respuesta.equals("no")) {
                return false;
            } else {
                System.out.println("Respuesta inválida. Por favor, responda con 'sí' o 'no'.");
            }
        }
    }
}
