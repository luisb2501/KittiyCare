package mainapplication;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Scanner;

public class AgenteUsuario extends jade.core.Agent {

    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                Scanner scanner = new Scanner(System.in);

                while (true) {
                    // Mostrar el menú de opciones al usuario
                    System.out.println("Seleccione una opción:");
                    System.out.println("a. Consultar recomendación para su mascota");
                    System.out.println("q. Salir");

                    // Usuario ingresa la letra por teclado
                    System.out.print("Ingresa una letra (a) o 'q' para salir: ");
                    String letraUsuario = scanner.nextLine().toLowerCase();

                    // Verifica si el usuario quiere salir del bucle
                    if (letraUsuario.equals("q")) {
                        System.out.println("¡Hasta luego! Gracias por usar el sistema de recomendación.");
                        break; // Sale del bucle si el usuario ingresa 'q'
                    }

                    if (letraUsuario.equals("a")) {
                        // Si el usuario selecciona "a", permite ingresar una descripción
                        System.out.print("Describe cómo está tu mascota: ");
                        String descripcion = scanner.nextLine();

// Crea un mensaje de solicitud y adjunta la letra seleccionada y la descripción
                        ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
                        mensaje.addReceiver(new AID("AgenteRecomendacion", AID.ISLOCALNAME));
                        mensaje.setContent("SolicitarRecomendacion");
                        mensaje.addUserDefinedParameter("letra", letraUsuario);
                        mensaje.addUserDefinedParameter("descripcion", descripcion);

// Imprime el mensaje enviado para depuración
                        System.out.println("Mensaje enviado: " + mensaje.getContent());

                        send(mensaje);
                    }

                    // Espera la respuesta del AgenteRecomendacion
                    ACLMessage respuesta = blockingReceive();
                    if (respuesta != null) {
                        // Procesa la respuesta
                        System.out.println("AgenteUsuario recibió la recomendación: " + respuesta.getContent());
                    }
                }

                // Cierra el scanner
                scanner.close();

                // Finaliza todo el programa
                System.exit(0);
            }
        });
    }
}
