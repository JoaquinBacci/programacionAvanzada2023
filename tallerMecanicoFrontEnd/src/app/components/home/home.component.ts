import { Component, ViewChild  } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  @ViewChild(MatSidenav) sidenav!: MatSidenav;
  constructor(
    private router: Router,
  ) {}
 
  closeSidenav() {
    this.sidenav.close();
  }
  openSidenav() {
    this.sidenav.open();
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
  
}
