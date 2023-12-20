import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/enviroment';
import { Orden } from '../model/orden';
import { OrdenSaveRq } from '../model/OrdenSaveRq';
import { RqReporteCantServMarca } from '../model/RqReporteCantServMarcaEntreFecha';

@Injectable({
  providedIn: 'root'
})
export class OrdenService {

  private API_SERVER = environment.apiUrl;

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(
    private http: HttpClient
  ) { }

  public getAllOrdenes(): Observable<any>{
    return this.http.get(`${this.API_SERVER}/orden`);
  }

  public getOrdenById(id):Observable<Orden>{
    return this.http.get<Orden>(`${this.API_SERVER}/orden/get/${id}`);
  }

  public newOrden(orden: OrdenSaveRq): Observable<any>{
    return this.http.post(`${this.API_SERVER}/orden/save`, orden, { headers: this.headers })
  }

  updateOrden( orden: OrdenSaveRq): Observable<any> {
    return this.http.put(`${this.API_SERVER}/orden/update`, orden,  { headers: this.headers });
  }

  consultarOrden(nombre: string): Observable<any>{
    return this.http.get(`${this.API_SERVER}/orden/${nombre}`)
  }

  deleteorden(id: number): Observable<any>{
    return this.http.delete(`${this.API_SERVER}/orden/delete/${id}`)
  }

  iniciarOrden(orden: Orden): Observable<Orden>{
    return this.http.post<Orden>(`${this.API_SERVER}/orden/iniciar`, orden, { headers: this.headers })
  }

  cancelarOrden(orden: Orden): Observable<Orden>{
    return this.http.post<Orden>(`${this.API_SERVER}/orden/cancelar`, orden, { headers: this.headers })
  }

  finalizarOrden(orden: Orden): Observable<Orden>{
    return this.http.post<Orden>(`${this.API_SERVER}/orden/finalizar`, orden, { headers: this.headers })
  }
  reporteMarcaServ(rq: RqReporteCantServMarca): Observable<any>{
    return this.http.post<any>(`${this.API_SERVER}/reporte/ordencantserv/`, rq, { headers: this.headers })
  }

}
