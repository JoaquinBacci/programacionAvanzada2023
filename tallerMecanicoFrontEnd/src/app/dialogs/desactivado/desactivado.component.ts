import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-desactivado',
  templateUrl: './desactivado.component.html',
  styleUrls: ['./desactivado.component.css']
})
export class DesactivadoComponent {
  
  constructor( 
    public dialogRef: MatDialogRef<DesactivadoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ){}
  //data.onActivar()?? 
}
