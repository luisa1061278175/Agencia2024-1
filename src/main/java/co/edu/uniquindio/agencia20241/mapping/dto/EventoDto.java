package co.edu.uniquindio.agencia20241.mapping.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoDto(
        String nombreEvento,
         String descripcionEvento,
         LocalDate fechaEvento,
         LocalTime horaEvento,
        int capacidadMaximaEvento,
         String ubicacionEvento

) {


}
