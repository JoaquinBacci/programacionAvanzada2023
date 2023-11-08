import { Component, Inject, OnInit } from '@angular/core';
import { VehiculoService } from '../../services/vehiculo.service';
import { Vehiculo } from 'src/app/model/vehiculo';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-vehiculoXcliente',
  templateUrl: './vehiculoXcliente.component.html',
  styleUrls: ['./vehiculoXcliente.component.css']
})
export class VehiculoXclienteComponent implements OnInit {

  vehiculos: Vehiculo[];
  columnas: string[] = ['NumeroDeFila','Patente','Marca', 'Modelo','Kilometros'] 
  filaActual: number;
  constructor(
    private service: VehiculoService,
    private router: Router,
    public dialogRef: MatDialogRef<VehiculoXclienteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number
  ){}


  ngOnInit(): void {
    this.filaActual = 0;
    this.service.getByCliente(this.data).subscribe((r)=> this.vehiculos = r, (error)=> console.log('error vXc: ', error));
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onNuevoVehiculo(){
    this.router.navigate(['/vehiculo']);
    this.dialogRef.close();
  }
}
