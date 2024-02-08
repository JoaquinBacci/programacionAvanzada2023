import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {
  Location,
  LocationStrategy,
  PathLocationStrategy,
} from '@angular/common';
import { DatePipe } from '@angular/common';

import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { MarcaAdmComponent } from './marca-adm/marca-adm.component';
import { ModeloAdmComponent } from './modelo-adm/modelo-adm.component';
import { VehiculoAdmComponent } from './vehiculo-adm/vehiculo-adm.component';
import { ClienteAdmComponent } from './cliente-adm/cliente-adm.component';
import { TecnicoAdmComponent } from './tecnico-adm/tecnico-adm.component';
import { HomeComponent } from './components/home/home.component';
import { VehiculoXclienteComponent } from './dialogs/vehiculoXcliente/vehiculoXcliente.component';
import { ConfirmComponent } from './dialogs/confirm/confirm.component';
import { ServicioAdmComponent } from './servicio-adm/servicio-adm.component';
import { OrdenAdmComponent } from './orden-adm/orden-adm.component';
import { OrdenEditComponent } from './orden-edit/orden-edit.component';
import { FacturaComponent } from './factura/factura.component';
import { OrdenXclienteComponent } from './orden-xcliente/orden-xcliente.component';
import { ReporteComponent } from './reporte/reporte.component';
import { ReactivarClienteComponent } from './dialogs/reactivar-cliente/reactivar-cliente.component';
import { ReactivarVehiculoComponent } from './dialogs/reactivar-vehiculo/reactivar-vehiculo.component';
import { ReactivarMarcaComponent } from './dialogs/reactivar-marca/reactivar-marca.component';
import { ReactivarModeloComponent } from './dialogs/reactivar-modelo/reactivar-modelo.component';
import { ReactivarServicioComponent } from './dialogs/reactivar-servicio/reactivar-servicio.component';
import { ReativarTecnicoComponent } from './dialogs/reativar-tecnico/reativar-tecnico.component';
import {MatPaginatorModule} from '@angular/material/paginator';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    MarcaAdmComponent,
    ModeloAdmComponent,
    VehiculoAdmComponent,
    ClienteAdmComponent,
    TecnicoAdmComponent,
    HomeComponent,
    VehiculoXclienteComponent,
    ConfirmComponent,
    ServicioAdmComponent,
    OrdenAdmComponent,
    OrdenEditComponent,
    FacturaComponent,
    OrdenXclienteComponent,
    ReporteComponent,
    ReactivarClienteComponent,
    ReactivarVehiculoComponent,
    ReactivarMarcaComponent,
    ReactivarModeloComponent,
    ReactivarServicioComponent,
    ReativarTecnicoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    HttpClientModule,
    MatToolbarModule,
    MatCardModule,
    MatTableModule,
    MatIconModule,
    MatTooltipModule,
    MatSidenavModule,
    MatListModule,
    MatSelectModule,
    MatOptionModule,
    MatDialogModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatPaginatorModule
  ],
  providers: [
    DatePipe,
    Location,
    { provide: LocationStrategy, useClass: PathLocationStrategy },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
