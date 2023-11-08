import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { VehiculoXclienteComponent } from '../vehiculoXcliente/vehiculoXcliente.component';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css'],
})
export class ConfirmComponent {
  constructor(
    private router: Router,
    public dialogRef: MatDialogRef<VehiculoXclienteComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: { tipo: string; mensaje: string; textoAceptar: string }
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  onAceptClick() {
    this.dialogRef.close(true);
  }
}
