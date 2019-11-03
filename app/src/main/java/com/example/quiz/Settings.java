package com.example.quiz;

public class Settings {
    private int nPreguntas;
    private String usuario;

    public Settings(int nPreguntas, String usuario) {
        this.nPreguntas = nPreguntas;
        this.usuario = usuario;
    }

    public int getnPreguntas() {
        return nPreguntas;
    }

    public void setnPreguntas(int nPreguntas) {
        this.nPreguntas = nPreguntas;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
