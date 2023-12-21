import { Component, OnInit } from '@angular/core';
import { Orden } from '../model/orden';
import { DetalleOrden } from '../model/detalleOrden';
import { OrdenService } from '../services/orden.service';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
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
import { OrdenSaveRq } from '../model/OrdenSaveRq';
import Toastify from 'toastify-js';
import { MatDialog } from '@angular/material/dialog';
import { VehiculoAdmComponent } from '../vehiculo-adm/vehiculo-adm.component';
import { Location } from '@angular/common';

@Component({
  selector: 'app-orden-adm',
  templateUrl: './orden-adm.component.html',
  styleUrls: ['./orden-adm.component.css'],
})
export class OrdenAdmComponent implements OnInit {
  // Las acciones de esta tabla van a ser: "consultar", "editar"
  columnasOrdenes = [
    'cliente',
    'vehiculo',
    'fechaIngreso',
    'tecnico',
    'estado',
    'acciones',
  ];
  dataSourceOrdenes: any[];

  // Las acciones de esta tabla van a ser: "remover servicio"
  columnasServicios = ['nombre', 'precio', 'acciones'];
  dataSourceServicios: MatTableDataSource<Servicio>;
  total: number = 0; // Variable para mostrar el precio total de la orden
  fechaActual: Date;

  arrayServicios: Servicio[] = [];

  dataClientes: Cliente[];
  dataTecnicos: Tecnico[];
  dataVehiculos: Vehiculo[];
  dataServicios: Servicio[];

  servicios: Servicio[] = [];

  dataDetalleOrdenes: DetalleOrden[] = [];

  modoEdicion = false;

  orden: Orden;
  detalleOrden: DetalleOrden;

  formularioOrden: FormGroup;

  estaEnConsultaOrdenes: boolean = true;
  estaEnCrearOrden: boolean = false;
  estaEnEditarOrden: boolean = false;

  estadosOrden: string[] = ['iniciada', 'cancelada', 'enCurso', 'finalizada'];

  // Habilita deshabilita console.logs
  debug: boolean = true;

  flitroCliente: Cliente = {
    activo: true,
    apellido: '',
    direccion: '',
    dni: null,
    email: '',
    id: null,
    nombre: '',
    num_tel: '',
    vehiculos: null,
    licenciaConducir: '',
  };
  filtroTecnico: Tecnico = {
    activo: null,
    apellido: '',
    direccion: '',
    dni: null,
    email: '',
    id: null,
    legajo: null,
    nombre: '',
    num_tel: '',
  };
  filtroOrden: Orden = {
    activo: true,
    detallesOrden: null,
    tecnico: this.filtroTecnico,
    vehiculo: null,
    id: null,
    fechaIngreso: '',
    descripcion: '',
    estado: '',
  };

  vista: string = '';
  vehiculoSeleccionado: Vehiculo;
  clienteSeleccionado: Cliente;
  tecnicoSeleccionado: Tecnico;

  detallesOrdenAEditar: DetalleOrden[]; // array para comparar los detalles existentes con los detalles nuevos a guardar y los que hay que eliminar :)
  constructor(
    private ordenServicio: OrdenService,
    private clienteService: ClienteService,
    private tecnicoService: TecnicoService,
    private vehiculoService: VehiculoService,
    private sercicioService: ServicioService,
    private location: Location,
    private router: Router,
    private dialog: MatDialog,
    private fb: FormBuilder
  ) {
    this.formularioOrden = this.fb.group({
      cliente: [new Cliente(), [Validators.required]],
      tecnico: [new Tecnico(), [Validators.required]],
      vehiculo: [new Vehiculo(), [Validators.required]],
      servicio: [this.fb.array([]), [Validators.required]],
      descripcion: ['', [Validators.required, Validators.maxLength(50)]],
    });
  }

  ngOnInit(): void {
    this.getAllOrdenes();
    this.getAllClientes();
    this.getAllTecnicos();
    this.getAllServicios();
    this.fechaActual = new Date();
    if (this.debug) {
      console.log('ngOnInit() - fechaActual: ', this.fechaActual);
    }
  }

