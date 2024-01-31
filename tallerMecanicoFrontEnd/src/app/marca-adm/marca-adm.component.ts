import { Component, OnInit } from '@angular/core';
import { MarcaService } from '../services/marca.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Marca } from '../model/marca';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../dialogs/confirm/confirm.component';
import Toastify from 'toastify-js';
import { ReactivarMarcaComponent } from '../dialogs/reactivar-marca/reactivar-marca.component';

@Component({
  selector: 'app-marca-adm',
  templateUrl: './marca-adm.component.html',
  styleUrls: ['./marca-adm.component.css'],
})
export class MarcaAdmComponent implements OnInit {
  form: FormGroup;
  columnas: string[] = ['nombre', 'acciones'];
  dataSource;
  nombreMarca: string = '';
  modoEdicion: boolean;
  marcaEdit: Marca;
  constructor(
    private marcaService: MarcaService,
    private fb: FormBuilder,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      nuevoNombre: ['', [Validators.required, Validators.maxLength(50)]],
    });
  }

  ngOnInit(): void {
    this.onGetAllMarcas();
  }

  onDialogDesactivados(){
    let dialogRef = this.dialog.open(ReactivarMarcaComponent, {
    });

    dialogRef.afterClosed().subscribe(result => this.onConsultar());
  }

  onGuardar() {
    if (!this.modoEdicion) {
      //Guargar marca nueva
      if (this.controlNombreNuevo()) {
        console.log('onGuardar');
        let marcaRq = new Marca();
        marcaRq.nombre = this.form.get('nuevoNombre').value;
        marcaRq.activo = true;
        console.log('marcaRq: ', marcaRq);
        this.marcaService.newMarca(marcaRq).subscribe({
          next: (data) => {
            if (data.id != null) {
              console.log('dataOK: ', data);
              Toastify({
                text: 'Marca agregada',
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
            this.onGetAllMarcas();
            this.form.reset();
          },
          error: (error) => {
            console.log('ERROR ', error);
          },
        });
      } else {
        //TODO: Dialog error nombre null
      }
    } else {
      console.log('Editar marca');
      this.marcaEdit.nombre = this.form.get('nuevoNombre').value;
      console.log('Marca: ', this.marcaEdit, this.marcaEdit.id);
      //Editar marca
      this.marcaService.updateMarca(this.marcaEdit).subscribe({
        next: (data) => {
          if (data.id != null) {
            console.log('dataOK: ', data);
          } else {
            console.log('dataNull: ', data);
          }
        },
        complete: () => {
          this.onGetAllMarcas();
          this.form.reset();
          this.modoEdicion = false;
        },
        error: (error) => {
          console.log('errorEdit: ', error);
        },
      });
    }
  }

  onEliminar(id: number) {
    console.log('ID a eliminar: ', id);
    this.marcaService.deleteMarca(id).subscribe({
      next: (data) => {
        this.onDialogConfirm('normal', 'Se elimino la marca correctamente');
      },
      complete: () => {
        this.onGetAllMarcas();
      },
      error: (error) => {
        console.log('ERROR ', error);
        this.onDialogConfirm('error', 'No se pudo eliminar la marca');
      },
    });
  }

  onConsultar() {
    let marca: Marca = new Marca();
    marca.nombre = this.nombreMarca;
    marca.activo = true;
    this.marcaService.consultarMarca(marca).subscribe({
      next: (data) => {
        console.log('marcas: ', data);
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
        this.dataSource = data;
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

  onEditar(marca: Marca) {
    this.modoEdicion = true;
    this.form.get('nuevoNombre').setValue(marca.nombre);
    this.marcaEdit = marca;
    console.log('MarcaEdit: ', this.marcaEdit);
  }

  onCancelar() {
    this.form.reset();
    this.modoEdicion = false;
  }

  onDialogConfirm(tipo: string, mensaje: string, textoAceptar?: string) {
    let dialogRef = this.dialog.open(ConfirmComponent, {
      data: { tipo: tipo, mensaje: mensaje, textoAceptar: textoAceptar },
    });
  }
}
