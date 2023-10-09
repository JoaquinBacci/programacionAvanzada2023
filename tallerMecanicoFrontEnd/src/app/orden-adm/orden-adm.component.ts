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
import { ServicioService } from '../services/servicio.service';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';

@Component({
  selector: 'app-orden-adm',
  templateUrl: './orden-adm.component.html',
  styleUrls: ['./orden-adm.component.css']
})
export class OrdenAdmComponent implements OnInit{

  // Las acciones de esta tabla van a ser: "consultar", "editar"
  columnasOrdenes = ['cliente', 'vehiculo','fechaIngreso','tecnico','acciones'];
  dataSourceOrdenes: any[];

  // Las acciones de esta tabla van a ser: "remover servicio"
  columnasServicios = ['descripcion','nombre','precio','acciones'];
  dataSourceServicios: MatTableDataSource<Servicio>;
  total: number = 0; // Variable para mostrar el precio total de la orden
  fechaActual: Date;

  arrayServicios: Servicio[] = [];

  dataClientes: Cliente[];
  dataTecnicos: Tecnico[];
  dataVehiculos: Vehiculo[];
  dataServicios: Servicio[];

  dataDetalleOrdenes: DetalleOrden[] = [];


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
  filtroOrden: Orden = {activo: true, detallesOrden: null, tecnico: this.filtroTecnico, vehiculo: null, id: null, fechaIngreso: '',descripcion:''};

  vista: string="";
  vehiculoSeleccionado: Vehiculo;
  clienteSeleccionado : Cliente;
  tecnicoSeleccionado : Tecnico;

  constructor(
    private ordenServicio: OrdenService,
    private clienteService: ClienteService,
    private tecnicoService: TecnicoService,
    private vehiculoService: VehiculoService,
    private sercicioService: ServicioService,
    private router: Router,
    private fb: FormBuilder
  ){
    this.formularioOrden = this.fb.group({
      cliente: [ new Cliente(), [Validators.required]],
      tecnico: [ new Tecnico(), [Validators.required]],
      vehiculo: [ new Vehiculo(), [Validators.required]],
      servicio: [this.fb.array([]), [Validators.required]],
      descripcion: ['',[Validators.required,Validators.maxLength(50)]]
    });
  }

  ngOnInit(): void {
      this.getAllOrdenes();
      this.getAllClientes();
      this.getAllTecnicos();
      this.getAllServicios();
      this.fechaActual = new Date();
      if(this.debug){console.log('ngOnInit() - fechaActual: ', this.fechaActual);}
  }

  getAllServicios(){
    this.sercicioService.getAllServicio().subscribe({
      next: (data)=>{
        this.dataServicios = data;
      }, complete:()=>{

      }, error:(error)=>{
        console.log('Error ger servicios: ', error);
      }

    });
  }

  getAllOrdenes(){
    // Se recuperan las ordenes para poblar la taba de consulta de ordenes
    this.ordenServicio.getAllOrdenes().subscribe({
      next:(data)=>{
        if(data){
          this.dataSourceOrdenes = data;
          console.log('Ordenes: ', data);
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

  getAllVehiculos(){
    // Si se selecciono cliente:
    // Se recuperan los vehiculos para poblar el selector de vehiculos

    let cliente: Cliente = this.formularioOrden.get('cliente').value
    if(cliente){
      this.vehiculoService.getByCliente(cliente.id).subscribe({
        next:(data)=>{
          if(data){
            this.dataVehiculos = data;
            console.log(this.dataVehiculos)
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

  setVista(vista: string){
    this.vista=vista;
  }

  addServicio(){
    let servicio: Servicio;

    servicio = this.formularioOrden.get('servicio').value;
    // Añadir servicio a dataSourceServicios
    if(this.debug){console.log("addServicio(): ",servicio)}

    this.arrayServicios.push(servicio);

    this.dataSourceServicios = new MatTableDataSource(this.arrayServicios);
    console.log("ARRAY SERVICIO:",this.arrayServicios);

    this.calcularTotal();
  }

  removeServicio(idServicio: number){
    // Quitar servicio de dataSourceServicios
    if(this.debug){console.log("removeServicio(): ",idServicio)}
    

    const indice = this.arrayServicios.findIndex(servicio => servicio.id === idServicio);

    if (indice !== -1) {
      this.arrayServicios.splice(indice, 1);
    }
    
    this.dataSourceServicios = new MatTableDataSource(this.arrayServicios);    

    this.calcularTotal();
  }

  calcularTotal(){
    this.total = 0;
      this.arrayServicios.forEach(servicio => {
        this.total = this.total + servicio.precio;
      });
  }

  getObjectOrden(){
    // Armar objeto de orden para enviar a servicio de ordenes
    let object: any = {id: null,activo: null,tecnico: {id: null},vehiculo: null,descripcion: null,fechaIngreso: null,detallesOrden: null};
    object.id = null;
    object.activo = true;
    let tecnico = this.formularioOrden.get('tecnico').value;
    object.tecnico.id = tecnico.id; 
    object.vehiculo = this.formularioOrden.get('vehiculo').value;
    object.descripcion = this.formularioOrden.get('descripcion').value;
    object.fechaIngreso = null;

    console.log("arrayServicios",this.arrayServicios);
    const detallesOrden: DetalleOrden[] = this.arrayServicios.map((servicio) => {
      const detalle: any = { id: null, servicio: {id:null},cantidad: null };
      detalle.servicio.id = servicio.id;
      detalle.cantidad = 1;
      // Puedes establecer otras propiedades en detalle si es necesario
      return detalle;
    });
    
    object.detallesOrden = detallesOrden;

    console.log("a",detallesOrden);

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
        this.router.navigate(['/home']);
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

 
  setVehiculo(v:Vehiculo){
    this.vehiculoSeleccionado = new Vehiculo();
    this.vehiculoSeleccionado = v;
  }

  deleteVehiculo(){
    this.vehiculoSeleccionado =  new Vehiculo();
  }

  setCliente(c:Cliente){
    this.clienteSeleccionado = new Cliente();
    this.clienteSeleccionado = c;
  }

  setTecnico(t:Tecnico){
    this.tecnicoSeleccionado = t;
  }

}
