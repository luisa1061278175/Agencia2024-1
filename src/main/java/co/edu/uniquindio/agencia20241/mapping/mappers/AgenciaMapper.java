package co.edu.uniquindio.agencia20241.mapping.mappers;

import co.edu.uniquindio.agencia20241.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.agencia20241.model.Empleado;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AgenciaMapper {

    AgenciaMapper INSTANCE = Mappers.getMapper(AgenciaMapper.class);

    @Named("empleadoToEmpleadoDto")
    EmpleadoDto empleadoToEmpleadoDto(Empleado empleado);

    Empleado empleadoDtoToEmpleado(EmpleadoDto empleadoDto);

    @IterableMapping(qualifiedByName = "empleadoToEmpleadoDto")
    List<EmpleadoDto> getEmpleadosDto(List<Empleado> listaEmpleados);



    @Named("mappingToEmpeladoDto")
    EmpleadoDto mappingToEmpeladoDto(Empleado empleado);

    @Mapping(target = "nombreEmpleado", source = "empleado.nombre")
    @IterableMapping(qualifiedByName = "cunetaToCuentaDto")
    EmpleadoDto clienteToClienteDto(Empleado empleado);


}

