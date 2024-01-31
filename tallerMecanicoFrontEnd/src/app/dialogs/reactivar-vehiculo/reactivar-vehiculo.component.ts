import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Vehiculo } from 'src/app/model/vehiculo';
import { VehiculoService } from 'src/app/services/vehiculo.service';

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
      },
      complete: () => {},
      error: (error) => {},
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
