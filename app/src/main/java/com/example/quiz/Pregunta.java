package com.example.quiz;

import android.graphics.drawable.Drawable;

public class Pregunta {

    private String texto;
    private String respuesta[];
    private int correcta;
    private Type tipo;
    private String ruta[];

    public Pregunta(){}

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String[] getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String[] respuesta) {
        this.respuesta = respuesta;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }

    public Type getTipo() {
        return tipo;
    }

    public void setTipo(Type tipo) {
        this.tipo = tipo;
    }

    public String[] getRuta() {
        return ruta;
    }

    public void setRuta(String[] ruta) {
        this.ruta = ruta;
    }
}
