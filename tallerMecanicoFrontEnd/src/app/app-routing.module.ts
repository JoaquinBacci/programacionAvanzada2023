import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClienteAdmComponent } from './cliente-adm/cliente-adm.component';
import { TecnicoAdmComponent } from './tecnico-adm/tecnico-adm.component';
import { MarcaAdmComponent } from './marca-adm/marca-adm.component';
import { ModeloAdmComponent } from './modelo-adm/modelo-adm.component';
import { VehiculoAdmComponent } from './vehiculo-adm/vehiculo-adm.component';
import { ServicioAdmComponent } from './servicio-adm/servicio-adm.component';
import { HomeComponent } from './components/home/home.component';
import { OrdenAdmComponent } from './orden-adm/orden-adm.component';
import { OrdenEditComponent } from './orden-edit/orden-edit.component';
import { FacturaComponent } from './factura/factura.component';
import { ReporteComponent } from './reporte/reporte.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'cliente', component: ClienteAdmComponent },
  { path: 'tecnico', component: TecnicoAdmComponent },
  { path: 'marca', component: MarcaAdmComponent },
  { path: 'modelo', component: ModeloAdmComponent },
  { path: 'vehiculo', component: VehiculoAdmComponent },
  { path: 'servicio', component: ServicioAdmComponent},
  { path: 'orden', component: OrdenAdmComponent},
  { path: 'orden/:id', component: OrdenEditComponent},
  { path: 'factura', component: FacturaComponent},
  { path: 'reporte', component: ReporteComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
