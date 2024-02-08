import { Component, Input, OnInit } from '@angular/core';
import { Orden } from '../model/orden';
import { ActivatedRoute } from '@angular/router';
import { OrdenService } from '../services/orden.service';
import { DetalleOrden } from '../model/detalleOrden';
import { Factura } from '../model/factura';
import jsPDF from 'jspdf';
import { DatePipe } from '@angular/common';
import autoTable from 'jspdf-autotable';


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


displayColumns = ['Servicio', 'Precio','cantidad','Impuesto','Vacio'];

  constructor(
    private route: ActivatedRoute,
    private service: OrdenService,
    private datePipe: DatePipe
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

  imprimirFactura(){
    let fechaFact = this.datePipe.transform(this.factura.fecha,"dd/MM/yyyy");

    const doc = new jsPDF();
    doc.setFont('roboto');
    doc.setFontSize(14);
    doc.text(`FACTURA Nº: ${this.factura.id}`, 10,5);
    doc.setFontSize(20);
    doc.text(`TALLER MECANICO: "CUATRO RUEDAS"`, 40,15);
    doc.setFontSize(14);
    doc.text(`Fecha facturacion: ${fechaFact}`, 130,5);
    doc.text(`Direccion: Calle siempre viva 245, Villa María, Cordoba, Argentina`, 10,25);
    doc.text(`Telefono: 0353 - 495687`, 10,35);
    doc.text(`Email: cuatroruedas@gmail.com`, 10,45);
    doc.text(`Tecnico a cargo: ${this.orden.tecnico.nombre}, ${this.orden.tecnico.apellido}`,130,45);
    doc.text(`-----------------------------------------------------------------------------------------------------------------`, 10,50);
    doc.text("Cliente: ", 10,55);
    doc.setFont('roboto');
    doc.text(`Nombre y Apellido: ${this.orden.vehiculo.cliente.nombre}, ${this.orden.vehiculo.cliente.apellido}`,10,65);
    doc.text(`DNI: ${this.orden.vehiculo.cliente.dni}`,10,75);
    doc.text(`Telefono: ${this.orden.vehiculo.cliente.num_tel}`,10,85);
    doc.text(`Email:  ${this.orden.vehiculo.cliente.email}`,10,95);
    /*doc.text(``,,);
    doc.text(``,,);
    doc.text(``,,);
    doc.text(``,,);
    doc.text(``,,);
    
    */
    const columnas = ['Servicio: ', 'Precio individual: ','Impuesto', 'Cantidad'];
    const cuerpo = this.orden.detallesOrden.map((obj) => [
      obj.servicio.nombre,
      obj.precioIndividual,
      obj.servicio.impuesto,
      obj.cantidad,
    ]);

    let yPrecio

    autoTable(doc, {
      startY:100,
      head: [columnas],
      body: cuerpo,
      didDrawPage: (d) => {yPrecio = d.cursor.y + 10},
    });

    doc.text(`TOTAL: ${this.precioTotal}`,140,yPrecio);



    doc.save(`Factura${this.factura.id}`);
  }

}
