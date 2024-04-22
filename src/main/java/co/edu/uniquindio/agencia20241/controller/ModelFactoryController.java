package co.edu.uniquindio.agencia20241.controller;

import co.edu.uniquindio.agencia20241.controller.service.IModelFactoryService;
import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.agencia20241.model.Agencia;
import co.edu.uniquindio.agencia20241.model.Empleado;
import co.edu.uniquindio.agencia20241.utils.AgenciaUtils;
import javafx.scene.transform.Affine;

import java.util.List;

public class ModelFactoryController implements IModelFactoryService {
    Agencia agencia;
    AgenciaMapper mapper = AgenciaMapper.INSTANCE;

    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        System.out.println("invocación clase singleton");
        cargarDatosBase();
    }

    private void cargarDatosBase() {
        agencia = AgenciaUtils.inicializarDatos();
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setBanco(Agencia agencia) {
        this.agencia = agencia;
    }


    @Override
    public List<EmpleadoDto> obtenerEmpleados() {

        return  mapper.getEmpleadosDto(agencia.getListaEmpleados());
    }

    @Override
    public boolean agregarEmpleado(EmpleadoDto empleadoDto) {
        try{
            if(!agencia.verificarEmpleadoExistente(empleadoDto.id())) {
                Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
                getAgencia().agregarEmpleado(empleado);
            }
            return true;
        }catch (EmpleadoException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarEmpleado(String cedula) {
        boolean flagExiste = false;
        try {
            flagExiste = getAgencia().eliminarEmpleado(cedula);
        } catch (EmpleadoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarEmpleado(String cedulaActual, EmpleadoDto empleadoDto) {
        try {
            Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
            getAgencia().actualizarEmpleado(cedulaActual, empleado);
            return true;
        } catch (EmpleadoException e) {
            e.printStackTrace();
            return false;
        }
    }
}
