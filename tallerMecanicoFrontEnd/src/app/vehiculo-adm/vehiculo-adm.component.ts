import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Vehiculo } from '../model/vehiculo';
import { Marca } from '../model/marca';
import { Modelo } from '../model/modelo';
import { Cliente } from '../model/cliente';
import { VehiculoService } from '../services/vehiculo.service';
import { MarcaService } from '../services/marca.service';
import { ModeloService } from '../services/modelo.service';
import { ClienteService } from '../services/cliente.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../dialogs/confirm/confirm.component';
import Toastify from 'toastify-js';
import { ReactivarVehiculoComponent } from '../dialogs/reactivar-vehiculo/reactivar-vehiculo.component';

@Component({
  selector: 'app-vehiculo-adm',
  templateUrl: './vehiculo-adm.component.html',
  styleUrls: ['./vehiculo-adm.component.css'],
})
export class VehiculoAdmComponent implements OnInit, OnChanges {
  idVehiculoUpdate: number = undefined;
  vehiculoConsultar: Vehiculo;
  modoEdicion: boolean = false;
  columnas: string[] = [
    'Patente',
    'Marca',
    'Modelo',
    'Kilometros',
    'Cliente',
    'Acciones',
  ];
  dataSource;
  idMarcaSelect: number = undefined;
  marcas: Marca[] = [];
  modelos: Modelo[] = [];
  modelosConsult: Modelo[] = [];
  clientes: Cliente[];

  form: FormGroup;

  constructor(
    private vehiculoService: VehiculoService,
    private marcaService: MarcaService,
    private modeloService: ModeloService,
    private clienteService: ClienteService,
    private fb: FormBuilder,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      patente: [
        '',
        [
          Validators.required,
          Validators.pattern(
            /^(([a-zA-Z]{3})(\d{3}))|(([a-zA-Z]{2})([\d]{3})([a-zA-Z]{2}))$/
          ),
        ],
      ],
      marca: ['', Validators.required], //id
      modelo: ['', Validators.required],
      kilometraje: ['', Validators.required],
      cliente: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    console.log('On init');
    this.vehiculoConsultar = new Vehiculo();
    this.vehiculoConsultar.patente = '';
    this.vehiculoConsultar.kilometraje = null;
    this.vehiculoConsultar.marca = new Marca();
    this.vehiculoConsultar.modelo = new Modelo();
    this.vehiculoConsultar.cliente = new Cliente();

    this.marcaService.getAllMarcas().subscribe((data) => (this.marcas = data));
    this.clienteService.getAll().subscribe((data) => (this.clientes = data));
    this.modeloService
      .getAllModelo()
      .subscribe((data) => (this.modelosConsult = data));

    this.onConsultar();
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('Form.valid: ', this.form.valid);
  }

  onConsultar() {
    this.vehiculoConsultar.activo=true;
    this.vehiculoService.onConsultar(this.vehiculoConsultar).subscribe({
      next: (data) => {
        console.log('Data: ', data);
        this.dataSource = data;
      },
      complete: () => {},
      error: (error) => {},
    });
  }

  onDialogDesactivados(){
    let dialogRef = this.dialog.open(ReactivarVehiculoComponent, {});

    dialogRef.afterClosed().subscribe(result => this.onConsultar());
  }

  onGuardar() {
    console.log('On Guardar');
    let vehiculoRq: Vehiculo = new Vehiculo();
    vehiculoRq.activo = true;
    vehiculoRq.patente = this.form.get('patente').value;
    vehiculoRq.kilometraje = this.form.get('kilometraje').value;
    vehiculoRq.marca = this.marcas.find(
      (m) => (m.id = this.form.get('marca').value)
    );
    vehiculoRq.modelo = this.modelos.find(
      (m) => (m.id = this.form.get('modelo').value)
    );
    vehiculoRq.cliente = this.clientes.find(
      (c) => (c.id = this.form.get('cliente').value)
    );

    console.log('VehiculoRq: ', vehiculoRq);

    if (!this.modoEdicion) {
      //guardar
      this.vehiculoService.onSave(vehiculoRq).subscribe({
        next: (data) => {
          if (data.id != null) {
            console.log('dataOK: ', data);
            Toastify({
              text: 'Vehiculo agregado',
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
            console.log('dataNULL: ', data);
          }
        },
        complete: () => {
          this.form.reset();
          this.onConsultar();
        },
        error: (error) => {
          console.log('error: ', error);
        },
      });
    } else {
      //editar
      console.log('Editar: ');
      vehiculoRq.id = this.idVehiculoUpdate;

      this.vehiculoService.onUpdate(vehiculoRq).subscribe({
        next: (data) => {
          if (data.id != null) {
            console.log('dataOK: ', data);
          } else {
            console.log('dataNULL: ', data);
          }
        },
        complete: () => {
          this.onConsultar();
          this.form.reset();
        },
        error: (error) => {
          console.log('error: ', error);
        },
      });
    }
  }

  onEditar(vehiculo: Vehiculo) {
    this.modoEdicion = true;
    this.idMarcaSelect = undefined;
    this.idVehiculoUpdate = vehiculo.id;
    this.form.get('patente').setValue(vehiculo.patente);
    this.form.get('kilometraje').setValue(vehiculo.kilometraje);
    this.form.get('marca').setValue(vehiculo.marca.id);
    this.idMarcaSelect = vehiculo.marca.id;
    this.form.get('cliente').setValue(vehiculo.cliente.id);
    this.form.get('modelo').setValue(vehiculo.modelo.id);
  }

  onEliminar(id: number) {
    this.vehiculoService.onDelete(id).subscribe({
      next: (data) => {
        if (data) {
          console.log('dataOK: ', data);
        } else {
          console.log('dataNULL: ', data);
          this.onDialogConfirm(
            'normal',
            'Se elimino el vehiculo correctamente'
          );
        }
      },
      complete: () => {
        this.onConsultar();
      },
      error: (error) => {
        console.log('error: ', error);
        this.onDialogConfirm('error', 'No se pudo eliminar el vehiculo');
      },
    });
  }
  onCancelar() {
    this.form.reset();
    this.modoEdicion = false;
    this.idVehiculoUpdate = undefined;
  }

  onValidarCampo(campo: string) {}

  onMarcaSelectionChange(selectedValue: number | null): void {
    this.idMarcaSelect = selectedValue;
    if (this.idMarcaSelect != null) {
      this.modeloService
        .getByIdMarca(this.idMarcaSelect)
        .subscribe((data) => (this.modelos = data));
    }
  }

  onDialogConfirm(tipo: string, mensaje: string, textoAceptar?: string) {
    let dialogRef = this.dialog.open(ConfirmComponent, {
      data: { tipo: tipo, mensaje: mensaje, textoAceptar: textoAceptar },
    });
  }
}
