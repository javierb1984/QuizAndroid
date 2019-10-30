package com.example.quiz;

public class Puntuacion implements Comparable<Puntuacion>{

    private String jugador;
    private int puntos;

    public Puntuacion() {}

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public int compareTo(Puntuacion other){
        return other.getPuntos() - this.puntos;
    }
}
