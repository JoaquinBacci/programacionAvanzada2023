import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Cliente } from '../model/cliente';
import { ClienteService } from '../services/cliente.service';
import { VehiculoService } from '../services/vehiculo.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { VehiculoXclienteComponent } from '../dialogs/vehiculoXcliente/vehiculoXcliente.component';
import { ConfirmComponent } from '../dialogs/confirm/confirm.component';
import { OrdenXclienteComponent } from '../orden-xcliente/orden-xcliente.component';
import Toastify from 'toastify-js';

@Component({
  selector: 'app-cliente-adm',
  templateUrl: './cliente-adm.component.html',
  styleUrls: ['./cliente-adm.component.css'],
})
export class ClienteAdmComponent implements OnInit {
  modoEdicion: boolean;
  idUpdate: number;
  form: FormGroup;
  clienteConsultar: Cliente;
  columnas: string[] = [
    'Nombre',
    'Apellido',
    'DNI',
    'Telefono',
    'CorreoElectronico',
    'licenciaConducir',
    'Acciones',
  ];
  dataSource: any;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    public dialog: MatDialog
  ) {
    this.form = this.fb.group({
      dni: [
        '',
        [Validators.required, Validators.minLength(8), Validators.maxLength(8)],
      ],
      num_tel: ['', [Validators.required, Validators.maxLength(12)]],
      nombre: ['', [Validators.required, Validators.maxLength(80)]],
      apellido: ['', [Validators.required, Validators.maxLength(50)]],
      direccion: [''],
      email: ['', [Validators.email]],
      licencia: [''],
      activo: [true, []],
    });
  }

  ngOnInit(): void {
    this.clienteConsultar = new Cliente();
    this.clienteConsultar.nombre = '';
    this.clienteConsultar.apellido = '';
    this.clienteConsultar.direccion = '';
    this.clienteConsultar.email = '';
    this.clienteConsultar.activo = true;
    this.clienteConsultar.dni = null;
    this.clienteConsultar.num_tel = '';
    this.clienteConsultar.licenciaConducir = '';

    this.onConsultar();
  }

  onGuardar() {
    let clienteRq = new Cliente();
    clienteRq.nombre = this.form.get('nombre').value;
    clienteRq.apellido = this.form.get('apellido').value;
    clienteRq.direccion = this.form.get('direccion').value;
    clienteRq.email = this.form.get('email').value;
    clienteRq.num_tel = this.form.get('num_tel').value;
    clienteRq.dni = this.form.get('dni').value;
    clienteRq.activo = this.form.get('activo').value;
    clienteRq.licenciaConducir = this.form.get('licencia').value;

    if (!this.modoEdicion) {
      this.clienteService.save(clienteRq).subscribe({
        next: (data) => {
          if (data.id) {
            console.log('dataOK: ', data);
            Toastify({
              text: 'Cliente agregado',
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
          console.log('ERROR: ', error);
        },
      });
    } else {
      clienteRq.id = this.idUpdate;

      this.clienteService.update(clienteRq).subscribe({
        next: (data) => {
          if (data.id) {
            console.log('dataOK: ', data);
            this.onDialogConfirm(
              'normal',
              'El cliente se ha editado correctamente'
            );
          } else {
            console.log('dataNULL: ', data);
          }
        },
        complete: () => {
          this.form.reset();
          this.modoEdicion = false;
          this.onConsultar();
        },
        error: (error) => {
          console.log('ERROR: ', error);
          this.onDialogConfirm(
            'error',
            'Se ha producido un error al editar el cliente'
          );
        },
      });
    }
  }

  onConsultar() {
    this.clienteService.onConsultar(this.clienteConsultar).subscribe({
      next: (data) => {
        if (data) {
          console.log('dataOK: ', data);
          this.dataSource = data;
        } else {
          console.log('dataNULL: ', data);
        }
      },
      complete: () => {
        //this.onConsultar();
      },
      error: (error) => {
        console.log('ERROR: ', error);
      },
    });
  }

  onEliminar(id: number) {
    this.clienteService.deleteById(id).subscribe({
      next: (data) => {
        if (data) {
          console.log('dataOK: ', data);
        } else {
          console.log('dataNULL: ', data);
          this.onDialogConfirm('normal', 'Se elimino el cliente correctamente');
        }
      },
      complete: () => {
        this.onConsultar();
      },
      error: (error) => {
        console.log('ERROR: ', error);
        this.onDialogConfirm('error', 'No se pudo eliminar el cliente');
      },
    });
  }

  onEditar(cliente: Cliente) {
    this.modoEdicion = true;
    this.idUpdate = cliente.id;

    this.form.get('nombre').setValue(cliente.nombre);
    this.form.get('apellido').setValue(cliente.apellido);
    this.form.get('dni').setValue(cliente.dni);
    this.form.get('num_tel').setValue(cliente.num_tel);
    this.form.get('email').setValue(cliente.email);
    this.form.get('direccion').setValue(cliente.direccion);
    this.form.get('activo').setValue(cliente.activo);
  }

  onCancelar() {
    this.form.reset();
    this.modoEdicion = false;
    this.clienteConsultar = new Cliente();
    this.idUpdate = undefined;
  }

  onVerVehiculos(id: number) {
    let dialogRef = this.dialog.open(VehiculoXclienteComponent, {
      data: id,
    });
  }

  onVerOrdenes(id: number) {
    let dialogRef = this.dialog.open(OrdenXclienteComponent, {
      data: id,
    });
  }

  onDialogConfirm(tipo: string, mensaje: string, textoAceptar?: string) {
    let dialogRef = this.dialog.open(ConfirmComponent, {
      data: { tipo: tipo, mensaje: mensaje, textoAceptar: textoAceptar },
    });
  }
}
