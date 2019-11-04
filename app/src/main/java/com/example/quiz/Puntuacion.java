package com.example.quiz;

public class Puntuacion implements Comparable<Puntuacion>{

    private String jugador;
    private  String tiempo;
    private int puntos;

    public Puntuacion() {
        tiempo = "00:00";
    }

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

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public int compareTo(Puntuacion other) {
        if (other.getPuntos() != this.puntos)
            return other.getPuntos() - this.puntos;
        else
            return timeToSeconds(this.tiempo) - this.timeToSeconds(other.getTiempo());
    }

    private int timeToSeconds(String time){
        String aux[] = time.split(":");
        int seconds = Integer.parseInt(aux[0]) * 60 + Integer.parseInt(aux[1]);
        return seconds;
    }
}
