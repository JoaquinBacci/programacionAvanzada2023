import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Cliente } from '../model/cliente';
import { ClienteService } from '../services/cliente.service';
import { VehiculoService } from '../services/vehiculo.service';

@Component({
  selector: 'app-cliente-adm',
  templateUrl: './cliente-adm.component.html',
  styleUrls: ['./cliente-adm.component.css']
})
export class ClienteAdmComponent implements OnInit {
  modoEdicion: boolean
  
  form: FormGroup
  clienteConsultar: Cliente;
  columnas: string[] = [ 'Nombre', 'Apellido', 'DNI', 'Telefono', 'CorreoElectronico', 'Acciones'];
  dataSource: any;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private vehiculoService: VehiculoService,
  ){
    this.form = this.fb.group({

      id: [''],
      dni: ['', [Validators.required, Validators.maxLength(8)]],
      num_tel: ['', [Validators.required, Validators.maxLength(12)]],
      nombre: ['', [Validators.required, Validators.maxLength(80)]],
      apellido: ['', [Validators.required, Validators.maxLength(50)]],
      direccion: [''],
      email: ['',[Validators.email]],
      activo: [true,[]],

    })
  }

  ngOnInit(): void {
      
  }

  onGuardar(){

  }

  onConsultar(){

  }

  onEliminar(id: number){}

  onEditar(cliente: Cliente){}

  onCancelar(){}

  onVisualizarFicha(id: number){}
}
