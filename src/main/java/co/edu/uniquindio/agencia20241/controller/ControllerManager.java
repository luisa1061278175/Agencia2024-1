package co.edu.uniquindio.agencia20241.controller;


import co.edu.uniquindio.agencia20241.controller.service.ActionObserver;
import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;
import co.edu.uniquindio.agencia20241.model.Usuario;

import java.util.ArrayList;
import java.util.List;



public class ControllerManager {
    private static ControllerManager instance;
    private List<ActionObserver> observers = new ArrayList<>();
    private List<ReservaDto> reservas = new ArrayList<>();
    private String idUsuarioAutenticado;

    private ControllerManager() {}

    public static ControllerManager getInstance() {
        if (instance == null) {
            instance = new ControllerManager();
        }
        return instance;
    }

    public void addObserver(ActionObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(ActionObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (ActionObserver observer : observers) {
            observer.onActionPerformed();
        }
    }

    public void addReserva(ReservaDto reserva) {
        reservas.add(reserva);
        notifyObservers();
    }
    public void setIdUsuarioAutenticado(String idUsuarioAutenticado) {
        this.idUsuarioAutenticado = idUsuarioAutenticado;
    }

    // Método para obtener el ID del usuario autenticado

    public String getIdUsuarioAutenticado() {
        return idUsuarioAutenticado;
    }

    public List<ReservaDto> getReservas() {
        return reservas;
    }
}
