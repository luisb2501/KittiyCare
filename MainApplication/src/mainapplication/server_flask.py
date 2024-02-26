from flask import Flask, request, jsonify
import joblib
import numpy as np

app = Flask(__name__)

# Cargar el modelo entrenado
modelo = joblib.load('C:\\Users\\luisb250\\Downloads\\modelo_random_forest.pkl')

# Definir una ruta para hacer predicciones
@app.route('/predict', methods=['POST'])
def predict():
    # Obtener los datos de la solicitud
    data = request.get_json(force=True)
    
    # Convertir la cadena JSON en una lista de valores numéricos
    valores = [int(value) for value in data.values()]
    
    # Realizar la predicción
    prediction = modelo.predict([valores])
    
    # Enviar la predicción como respuesta
    return jsonify(prediction.tolist())

if __name__ == '__main__':
    app.run(debug=False)