  getOrdenById(id) {
    this.ordenServicio.getOrdenById(id).subscribe({
      next: (value) => {
        this.orden = value;
        this.detallesOrdenAEditar = this.orden.detallesOrden;

        this.formularioOrden
          .get('cliente')
          .setValue(this.orden.vehiculo.cliente);
        this.setCliente(this.formularioOrden.get('cliente').value);
        this.formularioOrden.get('tecnico').setValue(this.orden.tecnico);
        this.setTecnico(this.formularioOrden.get('tecnico').value);
        this.formularioOrden.get('vehiculo').setValue(this.orden.vehiculo);
        this.setVehiculo(this.formularioOrden.get('vehiculo').value);
        this.formularioOrden
          .get('descripcion')
          .setValue(this.orden.descripcion);

        const arrayServicio: Servicio[] = [];
        this.orden.detallesOrden.forEach((detalle, i) => {
          //arrayServicio.push(JSON.parse(JSON.stringify(detalle.servicio)));
          arrayServicio.push(detalle.servicio);
          arrayServicio[i].precio = detalle.precioIndividual;
        });
        this.dataSourceServicios = new MatTableDataSource(arrayServicio);
        this.arrayServicios = arrayServicio;

        this.calcularTotal();

        console.log('datasourceServicios: ', this.dataSourceServicios);
      },
      complete: () => {
        console.log('this.orden: ', this.orden);
      },
      error: (err) => {
        console.log('error ', err);
      },
    });
  }

  editar(event) {
    console.log('EDITAR: ', event);
    this.setVista('EDITAR');

    this.formularioOrden.get('cliente').disable();
    this.formularioOrden.get('vehiculo').disable();

    //BuscarOrden y setear valores del formulario.
    this.getOrdenById(event);
  }

  getAllServicios() {
    this.sercicioService.getAllServicio().subscribe({
      next: (data) => {
        this.servicios = data;
        this.dataServicios = data;
      },
      complete: () => {},
      error: (error) => {
        console.log('Error ger servicios: ', error);
      },
    });
  }

  getAllOrdenes() {
    // Se recuperan las ordenes para poblar la taba de consulta de ordenes
    this.ordenServicio.getAllOrdenes().subscribe({
      next: (data) => {
        if (data) {
          this.dataSourceOrdenes = data;
          console.log('Ordenes: ', data);
        } else {
          if (this.debug) {
            console.log('getAllOrdenes() - dataNULL: ', data);
          }
        }
      },
      complete: () => {
        // TODO
      },
      error: (error) => {
        if (this.debug) {
          console.log('getAllOrdenes() - ERROR: ', error);
        }
      },
    });
  }

  getAllClientes() {
    // Se recuperan los clientes para poblar el selector de clientes
    this.clienteService.onConsultar(this.flitroCliente).subscribe({
      next: (data) => {
        if (data) {
          this.dataClientes = data;
        } else {
          if (this.debug) {
            console.log('getAllClientes() - dataNULL: ', data);
          }
        }
      },
      complete: () => {
        // TODO
      },
      error: (error) => {
        if (this.debug) {
          console.log('getAllClientes() - ERROR: ', error);
        }
      },
    });
  }

  getAllTecnicos() {
    // Se recuperan los tecnicos para poblar el selector de tecnicos
    this.tecnicoService.onConsultar(this.filtroTecnico).subscribe({
      next: (data) => {
        if (data) {
          this.dataTecnicos = data;
        } else {
          if (this.debug) {
            console.log('getAllTecnicos() - dataNULL: ', data);
          }
        }
      },
      complete: () => {
        // TODO
      },
      error: (error) => {
        if (this.debug) {
          console.log('getAllTecnicos() - ERROR: ', error);
        }
      },
    });
  }

  getAllVehiculos() {
    //console.log('get vehiculos')
    // Si se selecciono cliente:
    // Se recuperan los vehiculos para poblar el selector de vehiculos

    let cliente: Cliente = this.formularioOrden.get('cliente').value;
    if (cliente) {
      this.vehiculoService.getByCliente(cliente.id).subscribe({
        next: (data) => {
          if (data) {
            this.dataVehiculos = data;
            console.log(this.dataVehiculos);
          } else {
            if (this.debug) {
              console.log('getAllVehiculos() - dataNULL: ', data);
            }
          }
        },
        complete: () => {
          // TODO
        },
        error: (error) => {
          if (this.debug) {
            console.log('getAllVehiculos() - ERROR: ', error);
          }
        },
      });
    }
  }

  setVista(vista: string) {
    this.vista = vista;
    if(this.vista == 'CREAR'){
      console.log('vista crear');
      this.formularioOrden.reset();
      this.formularioOrden.get('cliente').enable();
      this.formularioOrden.get('vehiculo').enable();
      this.clienteSeleccionado = null;
      this.vehiculoSeleccionado = null;
      this.tecnicoSeleccionado = null;
      this.arrayServicios=[];
      this.dataSourceServicios = new MatTableDataSource;
      this.calcularTotal();
      this.ngOnInit();
      
    }
    //NECESITO REINICIALIZAR EL COMPONENTE SI LA VISTA ES 'CREAR'

  }

  addServicio() {
    let servicio: Servicio;

    servicio = this.formularioOrden.get('servicio').value;
    // Añadir servicio a dataSourceServicios
    if (this.debug) {
      console.log('addServicio(): ', servicio);
    }

    this.arrayServicios.push(JSON.parse(JSON.stringify(servicio))); // forzar a crear una nueva instancia de Servicio (problema de referencia)

    this.dataSourceServicios = new MatTableDataSource(this.arrayServicios);
    console.log('ARRAY SERVICIO:', this.arrayServicios);

    this.calcularTotal();
  }

