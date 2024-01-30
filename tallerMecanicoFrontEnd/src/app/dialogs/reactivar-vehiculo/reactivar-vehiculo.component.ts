import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-reactivar-vehiculo',
  templateUrl: './reactivar-vehiculo.component.html',
  styleUrls: ['./reactivar-vehiculo.component.css']
})
export class ReactivarVehiculoComponent implements OnInit{
  constructor(
    public dialogRef: MatDialogRef<ReactivarVehiculoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onNoClick(): void {
    this.dialogRef.close();
  }
  ngOnInit(): void {
      
  }

  onFiltrar(){

  }

  onReactivar(){
    
  }
}
