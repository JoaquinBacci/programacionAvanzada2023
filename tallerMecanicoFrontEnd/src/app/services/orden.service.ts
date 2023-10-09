import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/enviroment';
import { Orden } from '../model/orden';

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
    return this.http.get(`${this.API_SERVER}/orden/`)
  }

  public newOrden(orden: Orden): Observable<any>{
    return this.http.post(`${this.API_SERVER}/orden/`, orden, { headers: this.headers })
  }

  updateOrden( orden: Orden): Observable<any> {
    return this.http.put(`${this.API_SERVER}/orden/`, orden,  { headers: this.headers });
  }

  consultarOrden(nombre: string): Observable<any>{
    return this.http.get(`${this.API_SERVER}/orden/${nombre}`)
  }

  deleteorden(id: number): Observable<any>{
    return this.http.delete(`${this.API_SERVER}/orden/delete/${id}`)
  }
}
