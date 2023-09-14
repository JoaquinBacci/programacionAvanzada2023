import { Injectable } from '@angular/core';
import { Vehiculo } from '../model/vehiculo';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehiculoService {

  constructor() { }

  onGetAll(){}

  onSave(v:Vehiculo):  Observable<any>{
    return null;
  }

  onConsultar(v: Vehiculo): Observable<any>{
    return null;
  }

  onUpdate(v:Vehiculo): Observable<any>{
    return null;
  }

  onDelete(id: number): Observable<any>{
    return null;
  }
}
