import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-reactivar-marca',
  templateUrl: './reactivar-marca.component.html',
  styleUrls: ['./reactivar-marca.component.css']
})
export class ReactivarMarcaComponent implements OnInit{
  constructor(
    public dialogRef: MatDialogRef<ReactivarMarcaComponent>,
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
