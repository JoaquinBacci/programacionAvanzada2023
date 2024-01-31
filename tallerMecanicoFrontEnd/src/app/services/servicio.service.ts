import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/enviroment';
import { Servicio } from '../model/servicio';

@Injectable({
  providedIn: 'root'
})
export class ServicioService {

  private API_SERVER = environment.apiUrl;

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private http: HttpClient
  ) { }
  
  public getAllServicio(): Observable<any>{
    return this.http.get(`${this.API_SERVER}/servicio`,{ headers: this.headers })
  }

  public newServicio(servicio: Servicio): Observable<any>{
    console.log(`${this.API_SERVER}`+"/servicio/save", servicio, { headers: this.headers })
    return this.http.post(`${this.API_SERVER}`+"/servicio/save", servicio, { headers: this.headers })
  }

  public updateServicio( servicio: Servicio): Observable<any> {
    return this.http.post(`${this.API_SERVER}/servicio/update/`,servicio,{ headers: this.headers });
  }

  public consultarServicio(servicio: Servicio): Observable<any>{
    // TODO: no esta implementado el rest
    return this.http.post(`${this.API_SERVER}/servicio/buscar/`, servicio, { headers: this.headers })
  }

  public deleteServicio(id: number): Observable<any>{
    return this.http.delete(`${this.API_SERVER}/servicio/delete/${id}`)
  }

  public getByIdServicio(id: number): Observable<any>{
    return this.http.get(`${this.API_SERVER}/servicio/${id}`)
  }

  public filterServicio(servicio: Servicio): Observable<any>{
    return this.http.post(`${this.API_SERVER}/servicio/filtrar/`, servicio, { headers: this.headers })
  }

  public reactivar(id: number): Observable<any>{
    return this.http.get(`${this.API_SERVER}/servicio/activar/${id}`)
  }
}
