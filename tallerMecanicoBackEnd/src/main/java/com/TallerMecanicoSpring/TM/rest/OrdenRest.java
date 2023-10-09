package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.model.*;
import com.TallerMecanicoSpring.TM.repository.ServicioRepository;
import com.TallerMecanicoSpring.TM.repository.VehiculoRepository;
import com.TallerMecanicoSpring.TM.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orden")
public class OrdenRest {
    @Autowired
    OrdenService ordenService;

    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    VehiculoRepository vehiculoRepository;

    //Método GET todas las ordenes
    @GetMapping
    public ResponseEntity<List<Orden>> findAllOrdenes(){
        return ResponseEntity.ok(this.ordenService.findAllOrdenes());
    };

    //Método GET by id para una orden
    @GetMapping(path = "/{id}")
    public Optional<Orden> findByIdOrden(@PathVariable Long id){
        return this.ordenService.findByIdOrden(id);
    }

    //Método POST para una orden
    @PostMapping("/save")
    private ResponseEntity<Orden> saveOrden(@RequestBody Orden orden){
        try{
            // Crear una nueva entidad Técnico para solucionar el problema de entidades no relacionadas
            if(orden.getTecnico()!=null){
            Tecnico tecnico = new Tecnico();
            tecnico.setNombre(orden.getTecnico().getNombre());
            tecnico.setApellido(orden.getTecnico().getApellido());
            tecnico.setDireccion(orden.getTecnico().getDireccion());
            tecnico.setDni(orden.getTecnico().getDni());
            tecnico.setEmail(orden.getTecnico().getEmail());
            tecnico.setLegajo(orden.getTecnico().getLegajo());
            tecnico.setNum_tel(orden.getTecnico().getNum_tel());
            if(orden.getTecnico().isActivo()){
            tecnico.setActivo(true);
            }
            orden.setTecnico(tecnico);
            }

            //Guardar el vehiculo en orden
            Optional<Vehiculo> vehiculoExistente = vehiculoRepository.findById(orden.getVehiculo().getId());
            Vehiculo vehiculo = new Vehiculo();
            if(vehiculoExistente.isPresent()){
                //Si se encuentra el vehiculo existente
                vehiculo.setId(vehiculoExistente.get().getId());
                vehiculo.setPatente(vehiculoExistente.get().getPatente());
                vehiculo.setMarca(vehiculoExistente.get().getMarca());
                vehiculo.setModelo(vehiculoExistente.get().getModelo());
                vehiculo.setKilometraje(vehiculoExistente.get().getKilometraje());
                vehiculo.setActivo(vehiculoExistente.get().getActivo());
            }
            orden.setVehiculo(vehiculo);

            //Guardar los detalles de orden
            List<DetalleOrden> detallesOrden = new ArrayList<>();
            for(DetalleOrden detalleOrden:orden.getDetallesOrden()){
                // Buscar el servicio existente en la base de datos por su ID
                Optional<Servicio> servicioExistente = servicioRepository.findById(detalleOrden.getServicio().getId());
                if (servicioExistente.isPresent()) {
                    // Si se encuentra el servicio existente, asignarlo al detalle de la orden
                    detalleOrden.setServicio(servicioExistente.get());
                    detalleOrden.setPrecioTotal(servicioExistente.get().getPrecio() * detalleOrden.getCantidad());

                    // Agregar el detalle de la orden a la lista
                    detallesOrden.add(detalleOrden);
                } else {
                    System.out.println("Servicio no existente en base de datos");
                    // Manejar el caso en el que el servicio no existe en la base de datos
                    // Puedes lanzar una excepción, devolver un error, o manejarlo de otra manera según tus requerimientos.
                }
            }
            orden.setDetallesOrden(detallesOrden);


            Orden ordenGuardada = this.ordenService.saveOrden(orden);
            return ResponseEntity.created(new URI("/servicio/"+ordenGuardada.getId())).body(ordenGuardada);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarOrden(@RequestBody Orden orden, @PathVariable Long id){
        try{
            //Buscamos la orden existe con el id
            Orden ordenExistente = ordenService.findByIdOrden(id).get();
            //Actualizamos la orden
            ordenExistente.setTecnico(orden.getTecnico());
            ordenExistente.setVehiculo(orden.getVehiculo());

            ordenService.saveOrden(ordenExistente);
            return new ResponseEntity<Orden>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<Orden>(HttpStatus.NOT_FOUND);
        }
    }

}
