/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainapplication;

/**
 *
 * @author luisb250
 */


import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.regex.Pattern;


public class ProcesadorNLP {

    public static String generarRecomendacion(String texto) {
        try {
            // Carga de modelos de OpenNLP
            InputStream sentModelIn = new FileInputStream("src/mainapplication/es-sent.bin");
            SentenceModel sentenceModel = new SentenceModel(sentModelIn);
            sentModelIn.close();

            InputStream tokenModelIn = new FileInputStream("src/mainapplication/es-token.bin");
            TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            tokenModelIn.close();

            // Creación de objetos de análisis
            SentenceDetectorME sentDetector = new SentenceDetectorME(sentenceModel);
            TokenizerME tokenizer = new TokenizerME(tokenModel);

            // División del texto en oraciones
            String[] oraciones = sentDetector.sentDetect(texto);

            // Tokenización y procesamiento de cada oración
            StringBuilder recomendacion = new StringBuilder();
            for (String oracion : oraciones) {
                String[] tokens = tokenizer.tokenize(oracion);
                // Imprimir tokens para debug
                System.out.println("Tokens: " + Arrays.toString(tokens));

                // Lógica específica para cada palabra clave
                if (contienePalabraClave(tokens, "mal")) {
                    recomendacion.append("Lamentamos escuchar que tu mascota no está bien. Te recomendamos consultar a un veterinario.\n");
                } else if (contienePalabraClave(tokens, "enferma")) {
                    recomendacion.append("Es importante cuidar bien a tu mascota cuando está enferma. Consulta a un veterinario para obtener orientación específica.\n");
                } else if (contienePalabraClave(tokens, "alegre") || contienePalabraClave(tokens, "feliz")) {
                    recomendacion.append("¡Qué bueno saber que tu mascota está alegre y feliz! Continúa brindándole amor y atención.\n");
                } else if (contienePalabraClave(tokens, "triste") || contienePalabraClave(tokens, "desanimado")) {
                    recomendacion.append("Si tu mascota está triste o desanimada, dedícale tiempo y cariño extra. Jugar y estar presente puede ayudar.\n");
                }
                // Agrega más condiciones según sea necesario
            }

            // Agrega una recomendación predeterminada si no se encontró ninguna palabra clave específica
            if (recomendacion.length() == 0) {
                recomendacion.append("Tu mascota parece estar en una situación que no puedo identificar claramente. Si persiste, te recomendaría consultar a un veterinario para obtener asesoramiento profesional.\n");
            }

            return recomendacion.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error en el procesamiento del lenguaje natural.";
        }
    }

    // Resto del código...

    private static boolean contienePalabraClave(String[] tokens, String palabraClave) {
    // Verifica si alguna palabra clave está presente en los tokens (ignora mayúsculas/minúsculas)
    for (String token : tokens) {
        if (token.toLowerCase().contains(palabraClave.toLowerCase())) {
            return true;
        }
    }
    return false;
}


}
