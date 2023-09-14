import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClienteAdmComponent } from './cliente-adm/cliente-adm.component';
import { TecnicoAdmComponent } from './tecnico-adm/tecnico-adm.component';
import { MarcaAdmComponent } from './marca-adm/marca-adm.component';
import { ModeloAdmComponent } from './modelo-adm/modelo-adm.component';
import { VehiculoAdmComponent } from './vehiculo-adm/vehiculo-adm.component';

const routes: Routes = [
  { path: 'cliente', component: ClienteAdmComponent },
  { path: 'tecnico', component: TecnicoAdmComponent },
  { path: 'marca', component: MarcaAdmComponent },
  { path: 'modelo', component: ModeloAdmComponent },
  { path: 'vehiculo', component: VehiculoAdmComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
