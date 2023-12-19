package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.model.*;
import com.TallerMecanicoSpring.TM.repository.*;
import com.TallerMecanicoSpring.TM.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orden")
public class OrdenRest {
    @Autowired
    OrdenService ordenService;
    
    @Autowired
    TecnicoRepository tecnicoRepository;
    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    VehiculoRepository vehiculoRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    ModeloRepository modeloRepository;

    //Método GET todas las ordenes
    @GetMapping
    public ResponseEntity<List<Orden>> findAllOrdenes(){
        return ResponseEntity.ok(this.ordenService.findAllOrdenes());
    };

    //Método GET by id para una orden
    @GetMapping(path = "/get/{id}")
    public Optional<Orden> findByIdOrden(@PathVariable Long id){
        System.out.println(" == REST GET/ID ==");
        return this.ordenService.findByIdOrden(id);
    }

    @PostMapping(path = "/cancelar")
    public ResponseEntity<Orden> cancelar (@RequestBody Orden orden){
        return ResponseEntity.ok(this.ordenService.cancelar(orden));
    } 

    @PostMapping(path = "/finalizar")
    public ResponseEntity<Orden> finalizar (@RequestBody Orden orden){
        return ResponseEntity.ok(this.ordenService.finalizar(orden));
    } 

    @PostMapping(path = "/iniciar")
    public ResponseEntity<Orden> iniciar (@RequestBody Orden orden){
        return ResponseEntity.ok(this.ordenService.iniciar(orden));
    } 

    //Método POST para una orden
    @PostMapping("/save")
    private ResponseEntity<Orden> saveOrden(@RequestBody OrdenSaveRq orden){
        try{/* 
            // Crear una nueva entidad Técnico para solucionar el problema de entidades no relacionadas
            if(orden.getTecnico()!=null && orden.getTecnico().getId() != null){
                Optional<Tecnico> tecnicoExistente = tecnicoRepository.findById(orden.getTecnico().getId());
                if(tecnicoExistente.isPresent()){
                    orden.setTecnico(tecnicoExistente.get());
                }else {
                    // Manejar el caso en que el vehículo no existe en la base de datos
                    System.out.println("El tecnico no existe en la base de datos");
                    // Puedes lanzar una excepción o manejarlo de otra manera según tus requerimientos.
                    throw new UnsupportedOperationException("El tecnico no existe en la bd");// Por ejemplo, devolver una respuesta de error.
                }
                }
    
                if (orden.getVehiculo() != null && orden.getVehiculo().getId() != null) {
                    Optional<Vehiculo> vehiculoExistente = vehiculoRepository.findById(orden.getVehiculo().getId());
                    if (vehiculoExistente.isPresent()) {
                        // Usar la instancia existente en lugar de crear una nueva
                        orden.setVehiculo(vehiculoExistente.get());
                    } else {
                        // Manejar el caso en que el vehículo no existe en la base de datos
                        System.out.println("El vehículo no existe en la base de datos");
                        // Puedes lanzar una excepción o manejarlo de otra manera según tus requerimientos.
                        throw new UnsupportedOperationException("El vehiculo no existe en la db"); // Por ejemplo, devolver una respuesta de error.
                    }
                }
    
    
                //Guardar los detalles de orden
                List<DetalleOrden> detallesOrden = new ArrayList<>();
                for(DetalleOrden detalleOrden:orden.getDetallesOrden()){
                    // Buscar el servicio existente en la base de datos por su ID
                    Optional<Servicio> servicioExistente = servicioRepository.findById(detalleOrden.getServicio().getId());
                    if (servicioExistente.isPresent()) {
                        // Si se encuentra el servicio existente, asignarlo al detalle de la orden
                        detalleOrden.setServicio(servicioExistente.get());
                        // Asignamos el precio de descuento que pone el tecnico o dejamos el precio del servicio comun
                        // el front end tiene que mandar 0 si el tecnico no modifica precio y si si modifica tiene que mandar el precio modificado
                        if(detalleOrden.getPrecioIndividual() == 0) {
                            detalleOrden.setPrecioIndividual(servicioExistente.get().getPrecio());
                        }
                        System.out.println("Precio individual" + detalleOrden.getPrecioIndividual());
                        System.out.println("Precio total" + (detalleOrden.getPrecioIndividual()  * detalleOrden.getCantidad()));
    
                        detalleOrden.setPrecioTotal(detalleOrden.getPrecioIndividual()  * detalleOrden.getCantidad());
    
                        // Agregar el detalle de la orden a la lista
                        detallesOrden.add(detalleOrden);
                    } else {
                        System.out.println("Servicio no existente en base de datos");
                        // Manejar el caso en el que el servicio no existe en la base de datos
                        // Puedes lanzar una excepción, devolver un error, o manejarlo de otra manera según tus requerimientos.
                    }
                }
                orden.setDetallesOrden(detallesOrden);*/


            Orden ordenGuardada = this.ordenService.saveOrden(orden);
            return ResponseEntity.created(new URI("/servicio/"+ordenGuardada.getId())).body(ordenGuardada);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update")
    private ResponseEntity<Orden> updateOrden(@RequestBody OrdenSaveRq orden){
        try{
            Orden ordenGuardada = this.ordenService.updateOrden(orden);
            return ResponseEntity.created(new URI("/servicio/"+ordenGuardada.getId())).body(ordenGuardada);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

   /* @PutMapping("/{id}")
    public ResponseEntity<?> actualizarOrden(@RequestBody Orden nuevaOrden, @PathVariable Long id){
        try{
            // Buscamos la orden existe con el id
            Orden ordenExistente = ordenService.findByIdOrden(id).get();
            System.out.println("ID de Orden Existente -> " + ordenExistente.getId());
            System.out.println("ID del nuevo tecnico -> "+ nuevaOrden.getTecnico().getId());

            // Actualizamos la orden
            ordenExistente.setTecnico(nuevaOrden.getTecnico());
            // Usamos el service para guardar la orden
            ordenService.saveOrden(ordenExistente);
            return new ResponseEntity<Orden>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<Orden>(HttpStatus.NOT_FOUND);
        }
    }*/

    @GetMapping("/getByCLiente/{id}")
    private ResponseEntity<List<Orden>> getByIdCliente(@PathVariable Long id){
        return ResponseEntity.ok(this.ordenService.getByIdCliente(id));
    }
     

}