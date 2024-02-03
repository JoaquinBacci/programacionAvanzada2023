import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Vehiculo } from 'src/app/model/vehiculo';
import { VehiculoService } from 'src/app/services/vehiculo.service';
import Toastify from 'toastify-js';


@Component({
  selector: 'app-reactivar-vehiculo',
  templateUrl: './reactivar-vehiculo.component.html',
  styleUrls: ['./reactivar-vehiculo.component.css']
})
export class ReactivarVehiculoComponent implements OnInit{
  columnas: string[] = [
    'Patente',
    'Marca',
    'Modelo',
    'Kilometros',
    'Cliente',
    'Acciones',
  ];
  vehiculoConsultar: Vehiculo;
  dataSource
  
  constructor(
    public dialogRef: MatDialogRef<ReactivarVehiculoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private vehiculoService: VehiculoService) { }

  onNoClick(): void {
    this.dialogRef.close();
  }
  ngOnInit(): void {
      this.vehiculoConsultar = new Vehiculo();
      this.onFiltrar();
  }

  onFiltrar(){
    this.vehiculoConsultar.activo = false;
    this.vehiculoService.onConsultar(this.vehiculoConsultar).subscribe({
      next: (data) => {
        console.log('Data: ', data);
        this.dataSource = data;
        Toastify({
          text: 'Vehiculo reactivado',
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
      },
      complete: () => {},
      error: (error) => {
        Toastify({
          text: 'Problema al reactivar el vehiculo',
          duration: 3000,
          destination: 'https://github.com/apvarun/toastify-js',
          newWindow: true,
          close: true,
          gravity: 'bottom', // Cambiado a "bottom" para colocarlo en la parte inferior
          position: 'right', // Cambiado a "right" para colocarlo en la esquina derecha
          stopOnFocus: true,
          style: {
            background: 'red', // Cambiado a negro
            color: 'black'
          },
          onClick: function () {},
        }).showToast();
      },
    });
  }

  onReactivar(id: number){
    console.log('id: ', id);
    this.vehiculoService.activar(id).subscribe({
      next:(value)=> {
          console.log('activado');
          this.onFiltrar();
      
      }, error:(err)=> {
          console.log('error');
      },
    });
  }
}
