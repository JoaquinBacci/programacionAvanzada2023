import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/enviroment';
import { Modelo } from '../model/modelo';

@Injectable({
  providedIn: 'root'
})
export class ModeloService {
  private API_SERVER = environment.apiUrl;

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private http: HttpClient
  ) { }
  
  public getAllModelo(): Observable<any>{
    return this.http.get(`${this.API_SERVER}/modelo/`)
  }

  public newModelo(modelo: Modelo): Observable<any>{
    console.log(`${this.API_SERVER}`+"/modelo/save", modelo, { headers: this.headers })
    return this.http.post(`${this.API_SERVER}`+"/modelo/save/", modelo, { headers: this.headers })
  }

  public updateModelo( modelo: Modelo): Observable<any> {
    return this.http.put(`${this.API_SERVER}/modelo/update/`, modelo,  { headers: this.headers });
  }

  public consultarModelo(modelo: Modelo): Observable<any>{
    return this.http.post(`${this.API_SERVER}/modelo/buscar/`, modelo, { headers: this.headers })
  }

  public deleteModelo(id: number): Observable<any>{
    console.log("URL DELETE: " , `${this.API_SERVER}/modelo/delete/${id}`)
    return this.http.delete(`${this.API_SERVER}/modelo/delete/${id}`)
  }

}
