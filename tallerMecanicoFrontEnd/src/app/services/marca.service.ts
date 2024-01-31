import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Marca } from '../model/marca';
import { environment } from 'src/enviroments/enviroment';


@Injectable({
  providedIn: 'root'
})
export class MarcaService {
  private API_SERVER = environment.apiUrl;

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(
    private http: HttpClient
  ) { }

  public getAllMarcas(): Observable<any>{
    return this.http.get(`${this.API_SERVER}/marca/`)
  }

  public newMarca(marcaRq: Marca): Observable<any>{
    return this.http.post(`${this.API_SERVER}/marca/`, marcaRq, { headers: this.headers })
  }

  updateMarca( marcaActualizada: Marca): Observable<any> {
    return this.http.put(`${this.API_SERVER}/marca/`, marcaActualizada,  { headers: this.headers });
  }

  consultarMarca(marca: Marca): Observable<any>{
    return this.http.post(`${this.API_SERVER}/marca/filtrar/`, marca, { headers: this.headers })
  }

  deleteMarca(id: number): Observable<any>{
    return this.http.delete(`${this.API_SERVER}/marca/delete/${id}`)
  }

  reactivar(id: number): Observable<any>{
    return this.http.get(`${this.API_SERVER}/marca/activar/${id}`)
  }

}
