import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Cliente } from '../model/cliente';
import { ClienteService } from '../services/cliente.service';
import { VehiculoService } from '../services/vehiculo.service';
import { MatDialog } from '@angular/material/dialog';
import { VehiculoXclienteComponent } from '../dialogs/vehiculoXcliente/vehiculoXcliente.component';

@Component({
  selector: 'app-cliente-adm',
  templateUrl: './cliente-adm.component.html',
  styleUrls: ['./cliente-adm.component.css']
})
export class ClienteAdmComponent implements OnInit {
  modoEdicion: boolean
  idUpdate: number;
  form: FormGroup
  clienteConsultar: Cliente;
  columnas: string[] = [ 'Nombre', 'Apellido', 'DNI', 'Telefono', 'CorreoElectronico', 'Acciones'];
  dataSource: any;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    public dialog: MatDialog
  ){
    this.form = this.fb.group({
      dni: ['', [Validators.required,Validators.minLength(8), Validators.maxLength(8)]],
      num_tel: ['', [Validators.required, Validators.maxLength(12)]],
      nombre: ['', [Validators.required, Validators.maxLength(80)]],
      apellido: ['', [Validators.required, Validators.maxLength(50)]],
      direccion: [''],
      email: ['',[Validators.email]],
      activo: [true,[]],
    })
  }

  ngOnInit(): void {

    this.clienteConsultar = new Cliente();
    this.clienteConsultar.nombre = '';
    this.clienteConsultar.apellido= '';
    this.clienteConsultar.direccion = '';
    this.clienteConsultar.email = '';
    this.clienteConsultar.activo = true;
    this.clienteConsultar.dni = null;
    this.clienteConsultar.num_tel = '';

    this.onConsultar();


  }

  onGuardar(){
    let clienteRq = new Cliente();
    clienteRq.nombre = this.form.get('nombre').value;
    clienteRq.apellido = this.form.get('apellido').value;
    clienteRq.direccion = this.form.get('direccion').value;
    clienteRq.email = this.form.get('email').value;
    clienteRq.num_tel = this.form.get('num_tel').value;
    clienteRq.dni = this.form.get('dni').value;
    clienteRq.activo = this.form.get('activo').value;


    if(!this.modoEdicion){
      this.clienteService.save(clienteRq).subscribe({
        next:(data)=>{
          if(data.id){
            console.log('dataOK: ', data);
          } else {
            console.log('dataNULL: ', data);
          }
        },
        complete:()=>{
          this.form.reset();
          this.onConsultar();
        }, error: (error) =>{
            console.log('ERROR: ', error);
        }
      });
    } else {
      clienteRq.id = this.idUpdate;

      this.clienteService.update(clienteRq).subscribe({
        next:(data)=>{
          if(data.id){
            console.log('dataOK: ', data);
          } else {
            console.log('dataNULL: ', data);
          }
        },
        complete:()=>{
          this.form.reset();
          this.modoEdicion = false;
          this.onConsultar();
        }, error: (error) =>{
          console.log('ERROR: ', error);
        }
      })
    }
    
  }

  onConsultar(){
    this.clienteService.onConsultar(this.clienteConsultar).subscribe({
      next:(data)=>{
        if(data){
          console.log('dataOK: ', data);
          this.dataSource = data;
        } else {
          console.log('dataNULL: ', data);
        }
      },
      complete:()=>{
        //this.onConsultar();
      }, error: (error) =>{
          console.log('ERROR: ', error);
      }
    });
  }

  onEliminar(id: number){
    this.clienteService.deleteById(id).subscribe({
      next:(data)=>{
        if(data){
          console.log('dataOK: ', data);
        } else {
          console.log('dataNULL: ', data);
        }
      },
      complete:()=>{
        this.onConsultar();
      }, error: (error) =>{
          console.log('ERROR: ', error);
      }
    });
  }

  onEditar(cliente: Cliente){
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

  onCancelar(){
    this.form.reset();
    this.modoEdicion = false;
    this.clienteConsultar = new Cliente();
    this.idUpdate = undefined;
  }

  onVerVehiculos(id: number){
      let dialogRef = this.dialog.open(VehiculoXclienteComponent, {
        data:  id
      });
  }
}
