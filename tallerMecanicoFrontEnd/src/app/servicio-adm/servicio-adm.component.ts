import { Component } from '@angular/core';
import { Servicio } from 'src/app/model/servicio';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServicioService } from 'src/app/services/servicio.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../dialogs/confirm/confirm.component';
import Toastify from 'toastify-js';
import { ReactivarServicioComponent } from '../dialogs/reactivar-servicio/reactivar-servicio.component';

@Component({
  selector: 'app-servicio-adm',
  templateUrl: './servicio-adm.component.html',
  styleUrls: ['./servicio-adm.component.css'],
})
export class ServicioAdmComponent {
  form: FormGroup;
  columnas: string[] = ['descripcion', 'nombre', 'precio', 'acciones'];
  dataSource;
  modoEdicion: boolean = false;
  servicioEdit: Servicio;
  servicioConsultar: Servicio;

  constructor(
    private servicioService: ServicioService,
    private fb: FormBuilder,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      descripcion: ['', [Validators.required, Validators.maxLength(50)]],
      nombre: ['', [Validators.required, Validators.maxLength(50)]],
      precio: [
        0.0,
        [Validators.required, this.validarPrecio, Validators.min(1)],
      ],
    });
  }

  validarPrecio(control) {
    const valor = parseFloat(control.value);
    if (!isNaN(valor) && valor >= 0) {
      return null; // Válido
    } else {
      return { precioInvalido: true }; // Inválido
    }
  }

  ngOnInit(): void {
    this.servicioConsultar = new Servicio();
    this.servicioConsultar.id = null;
    this.servicioConsultar.activo = true;
    this.servicioConsultar.descripcion = '';
    this.servicioConsultar.nombre = '';
    this.servicioConsultar.precio = 0.0;

    this.onConsultarServicios();
  }

  onGuardar() {
    console.log('OnGuardar!! ');
    if (this.controlNombreNuevo()) {
      console.log('nombre valido');
      //Guargar servicio nuevo
      if (!this.modoEdicion) {
        console.log('modoEdicion ', this.modoEdicion);
        let servicioRq = new Servicio();
        servicioRq.id = null;
        servicioRq.nombre = this.form.get('nombre').value;
        servicioRq.descripcion = this.form.get('descripcion').value;
        let precio = parseFloat(this.form.get('precio').value).toFixed(2);
        servicioRq.precio = parseFloat(precio);
        servicioRq.activo = true;
        this.servicioService.newServicio(servicioRq).subscribe({
          next: (data) => {
            if (data) {
              if (data.id != null) {
                console.log('dataOK: ', data);
                Toastify({
                  text: 'Servicio agregado',
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
            } else {
              console.log('dataNull: ', data);
            }
          },
          complete: () => {
            this.onConsultarServicios();
            this.form.reset();
          },
          error: (error) => {
            console.log('ERROR ', error);
          },
        });
      } else {
        console.log('Editar servicio');
        this.servicioEdit.activo = true;
        this.servicioEdit.descripcion = this.form.get('descripcion').value;
        this.servicioEdit.nombre = this.form.get('nombre').value;
        this.servicioEdit.precio = this.form.get('precio').value;
        console.log('servicioEdit: ', this.servicioEdit);
        //Editar marca
        this.servicioService.updateServicio(this.servicioEdit).subscribe({
          next: (data) => {
            if (data) {
              if (data.id != null) {
                console.log('dataOK: ', data);
              } else {
                console.log('dataNull: ', data);
              }
            } else {
              console.log('dataNull: ', data);
            }
          },
          complete: () => {
            this.onConsultarServicios();
            this.form.reset();
            this.modoEdicion = false;
          },
          error: (error) => {
            console.log('errorEdit: ', error);
          },
        });
      }
    }
  }

  onEliminar(id: number) {
    console.log('ID a eliminar: ', id);
    this.servicioService.deleteServicio(id).subscribe({
      next: (data) => {
        console.log('data Delete: ', data);
        this.onDialogConfirm('normal', 'Se elimino el servicio correctamente');
      },
      complete: () => {
        this.onConsultarServicios();
      },
      error: (error) => {
        console.log('ERROR ', error);
        this.onDialogConfirm('error', 'No se pudo eliminar el tecnico');
      },
    });
  }

  onDialogDesactivados(){
    let dialogRef = this.dialog.open(ReactivarServicioComponent, {});

    dialogRef.afterClosed().subscribe(result => this.onConsultarServicios());
  }

  onConsultarServicios() {
    this.servicioConsultar.activo = true;
    this.servicioService.filterServicio(this.servicioConsultar).subscribe({
      next: (data) => {
        console.log('servicios: ', data);
        this.dataSource = data;
      },
      complete: () => {},
      error: (error) => {
        console.log('ERROR ', error);
      },
    });
  }

  // onConsultar(){
  //   this.servicioService.getByIdServicio(1).subscribe({
  //     next: (data)=>{
  //       console.log('servicios: ', data);
  //       this.dataSource = data;
  //     }, complete: () =>{

  //     },error: (error)=>{
  //         console.log('ERROR ', error)
  //     }
  //   })
  // }

  controlNombreNuevo(): boolean {
    console.log('Nombre: ', this.form.get('nombre').value);
    let nombre: string = this.form.get('nombre').value;
    return nombre.trim() === '' ? false : true;
  }

  onEditar(servicio: Servicio) {
    console.log('servicio: ', servicio);
    this.servicioEdit = new Servicio();
    this.modoEdicion = true;
    this.servicioEdit.id = servicio.id;
    this.servicioEdit.activo = true;
    this.form.get('descripcion').setValue(servicio.descripcion);
    this.form.get('nombre').setValue(servicio.nombre);
    this.form.get('precio').setValue(servicio.precio);
  }

  onCancelar() {
    this.form.reset();
    this.modoEdicion = false;
    this.servicioConsultar = new Servicio();
  }

  onDialogConfirm(tipo: string, mensaje: string, textoAceptar?: string) {
    let dialogRef = this.dialog.open(ConfirmComponent, {
      data: { tipo: tipo, mensaje: mensaje, textoAceptar: textoAceptar },
    });
  }
}
