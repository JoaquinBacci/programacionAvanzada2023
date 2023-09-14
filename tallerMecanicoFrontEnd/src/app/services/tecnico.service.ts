import { Injectable } from '@angular/core';
import { Tecnico } from '../model/tecnico';
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/enviroment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TecnicoService {

  private API_REST = environment.apiUrl
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(
    private http: HttpClient
  ) { }

  /* @PostMapping("filtrar/")
  private ResponseEntity<List<Tecnico>> search(@RequestBody Tecnico tecnicoRq){
      return ResponseEntity.ok(this.tecnicoService.filtrarTecnicos(tecnicoRq));
  } */

  public onConsultar(tecnicoRq: Tecnico): Observable<any>{
    return this.http.post(`${this.API_REST}/tecnico/filtrar/`, tecnicoRq, { headers: this.headers });
  }
  
  /* @PutMapping("update/")
  private ResponseEntity<Tecnico> update(@RequestBody Tecnico tecnicoRq){
      return ResponseEntity.ok(this.tecnicoService.update(tecnicoRq));
  } */

  public onUpdate(tecnicoRq: Tecnico): Observable<any>{
    return this.http.put(`${this.API_REST}/tecnico/update/`, tecnicoRq, { headers: this.headers });
  }
  
  /* @DeleteMapping("delete/{id}")
  private ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
      this.tecnicoService.deleteById(id);
      return ResponseEntity.ok(!(this.tecnicoService.getById(id) != null));
  } */

  public onDeleteById(id: number): Observable<any>{
    return this.http.delete(`${this.API_REST}/tecnico/delete/${id}`);
  }
  
  /* @PostMapping("save/")
  private ResponseEntity<Tecnico> saveTecnico(@RequestBody Tecnico tecnicoRq){
      return ResponseEntity.ok(this.tecnicoService.save(tecnicoRq));
  } */
  public onGuardar(tecnicoRq: Tecnico): Observable<any>{
    return this.http.post(`${this.API_REST}/tecnico/save/`, tecnicoRq, { headers: this.headers });
  }

}
