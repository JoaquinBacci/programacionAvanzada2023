import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-reactivar-servicio',
  templateUrl: './reactivar-servicio.component.html',
  styleUrls: ['./reactivar-servicio.component.css']
})
export class ReactivarServicioComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<ReactivarServicioComponent>,
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
