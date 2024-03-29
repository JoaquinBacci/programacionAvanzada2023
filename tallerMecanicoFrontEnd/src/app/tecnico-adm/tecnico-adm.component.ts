import { Component, OnInit, ViewChild } from '@angular/core';
import { TecnicoService } from '../services/tecnico.service';
import { Tecnico } from '../model/tecnico';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../dialogs/confirm/confirm.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Toastify from 'toastify-js';
import { ReativarTecnicoComponent } from '../dialogs/reativar-tecnico/reativar-tecnico.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-tecnico-adm',
  templateUrl: './tecnico-adm.component.html',
  styleUrls: ['./tecnico-adm.component.css'],
})
export class TecnicoAdmComponent implements OnInit {
  idTecnitoUpdate: number = undefined;
  tecnicoConsultar: Tecnico;
  modoEdicion: boolean = false;
  columnasTecnicos: string[] = [
    'Nombre',
    'Apellido',
    'DNI',
    'Telefono',
    'Email',
    'Direccion',
    'Legajo',
    'Acciones',
  ];
  dataSourceTecnico: MatTableDataSource<Tecnico> = new MatTableDataSource<Tecnico>();

  form: FormGroup;

  constructor(
    private tecnicoService: TecnicoService,
    private fb: FormBuilder,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      dni: [
        '',
        [Validators.required, Validators.minLength(8), Validators.maxLength(8)],
      ],
      num_tel: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(13),
        ],
      ],
      legajo: [
        '',
        [Validators.required, Validators.minLength(5), Validators.maxLength(5)],
      ],
      nombre: ['', [Validators.required, Validators.maxLength(50)]],
      apellido: ['', [Validators.required, Validators.maxLength(50)]],
      direccion: ['', [Validators.maxLength(50)]],
      email: ['', [Validators.maxLength(80), Validators.email]],
    });
  }

  ngOnInit(): void {
    console.log('On init');
    this.tecnicoConsultar = new Tecnico();
    this.tecnicoConsultar.nombre = '';
    this.tecnicoConsultar.apellido = '';
    this.tecnicoConsultar.num_tel = '';
    this.tecnicoConsultar.email = '';
    this.tecnicoConsultar.direccion = '';
    this.tecnicoConsultar.dni = null;
    this.tecnicoConsultar.legajo = null;
    //this.onConsultar();

    this.loadEntidades(0, 5);
  }

  // onConsultar() {
  //   this.tecnicoConsultar.activo = true;
  //   this.tecnicoService.onConsultar(this.tecnicoConsultar).subscribe({
  //     next: (data) => {
  //       console.log('Data: ', data);
  //       this.dataSource = data;
  //     },
  //     complete: () => {},
  //     error: (error) => {},
  //   });
  // }

  onDialogDesactivados(){
    let dialogRef = this.dialog.open(ReativarTecnicoComponent, {});

    dialogRef.afterClosed().subscribe(result => this.loadEntidades(this.currentPage, 5));
  }

  onGuardar() {
    console.log('On Guardar');
    let tecnicoRq: Tecnico = new Tecnico();
    tecnicoRq.activo = true;
    tecnicoRq.apellido = this.form.get('apellido').value;
    tecnicoRq.direccion = this.form.get('direccion').value;
    tecnicoRq.num_tel = this.form.get('num_tel').value;
    tecnicoRq.nombre = this.form.get('nombre').value;
    tecnicoRq.dni = this.form.get('dni').value;
    tecnicoRq.legajo = this.form.get('legajo').value;
    tecnicoRq.email = this.form.get('email').value;

    if (!this.modoEdicion) {
      //guardar
      this.tecnicoService.onGuardar(tecnicoRq).subscribe({
        next: (data) => {
          if (data.id != null) {
            console.log('dataOK: ', data);
            Toastify({
              text: 'Tecnico agregada',
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
          this.loadEntidades(this.currentPage, 5);
        },
        error: (error) => {
          console.log('error: ', error);
        },
      });
    } else {
      //editar
      console.log('Editar: ');
      tecnicoRq.id = this.idTecnitoUpdate;
      tecnicoRq.activo = true;
      /* tecnicoRq.apellido = tecnico.apellido   ;   
      tecnicoRq.direccion = tecnico.direccion;
      tecnicoRq.num_tel = tecnico.num_tel ;
      tecnicoRq.nombre = tecnico.nombre;
      tecnicoRq.dni = tecnico.dni ;
      tecnicoRq.legajo = tecnico.legajo; */

      this.tecnicoService.onUpdate(tecnicoRq).subscribe({
        next: (data) => {
          if (data.id != null) {
            console.log('dataOK: ', data);
          } else {
            console.log('dataNULL: ', data);
          }
        },
        complete: () => {
          this.loadEntidades(this.currentPage, 5);
          this.form.reset();
        },
        error: (error) => {
          console.log('error: ', error);
        },
      });
    }
  }

  onEditar(tecnico: Tecnico) {
    this.modoEdicion = true;
    this.idTecnitoUpdate = tecnico.id;
    this.form.get('apellido').setValue(tecnico.apellido);
    this.form.get('direccion').setValue(tecnico.direccion);
    this.form.get('num_tel').setValue(tecnico.num_tel);
    this.form.get('nombre').setValue(tecnico.nombre);
    this.form.get('dni').setValue(tecnico.dni);
    this.form.get('legajo').setValue(tecnico.legajo);
    this.form.get('email').setValue(tecnico.email);
  }
  s;

  onEliminar(id: number) {
    this.tecnicoService.onDeleteById(id).subscribe({
      next: (data) => {
        if (data) {
          console.log('dataOK: ', data);
        } else {
          console.log('dataNULL: ', data);
          this.onDialogConfirm('normal', 'Se elimino el tecnico correctamente');
        }
      },
      complete: () => {
        this.loadEntidades(this.currentPage, 5);
      },
      error: (error) => {
        console.log('error: ', error);
        this.onDialogConfirm('error', 'No se pudo eliminar el tecnico');
      },
    });
  }
  onCancelar() {
    this.form.reset();
    this.modoEdicion = false;
    this.idTecnitoUpdate = undefined;
  }

  onValidarCampo(campo: string) {}

  onDialogConfirm(tipo: string, mensaje: string, textoAceptar?: string) {
    let dialogRef = this.dialog.open(ConfirmComponent, {
      data: { tipo: tipo, mensaje: mensaje, textoAceptar: textoAceptar },
    });
  }

  totalPages: number = 0;
  currentPage: number = 0;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  
  loadEntidades(page: number, size: number): void {
    this.tecnicoConsultar.activo = true;
    this.tecnicoService.listarTecnicos(this.tecnicoConsultar, page, size)
      .subscribe(response => {
        this.dataSourceTecnico.data = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = page;
        this.paginator.pageIndex = page;
   
        this.paginator.length = response.totalElements;
      });
  }

  onPageChange(event): void {
    const page = event.pageIndex;
    const size = event.pageSize;
    this.loadEntidades(page, size);
  }
}
