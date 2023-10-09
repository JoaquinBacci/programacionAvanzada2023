import { Component, OnInit } from '@angular/core';
import { Orden } from '../model/orden';
import { DetalleOrden } from '../model/detalleOrden';
import { OrdenService } from '../services/orden.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Cliente } from '../model/cliente';
import { Tecnico } from '../model/tecnico';
import { Vehiculo } from '../model/vehiculo';
import { ClienteService } from '../services/cliente.service';
import { TecnicoService } from '../services/tecnico.service';
import { VehiculoService } from '../services/vehiculo.service';
import { Servicio } from '../model/servicio';

@Component({
  selector: 'app-orden-adm',
  templateUrl: './orden-adm.component.html',
  styleUrls: ['./orden-adm.component.css']
})
export class OrdenAdmComponent implements OnInit{

  // Las acciones de esta tabla van a ser: "consultar", "editar"
  columnasOrdenes = ['cliente', 'vehiculo','fechaIngreso','acciones'];
  dataSourceOrdenes: any[];

  // Las acciones de esta tabla van a ser: "remover servicio"
  columnasServicios = ['id','nombre','precio','acciones'];
  dataSourceServicios: any[];
  total: number = 0; // Variable para mostrar el precio total de la orden
  fechaActual: Date;

  dataClientes: Cliente[];
  dataTecnicos: Tecnico[];
  dataVehiculos: Vehiculo[];

  modoEdicion = false;

  orden: Orden;
  detalleOrden: DetalleOrden

  formularioOrden: FormGroup;

  estaEnConsultaOrdenes: boolean = true;
  estaEnCrearOrden: boolean = false;
  estaEnEditarOrden: boolean = false;

  // Habilita deshabilita console.logs
  debug: boolean = true;

  flitroCliente: Cliente = {activo:true, apellido: '', direccion: '', dni: null, email: '', id: null, nombre: '', num_tel: '', vehiculos: null};
  filtroTecnico: Tecnico = {activo: null, apellido: '', direccion: '', dni: null, email: '', id: null, legajo: null, nombre: '', num_tel: ''};
  filtroOrden: Orden = {activo: true, detallesOrden: null, tecnico: this.filtroTecnico, vehiculo: null, id: null};

  constructor(
    private ordenServicio: OrdenService,
    private clienteService: ClienteService,
    private tecnicoService: TecnicoService,
    private vehiculoService: VehiculoService,
    private fb: FormBuilder
  ){
    this.formularioOrden = this.fb.group({
      cliente: [ new Cliente(), [Validators.required]],
      tecnico: [ new Tecnico(), [Validators.required]],
      vehiculo: [ new Vehiculo(), [Validators.required]],
      servicio: [this.fb.array([]), [Validators.required]],
    });
  }

  ngOnInit(): void {
      this.getAllOrdenes();
      this.getAllClientes();
      this.getAllTecnicos();
      this.fechaActual = new Date();
      if(this.debug){console.log('ngOnInit() - fechaActual: ', this.fechaActual);}
  }

  getAllOrdenes(){
    // Se recuperan las ordenes para poblar la taba de consulta de ordenes
    this.ordenServicio.getAllOrdenes().subscribe({
      next:(data)=>{
        if(data){
          this.dataSourceOrdenes = data;
        } else {
          if(this.debug){console.log('getAllOrdenes() - dataNULL: ', data);}
        }
      },
      complete:()=>{
        // TODO
      }, error: (error) =>{
        if(this.debug){console.log('getAllOrdenes() - ERROR: ', error);}
      }
    });
  }

  getAllClientes(){
    // Se recuperan los clientes para poblar el selector de clientes
    this.clienteService.onConsultar(this.flitroCliente).subscribe({
      next:(data)=>{
        if(data){
          this.dataClientes = data;
        } else {
          if(this.debug){console.log('getAllClientes() - dataNULL: ', data);}
        }
      },
      complete:()=>{
        // TODO
      }, error: (error) =>{
        if(this.debug){console.log('getAllClientes() - ERROR: ', error);   }
      }
    });
  }

  getAllTecnicos(){
    // Se recuperan los tecnicos para poblar el selector de tecnicos
    this.tecnicoService.onConsultar(this.filtroTecnico).subscribe({
      next:(data)=>{
        if(data){
          this.dataTecnicos = data;
        } else {
          if(this.debug){console.log('getAllTecnicos() - dataNULL: ', data);}
        }
      },
      complete:()=>{
        // TODO
      }, error: (error) =>{
        if(this.debug){console.log('getAllTecnicos() - ERROR: ', error);}
      }
    });
  }

  getAllVehiculos(cliente: Cliente){
    // Si se selecciono cliente:
    // Se recuperan los vehiculos para poblar el selector de vehiculos
    if(cliente){
      this.vehiculoService.getByCliente(cliente).subscribe({
        next:(data)=>{
          if(data){
            this.dataVehiculos = data;
          } else {
            if(this.debug){console.log('getAllVehiculos() - dataNULL: ', data);}
          }
        },
        complete:()=>{
          // TODO
        }, error: (error) =>{
          if(this.debug){console.log('getAllVehiculos() - ERROR: ', error);}
        }
      });
    }
  }

  addServicio( servicio: Servicio ){
    // Añadir servicio a dataSourceServicios
    if(this.debug){console.log("addServicio(): ",servicio)}

    this.dataSourceServicios.push(servicio);

    this.calcularTotal();
  }

  removeServicio(servicioAEliminar: Servicio){
    // Quitar servicio de dataSourceServicios
    if(this.debug){console.log("removeServicio(): ",servicioAEliminar)}

    const index = this.dataSourceServicios.indexOf(servicioAEliminar);
    if (index !== -1) {
      this.dataSourceServicios.splice(index, 1);
    }

    this.calcularTotal();
  }

  calcularTotal(){
    this.dataSourceServicios.forEach(servicio => {
      this.total += servicio.precio;
    });
  }

  getObjectOrden(){
    // Armar objeto de orden para enviar a servicio de ordenes
    let object: Orden = new Orden();
    object.activo = true;
    object.detallesOrden = this.formularioOrden.get('servicio').value;
    object.id = null;
    object.tecnico = this.formularioOrden.get('tecnico').value;
    object.vehiculo = this.formularioOrden.get('vehiculo').value;

    if(this.debug){console.log("getObjectOrden(): ",object);}
    return object
  }

  guardarOrdentrabajo(){
    let orden = this.getObjectOrden();

    this.ordenServicio.newOrden(orden).subscribe({
      next: (data) => {
        if(data){
          if(this.debug){console.log('guardarOrdentrabajo() - dataOK: ', data);}
        } else {
          if(this.debug){console.log('guardarOrdentrabajo() - dataNULL: ', data);}
        }
      },
      complete: () => {
        // Se actualiza la tabla de ordenes
        this.getAllOrdenes()
        this.formularioOrden.reset();
      },error: (error) => {
        if(this.debug){console.log('guardarOrdentrabajo() - ERROR: ', error);}
      }
    })
  }


  // Metodos para mostrar ocultar elementos en la interfaz de Ordenes
  onEditarOrdenes(){
    this.estaEnConsultaOrdenes = false;
    this.estaEnCrearOrden = false;
    this.estaEnEditarOrden = true; // <- 
  }

  onConsultarOrdenes(){
    this.estaEnConsultaOrdenes = true; // <-
    this.estaEnCrearOrden = false;
    this.estaEnEditarOrden = false;
  }

  onCrearOrdenes(){
    this.estaEnConsultaOrdenes = false;
    this.estaEnCrearOrden = true; // <-
    this.estaEnEditarOrden = false;
  }

}
