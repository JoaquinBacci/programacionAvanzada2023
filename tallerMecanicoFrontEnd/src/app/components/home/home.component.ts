import { Component, OnInit, ViewChild  } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  @ViewChild(MatSidenav) sidenav!: MatSidenav;
  constructor(
    private router: Router,
  ) {}

  ngOnInit() {
    console.log("estoy");
    this.router.navigate(['/cliente']);
  }
 
  toggleSidenav() {
    this.sidenav.toggle();
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
  
}
