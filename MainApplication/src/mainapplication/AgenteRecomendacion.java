package mainapplication;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgenteRecomendacion extends jade.core.Agent {
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                // Espera a recibir un mensaje de solicitud
                ACLMessage mensaje = receive();
                if (mensaje != null) {
                    
                    // Procesa la solicitud y envía una recomendación personalizada
                    String contenido = mensaje.getContent();
                    if ("SolicitarRecomendacion".equals(contenido)) {
                        // Obtén la letra seleccionada por el usuario
                        String letra = mensaje.getUserDefinedParameter("letra");
                        String descripcion = mensaje.getUserDefinedParameter("descripcion");
                        // Procesamiento ficticio del lenguaje natural para obtener una recomendación
                        String recomendacion = ProcesadorNLP.generarRecomendacion("Tu mascota está comportándose de manera " + descripcion);

                        // Envía la recomendación
                        ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
                        respuesta.addReceiver(mensaje.getSender());
                        respuesta.setContent("Aquí tienes una recomendación personalizada: " + recomendacion);
                        send(respuesta);
                    }
                } else {
                    block();
                }
            }
        });
    }
}