  removeServicio(idServicio: number) {
    // Quitar servicio de dataSourceServicios
    if (this.debug) {
      console.log('removeServicio(): ', idServicio);
    }

    const indice = this.arrayServicios.findIndex(
      (servicio) => servicio.id === idServicio
    );

    if (indice !== -1) {
      this.arrayServicios.splice(indice, 1);
    }

    this.dataSourceServicios = new MatTableDataSource(this.arrayServicios);

    this.calcularTotal();
  }

  calcularTotal() {
    this.total = 0;
    this.arrayServicios.forEach((servicio) => {
      this.total = this.total + servicio.precio;
    });
  }
  getObjectOrden(): OrdenSaveRq {
    let orden: OrdenSaveRq = new OrdenSaveRq();
    orden.idTecnico = this.formularioOrden.get('tecnico').value.id;
    orden.idVehiculo = this.formularioOrden.get('vehiculo').value.id;
    orden.descripcion = this.formularioOrden.get('descripcion').value;

    console.log('arrayServicios: ', this.arrayServicios);

    if (this.vista == 'CREAR') {
      orden.detallesAEliminar = [];

      orden.detallesAGuardar = this.arrayServicios.map((servicio) => {
        const detalle: DetalleOrden = new DetalleOrden();
        detalle.servicio = servicio;
        detalle.cantidad = 1; //por defecto es 1 y se pueden agregar varias veces el mismo servicio, creando la cantidad de detalles como servicios se añadan
        detalle.precioIndividual = servicio.precio;
        return detalle;
      });
    } else if (this.vista == 'EDITAR') {
      console.log('detallesIniciales: ', this.detallesOrdenAEditar);
      orden.detallesAGuardar = this.arrayServicios.map((servicio, i) => {
        const detalle: DetalleOrden = new DetalleOrden();
        if (
          i < this.detallesOrdenAEditar.length &&
          this.detallesOrdenAEditar[i].servicio.id === servicio.id
        ) {
          this.detallesOrdenAEditar[i].precioIndividual = servicio.precio;
          detalle.id = this.detallesOrdenAEditar[i].id;
          detalle.precioIndividual =
            this.detallesOrdenAEditar[i].precioIndividual;
          detalle.cantidad = this.detallesOrdenAEditar[i].cantidad;
          detalle.servicio = this.detallesOrdenAEditar[i].servicio;
        } else {
          detalle.servicio = servicio;
          detalle.cantidad = 1; //por defecto es 1 y se pueden agregar varias veces el mismo servicio, creando la cantidad de detalles como servicios se añadan
          detalle.precioIndividual = servicio.precio;
        }

        return detalle;
      });

      orden.detallesAEliminar = [];
      orden.detallesAEliminar = this.detallesOrdenAEditar.filter(
        (originalDetalle) => {
          return !this.arrayServicios.some(
            (servicio) => servicio.id === originalDetalle.servicio.id
          );
        }
      );
    }

    if (this.vista == 'EDITAR') {
      orden.idOrden = this.orden.id;
    }

    console.log('rq: ', orden);
    return orden;
  }

  onPrecioChange(event, index) {
    let precio = event.target.value;

    this.arrayServicios[index].precio = parseFloat(precio);

    console.log('arrayServicio: ', this.arrayServicios);
    this.calcularTotal();
  }

