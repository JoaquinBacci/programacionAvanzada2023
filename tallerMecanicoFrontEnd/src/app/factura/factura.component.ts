import { Component, Input, OnInit } from '@angular/core';
import { Orden } from '../model/orden';
import { ActivatedRoute } from '@angular/router';
import { OrdenService } from '../services/orden.service';
import { DetalleOrden } from '../model/detalleOrden';
import { Factura } from '../model/factura';

@Component({
  selector: 'app-factura',
  templateUrl: './factura.component.html',
  styleUrls: ['./factura.component.css']
})


export class FacturaComponent implements OnInit {
  id;
  precioTotal: number = 0;
  orden: Orden 
  /*= {
    id: 1,
    activo: true,
    detallesOrden: [
        {
            id: 1,
            servicio: {
                id: 1,
                nombre: "Cambio de aceite",
                precio: 50.0,
                activo: true,
                descripcion: "Incluye cambio de filtro de aceite"
            },
            cantidad: 1,
            precioIndividual: 50.0,
            precioTotal: 50.0
        }
    ],
    tecnico: {
        id: 1,
        dni: 12345678,
        num_tel: "123-456-7890",
        legajo: 101,
        nombre: "Juan",
        apellido: "Perez",
        direccion: "Calle 123",
        email: "juan.perez@email.com",
        activo: true
    },
    vehiculo: {
        id: 1,
        kilometraje: 50000,
        patente: "ABC123",
        marca: {
            id: 1,
            nombre: "Ford",
            activo: true
        },
        modelo: {
            id: 1,
            nombre: "Fiesta",
            activo: true,
            marca: {
                id: 1,
                nombre: "Ford",
                activo: true
            }
        },
        cliente: {
            id: 1,
            dni: 12345678,
            num_tel: "123-456-7890",
            nombre: "Juan",
            apellido: "Perez",
            direccion: "Calle 123",
            email: "juan.perez@email.com",
            activo: true,
            licenciaConducir: "Licencia123456"
        },
        activo: true,
        anio: 2022
    },
    fechaIngreso: "2023-12-19T23:44:23.800+00:00",
    estado: "creada",
    descripcion: ""
};*/

factura: Factura;


displayColumns = ['Servicio', 'Precio','Vacio'];

  constructor(
    private route: ActivatedRoute,
    private service: OrdenService,
  ){}
  
  ngOnInit(): void {
    this.factura = history.state.data;
    console.log('fac recibida: ', this.factura);
    this.orden = this.factura.orden;
      /*this.route.params.subscribe(params => {
        this.id = params['id'];

        this.service.getOrdenById(this.id).subscribe({
          next(value) {
              this.orden = value;
          }, error(err) {
              console.log('error: ', err)
          },
        })      

      });*/
      for (const detalle of this.orden.detallesOrden) {
        this.precioTotal += detalle.precioTotal;
      }
  }

}
