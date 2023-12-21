import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MarcaService } from '../services/marca.service';
import { ModeloService } from '../services/modelo.service';
import { Marca } from '../model/marca';
import { Modelo } from '../model/modelo';
import Toastify from 'toastify-js';

@Component({
  selector: 'app-modelo-adm',
  templateUrl: './modelo-adm.component.html',
  styleUrls: ['./modelo-adm.component.css'],
})
export class ModeloAdmComponent implements OnInit {
  form: FormGroup;
  columnas: string[] = ['nombre', 'marca', 'acciones'];
  dataSource;
  modoEdicion: boolean = false;
  modeloEdit: Modelo;
  modeloConsultar: Modelo;
  marcas: Marca[] = [];
  constructor(
    private marcaService: MarcaService,
    private modeloService: ModeloService,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      nuevoNombre: ['', [Validators.required, Validators.maxLength(50)]],
      marca: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.modeloConsultar = new Modelo();
    this.modeloConsultar.nombre = '';
    let m = new Marca();
    this.modeloConsultar.marca = m;

    this.modeloEdit = new Modelo();
    this.modeloEdit.nombre = '';
    this.modeloEdit.marca = m;

    this.onGetAllMarcas();
    this.onConsultar();
  }

  onGuardar() {
    console.log('OnGuardar!! ');
    if (this.controlNombreNuevo()) {
      console.log('nombre valido');
      //Guargar modelo nuevo
      if (!this.modoEdicion) {
        console.log('modoEdicion ', this.modoEdicion);
        let modeloRq = new Modelo();
        modeloRq.id = null;
        modeloRq.nombre = this.form.get('nuevoNombre').value;
        modeloRq.marca = this.marcas.find(
          (m) => m.id == this.form.get('marca').value
        );
        modeloRq.activo = true;
        console.log('modeloRq: ', modeloRq);
        this.modeloService.newModelo(modeloRq).subscribe({
          next: (data) => {
            if (data.id != null) {
              console.log('dataOK: ', data);
              Toastify({
                text: 'Vehiculo agregada',
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
              console.log('dataNull: ', data);
            }
          },
          complete: () => {
            this.onConsultar();
            this.form.reset();
          },
          error: (error) => {
            console.log('ERROR ', error);
          },
        });
      } else {
        console.log('Editar modelo');
        this.modeloEdit.nombre = this.form.get('nuevoNombre').value;
        this.modeloEdit.marca = this.marcas.find(
          (m) => m.id == this.form.get('marca').value
        );
        this.modeloEdit.activo = true;
        console.log('modeloEdit: ', this.modeloEdit);
        //Editar marca
        this.modeloService.updateModelo(this.modeloEdit).subscribe({
          next: (data) => {
            if (data.id != null) {
              console.log('dataOK: ', data);
            } else {
              console.log('dataNull: ', data);
            }
          },
          complete: () => {
            this.onConsultar();
            this.form.reset();
            this.modoEdicion = false;
          },
          error: (error) => {
            console.log('errorEdit: ', error);
          },
        });
      }
    } else {
      //TODO: Dialog error nombre null
    }
  }

  onEliminar(id: number) {
    console.log('ID a eliminar: ', id);
    this.modeloService.deleteModelo(id).subscribe({
      next: (data) => {
        console.log('data Delete: ', data);
      },
      complete: () => {
        this.onConsultar();
      },
      error: (error) => {
        console.log('ERROR ', error);
      },
    });
  }

  onConsultar() {
    this.modeloService.consultarModelo(this.modeloConsultar).subscribe({
      next: (data) => {
        console.log('modelos: ', data);
        this.dataSource = data;
      },
      complete: () => {},
      error: (error) => {
        console.log('ERROR ', error);
      },
    });
  }

  onGetAllMarcas() {
    this.marcaService.getAllMarcas().subscribe({
      next: (data) => {
        console.log('marcas: ', data);
        this.marcas = data;
      },
      complete: () => {},
      error: (error) => {
        console.log('ERROR ', error);
      },
    });
  }

  controlNombreNuevo(): boolean {
    console.log('Nombre: ', this.form.get('nuevoNombre').value);
    let nombre: string = this.form.get('nuevoNombre').value;
    return nombre.trim() === '' ? false : true;
  }

  onEditar(modelo: Modelo) {
    console.log('modelo: ', modelo);
    this.modoEdicion = true;
    this.modeloEdit.id = modelo.id;
    this.modeloEdit.activo = true;
    this.form.get('nuevoNombre').setValue(modelo.nombre);
    this.form.get('marca').setValue(modelo.marca.id);
  }

  onCancelar() {
    this.form.reset();
    this.modoEdicion = false;
    this.modeloConsultar = new Modelo();
  }
}
