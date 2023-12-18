package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.Orden;
import com.TallerMecanicoSpring.TM.dao.OrdenSaveRq;
import com.TallerMecanicoSpring.TM.model.Tecnico;
import com.TallerMecanicoSpring.TM.model.Vehiculo;
import com.TallerMecanicoSpring.TM.model.DetalleOrden;
import com.TallerMecanicoSpring.TM.repository.ClienteRepository;
import com.TallerMecanicoSpring.TM.repository.DetalleOrdenRepository;
import com.TallerMecanicoSpring.TM.repository.OrdenRepository;
import com.TallerMecanicoSpring.TM.repository.ServicioRepository;
import com.TallerMecanicoSpring.TM.repository.TecnicoRepository;
import com.TallerMecanicoSpring.TM.repository.VehiculoRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {
    @Autowired
    OrdenRepository ordenRepository;

    @Autowired
    TecnicoRepository tecnicoRepository;

    @Autowired
    VehiculoRepository vehiculoRepository;

    @Autowired
    VehiculoService vehiculoService;

    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    DetalleOrdenRepository detalleRepository;

    @Autowired
    DetalleOrdenService detalleService;

    public List<Orden> findAllOrdenes() {
        return ordenRepository.findAll();
    }

    public Optional<Orden> findByIdOrden(Long id) {
        System.out.println("find By Id");
        return ordenRepository.findById(id);
    }

    @Transactional
    public Orden saveOrden(OrdenSaveRq ordenRq) {
        System.out.println("====================================== ");
        System.out.println("idTecnico: " + ordenRq.getIdTecnico());
        System.out.println("idVehiculo: " + ordenRq.getIdVehiculo());
        System.out.println("====================================== ");

        Orden ordenAGuardar = new Orden();
        System.out.println("idOrden: " + ordenRq.getIdOrden());
        if(ordenRq.getIdOrden()==null){
            //CREAR
            Date fi = new Date();
            ordenAGuardar.setFechaIngreso(fi);
            ordenAGuardar.crear();
        } else {
            //ACTUALIZAR
            Optional<Orden> ordenExitente = this.ordenRepository.findById(ordenRq.getIdOrden());

            if(ordenExitente.isPresent()){
                ordenAGuardar = ordenExitente.get();
            } else {
                throw new UnsupportedOperationException("No existe la orden en la BD");
            }
        }

        ordenAGuardar.setDescripcion(ordenRq.getDescripcion());
        ordenAGuardar.setActivo(true);

        if(ordenRq.getIdTecnico()!=null){
            Optional<Tecnico> tecnicoExistente = tecnicoRepository.findById(ordenRq.getIdTecnico());
            System.out.println("===== TECNICO: " + tecnicoExistente.get().getNombre());
            if(tecnicoExistente.isPresent()){
                ordenAGuardar.setTecnico(tecnicoExistente.get());
                System.out.println("===== TECNICO: " + ordenAGuardar.getTecnico().getNombre());
            } else{
                 throw new UnsupportedOperationException("El tecnico no existe en la BD");
            }

        } else {
            throw new UnsupportedOperationException("No hay idTecnico en la request");
        }

        if (ordenRq.getIdVehiculo() != null) {
            System.out.println("==========================================");
            System.out.println("ID de Vehiculo: " + ordenRq.getIdVehiculo());
            //Optional<Vehiculo> vehiculoExistente = this.vehiculoRepository.findById(ordenRq.getIdVehiculo());
        
            Optional<Vehiculo> vehiculoExistente = this.vehiculoService.findById(ordenRq.getIdVehiculo());

            if (vehiculoExistente.isPresent()) {
                ordenAGuardar.setVehiculo(vehiculoExistente.get());
                 System.out.println("VEHICULO PRESENT");
            } else {
                System.out.println("VEHICULO NO!!!!   PRESENT");
                throw new UnsupportedOperationException("El vehiculo no existe en la BD");
            }
        } else {
            throw new UnsupportedOperationException("No hay idVehiculo en la request");
        }
        
        if(ordenRq.getDetallesAGuardar().size() > 0){

            List<DetalleOrden> detallesOrden = new ArrayList<>();
            for(DetalleOrden detalleOrden:ordenRq.getDetallesAGuardar()){
                // Buscar el servicio existente en la base de datos por su ID
                DetalleOrden detalle =  this.detalleService.saveDetalleOrden(detalleOrden);
                detallesOrden.add(detalle);
            }
            ordenAGuardar.setDetallesOrden(detallesOrden);
        }
        
        if(ordenRq.getDetallesAEliminar().size() > 0){
            for(DetalleOrden detalleOrden:ordenRq.getDetallesAEliminar()){
                //Eliminar El detalle
                this.detalleService.deleteById(detalleOrden.getId());
            }
        }

        /*TODO: MANEJAR LOS DETALLES A ELIMINAR */
        return this.ordenRepository.save(ordenAGuardar);
    }

     //lista a guardar y lista a eliminar
        /*for(DetalleOrden detalle: ordenRq.getDetallesAGuardar()){
            detalle.setOrden(ordenGuardada);
            this.detalleRepository.save(detalle);

        }

        if(ordenRq.getDetallesAEliminar().size()>0){
            for(DetalleOrden detalle: ordenRq.getDetallesAEliminar()){
                this.detalleRepository.deleteById(detalle.getId());
            }
        }
        */

    public Orden updateOrden(OrdenSaveRq o) {
        return this.saveOrden(o);
    }

    public Orden cancelar(Orden o) {
        if ("finalizada".equals(o.getEstado())) {
            throw new UnsupportedOperationException("No se puede cancelar la Orden");
        } else {
            o = (this.ordenRepository.findById(o.getId())).get();
            o.cancelar();
            return this.ordenRepository.save(o); 
        }
    }

    public Orden iniciar(Orden o) {
        if ("creada".equals(o.getEstado())) {
            o = (this.ordenRepository.findById(o.getId())).get();
            o.iniciar();
            return this.ordenRepository.save(o);
        } else {
            throw new UnsupportedOperationException("No se puede iniciar la Orden");
        }
    }

    public Orden finalizar(Orden o) {
        if ("enCurso".equals(o.getEstado())) {
            o = (this.ordenRepository.findById(o.getId())).get();
            o.finalizar();
            return this.ordenRepository.save(o); 
        } else {
            throw new UnsupportedOperationException("No se puede finalizar la Orden");
        }
    }
    

    public List<Orden> getByIdCliente(Long id) {
        List<Orden> ordenes = this.findAllOrdenes();
        List<Orden> ordenesRs = new ArrayList<>();

        for (Orden o : ordenes) {
            if (o.getVehiculo().getCliente().getId() == id) {
                ordenesRs.add(o);
            }
        }
        return ordenesRs;
    }

}
