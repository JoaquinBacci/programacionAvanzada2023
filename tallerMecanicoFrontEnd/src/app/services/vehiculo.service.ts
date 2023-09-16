import { Injectable } from '@angular/core';
import { Vehiculo } from '../model/vehiculo';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/enviroments/enviroment';

@Injectable({
  providedIn: 'root'
})
export class VehiculoService {

  private API_REST = environment.apiUrl
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(
    private http: HttpClient
  ) { }

  /* 
  @GetMapping
    private ResponseEntity<List<Vehiculo>> getAllVehiculos(){
        return ResponseEntity.ok(this.vehiculoService.findAll());
    }
  */

  public onGetAll(): Observable<any>{
    return this.http.get(`${this.API_REST}/vehiculo/`);
  }


  /*  
    @PostMapping("filtrar/")
    private ResponseEntity<List<Vehiculo>> filrarVehiculos(@RequestBody Vehiculo v){
        return ResponseEntity.ok(this.vehiculoService.filtarVehiculos(v));
    }
  */

    onConsultar(vehiculoRq: Vehiculo): Observable<any>{
      return this.http.post(`${this.API_REST}/vehiculo/filtrar/`, vehiculoRq, {headers: this.headers} );
    }
  
 
    /*  
    @PostMapping("save/")
    private ResponseEntity<Vehiculo> crearVehiculo(@RequestBody Vehiculo v){
        return ResponseEntity.ok(this.vehiculoService.save(v));
    }
  */

    onSave(vehiculoRq :Vehiculo):  Observable<any>{
      return this.http.post(`${this.API_REST}/vehiculo/save/`, vehiculoRq, {headers: this.headers} );
    }

  /*  
    @PutMapping("update/")
    private ResponseEntity<Vehiculo> updateVehiculo(@RequestBody Vehiculo v){
        return ResponseEntity.ok(this.vehiculoService.updateVehiculo(v));
    }
  */

    onUpdate(vehiculoRq :Vehiculo): Observable<any>{
      return this.http.put(`${this.API_REST}/vehiculo/update/`, vehiculoRq, {headers: this.headers} );
    }

  /*  
    @DeleteMapping("delete/{id}")
    private ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
        this.vehiculoService.deleteById(id);
        return ResponseEntity.ok(!(this.vehiculoService.getById(id) != null));
    }
    
  */

  onDelete(id: number): Observable<any>{
    return this.http.delete(`${this.API_REST}/vehiculo/delete/${id}`);
  }
}
