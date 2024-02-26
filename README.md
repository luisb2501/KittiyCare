# KittyCare: Sistema de Recomendación de Cuidado para Gatos

KittyCare es un sistema de recomendación de cuidado para gatos que utiliza inteligencia artificial para ofrecer consejos personalizados sobre cómo cuidar mejor a tu mascota felina. Este sistema está compuesto por tres componentes principales: un agente de usuario, un agente de recomendación y un servidor Flask que aloja un modelo entrenado de Random Forest.

## Funcionamiento del Sistema

El sistema opera de la siguiente manera:

1. **Agente de Usuario (AgenteUsuario)**: Este agente interactúa con el usuario humano para recopilar información sobre el gato, como su edad, raza, comportamiento, historial médico, entre otros. Utiliza esta información para formular consultas y enviarlas al agente de recomendación.

2. **Agente de Recomendación (AgenteRecomendacion)**: El agente de recomendación recibe las consultas del agente de usuario y las utiliza para hacer predicciones sobre el tipo de cuidado adecuado para el gato. Utiliza un modelo de Random Forest entrenado previamente para hacer estas predicciones.

3. **Servidor Flask**: Este servidor aloja una API web que acepta consultas del agente de recomendación en formato JSON. Cuando recibe una consulta, realiza predicciones utilizando el modelo de Random Forest y devuelve los resultados al agente de recomendación.

## Uso del Sistema

Para utilizar el sistema KittyCare, sigue estos pasos:

1. Ejecuta el servidor Flask utilizando el script proporcionado (`flask_server.py`).
2. Inicia los agentes `AgenteUsuario` y `AgenteRecomendacion` en una plataforma de agentes compatibles, como JADE.
3. Sigue las instrucciones proporcionadas por el agente de usuario para ingresar información sobre tu gato.
4. El sistema proporcionará recomendaciones de cuidado personalizadas basadas en la información proporcionada.

## Requisitos del Sistema

- Python 3.x
- Flask
- JADE

## Estructura del Repositorio

El repositorio de KittyCare se organiza de la siguiente manera:

- `AgenteUsuario.java`: Código fuente del agente de usuario.
- `AgenteRecomendacion.java`: Código fuente del agente de recomendación.
- `flask_server.py`: Script Python para el servidor Flask.
- `modelo_random_forest.pkl`: Modelo de Random Forest preentrenado.
- `recomendaciones.txt`: Archivo de texto que contiene las recomendaciones asociadas a las predicciones del modelo.
- Otros archivos y carpetas según sea necesario.

## Contribuciones y Problemas

Si deseas contribuir con mejoras al sistema o has encontrado algún problema, por favor crea un nuevo issue en este repositorio o envía una solicitud de extracción con tus cambios propuestos.

¡Gracias por utilizar KittyCare para el cuidado de tu gato!
