import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'tallerMecanicoFront';

  @ViewChild(MatSidenav) sidenav!: MatSidenav;
  constructor(
    private router: Router,
  ) {}

  ngOnInit() {
    console.log("estoy");
    this.router.navigate(['/home']);
  }

  toggleSidenav() {
    this.sidenav.toggle();
  }

  openHome(){
    this.router.navigate(['/home']);
  }
  
  openClientes() {
    this.router.navigate(['/cliente']);
  }

  openModelo() {
    this.router.navigate(['/modelo']);
  }

  openVehiculo() {
    this.router.navigate(['/vehiculo']);
  }
  openTecnico() {
    this.router.navigate(['/tecnico']);
  }
  openMarca() {
    this.router.navigate(['/marca']);
  }

  openServicio() {
    this.router.navigate(['/servicio']);
  }
  
  openReporte() {
    this.router.navigate(['/reporte']);
  }
}
