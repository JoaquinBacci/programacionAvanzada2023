import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ClienteFiltrarRq } from 'src/app/model/ClienteFiltrarRq';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-reactivar-cliente',
  templateUrl: './reactivar-cliente.component.html',
  styleUrls: ['./reactivar-cliente.component.css']
})
export class ReactivarClienteComponent implements OnInit {
  
  columnas: string[] = [
    'Nombre',
    'Apellido',
    'DNI',
    'Telefono',
    'CorreoElectronico',
    'Direccion',
    'licenciaConducir',
    'Acciones',
  ];

  clienteConsultar: ClienteFiltrarRq
  dataSource

  constructor(
    public dialogRef: MatDialogRef<ReactivarClienteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private clienteService: ClienteService
    ) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
      this.clienteConsultar = new ClienteFiltrarRq();
      this.clienteConsultar.activo = false;
      this.onFiltrar();
  }

  onFiltrar(){
    this.clienteService.onConsultar(this.clienteConsultar).subscribe({
      next:(value)=> {
          console.log('value: ', value);
          this.dataSource = value;
      }, error:(err)=> {
          console.log('Error: ', err);
      },
    })
  }

  onReactivar(id: number){
    console.log('id: ', id);
    this.clienteService.reactivar(id).subscribe({
      next:(value)=> {
          console.log('activado');
          this.onFiltrar();
      
      }, error:(err)=> {
          console.log('error');
      },
    });
  }

}