  guardarOrdentrabajo() {
    let ordenRq: OrdenSaveRq = this.getObjectOrden();
    console.log('ORDEN RQ: ', ordenRq);

    if (this.vista == 'CREAR') {
      this.ordenServicio.newOrden(ordenRq).subscribe({
        next: (data) => {
          if (data) {
            if (this.debug) {
              console.log('guardarOrdentrabajo() - dataOK: ', data);
            }
            Toastify({
              text: 'Orden agregada',
              duration: 3000,
              destination: 'https://github.com/apvarun/toastify-js',
              newWindow: true,
              close: true,
              gravity: 'bottom', // Cambiado a "bottom" para colocarlo en la parte inferior
              position: 'right', // Cambiado a "right" para colocarlo en la esquina derecha
              stopOnFocus: true,
              style: {
                background: 'black', // Cambiado a negro
              },
              onClick: function () {},
            }).showToast();
          } else {
            if (this.debug) {
              console.log('guardarOrdentrabajo() - dataNULL: ', data);
            }
          }
        },
        complete: () => {
          // Se actualiza la tabla de ordenes
          this.getAllOrdenes();
          this.formularioOrden.reset();
          this.router.navigate(['/home']);
        },
        error: (error) => {
          if (this.debug) {
            console.log('guardarOrdentrabajo() - ERROR: ', error);
          }
        },
      });
    } else {
      this.ordenServicio.updateOrden(ordenRq).subscribe({
        next: (data) => {
          if (data) {
            if (this.debug) {
              console.log('guardarOrdentrabajo() - dataOK: ', data);
              Toastify({
                text: 'Orden Actualizada',
                duration: 3000,
                destination: 'https://github.com/apvarun/toastify-js',
                newWindow: true,
                close: true,
                gravity: 'bottom', // Cambiado a "bottom" para colocarlo en la parte inferior
                position: 'right', // Cambiado a "right" para colocarlo en la esquina derecha
                stopOnFocus: true,
                style: {
                  background: 'black', // Cambiado a negro
                },
                onClick: function () {},
              }).showToast();
            }
          } else {
            if (this.debug) {
              console.log('guardarOrdentrabajo() - dataNULL: ', data);
            }
          }
        },
        complete: () => {
          // Se actualiza la tabla de ordenes
          this.getAllOrdenes();
          this.formularioOrden.reset();
          this.router.navigate(['/home']);
        },
        error: (error) => {
          if (this.debug) {
            console.log('guardarOrdentrabajo() - ERROR: ', error);
          }
        },
      });
    }
  }

  // Metodos para mostrar ocultar elementos en la interfaz de Ordenes
  onEditarOrdenes() {
    this.estaEnConsultaOrdenes = false;
    this.estaEnCrearOrden = false;
    this.estaEnEditarOrden = true; // <-
  }

  onEditar(id) {
    this.router.navigate([`orden/${id}`]);
  }

  onConsultarOrdenes() {
    this.estaEnConsultaOrdenes = true; // <-
    this.estaEnCrearOrden = false;
    this.estaEnEditarOrden = false;
  }

  onCrearOrdenes() {
    this.estaEnConsultaOrdenes = false;
    this.estaEnCrearOrden = true; // <-
    this.estaEnEditarOrden = false;
  }

  setVehiculo(v: Vehiculo) {
    this.vehiculoSeleccionado = new Vehiculo();
    this.vehiculoSeleccionado = v;
  }

  deleteVehiculo() {
    this.vehiculoSeleccionado = undefined;
    this.formularioOrden.get('vehiculo').reset();
  }

  setCliente(c: Cliente) {
    console.log('setCliente: ', c);
    this.clienteSeleccionado = new Cliente();
    this.clienteSeleccionado = c;
    this.deleteVehiculo();
    console.log('cliente: ' + this.clienteSeleccionado);
    //this.getAllVehiculos();
  }

  setTecnico(t: Tecnico) {
    this.tecnicoSeleccionado = t;
  }

  iniciarOrden(o: Orden) {
    this.ordenServicio.iniciarOrden(o).subscribe({
      next: (data) => {
        if (data) {
          console.log('Se actualizo el estado');
          this.getAllOrdenes();
        } else {
          console.log('NO hay data = error');
        }
      },
    });
  }

  cancelarOrden(o: Orden) {
    this.ordenServicio.cancelarOrden(o).subscribe({
      next: (data) => {
        if (data) {
          console.log('Se actualizo el estado');
          this.getAllOrdenes();
        } else {
          console.log('NO hay data = error');
        }
      },
    });
  }

  finalizarOrden(o: Orden) {
    this.ordenServicio.finalizarOrden(o).subscribe({
      next: (data) => {
        if (data) {
          console.log('Se actualizo el estado');
          this.getAllOrdenes();
        } else {
          console.log('NO hay data = error');
        }
      },
    });
  }

  imprimirFactura(o: Orden) {
    this.ordenServicio.generarFactura(o).subscribe({
      next: (data) => {
        if (data) {
          console.log('facturaaaaa: ', data);
        } else {
          console.log('NO hay data = error');
        }
      },
    });
  }

  aggVehiculo() {
    const dialog = this.dialog.open(VehiculoAdmComponent);
    dialog.afterClosed().subscribe((result) => this.getAllVehiculos());
  }

  disabledFormField() {
    const isDisabled = this.vista === 'EDITAR';
    return isDisabled;
  }

  /*get isFormFieldDisabled(): boolean {
    return this.vista === 'EDITAR';
  }*/

  getPrecio(servicio: Servicio, i: number) {
    if (this.vista == 'EDITAR' && i < this.orden.detallesOrden.length) {
      return this.orden.detallesOrden[i].precioIndividual;
    } else {
      return servicio.precio;
    }
  }
}

// Armar objeto de orden para enviar a servicio de ordenes
/*  let object: any = {id: null,activo: null,tecnico: {id: null},vehiculo: null,descripcion: null,fechaIngreso: null,detallesOrden: null};
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
*/
