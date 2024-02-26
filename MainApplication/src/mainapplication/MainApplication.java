package mainapplication;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class MainApplication {
    public static void main(String[] args) {
        // Configuración de la plataforma JADE
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.MAIN_PORT, "1099");

        // Contenedor de agentes
        ContainerController containerController = jade.core.Runtime.instance().createMainContainer(profile);

        try {
            // Iniciar el agente de recomendación
            AgentController recomendacionController = containerController.createNewAgent("AgenteRecomendacion", AgenteRecomendacion.class.getName(), null);
            recomendacionController.start();

            // Iniciar el agente de usuario
            AgentController usuarioController = containerController.createNewAgent("AgenteUsuario", AgenteUsuario.class.getName(), null);
            usuarioController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}

