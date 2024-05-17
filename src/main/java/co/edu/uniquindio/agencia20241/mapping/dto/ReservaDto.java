package co.edu.uniquindio.agencia20241.mapping.dto;

import co.edu.uniquindio.agencia20241.model.Eventos;
import co.edu.uniquindio.agencia20241.model.Reserva;
import co.edu.uniquindio.agencia20241.model.Usuario;

public record ReservaDto(

        String id,
        Usuario usuario,
        Eventos evento,
        String fecha,
        String estadoReserva

) {
}
