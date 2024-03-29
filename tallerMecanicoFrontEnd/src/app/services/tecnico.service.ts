import { Injectable } from '@angular/core';
import { Tecnico } from '../model/tecnico';
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/enviroment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { PaginatedResponse } from '../model/PaginatedResponse';

@Injectable({
  providedIn: 'root',
})
export class TecnicoService {
  private API_REST = environment.apiUrl;
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient) {}

  /* @GetMapping
    private ResponseEntity<List<Tecnico>> getAllTecnico(){
        return ResponseEntity.ok(this.tecnicoService.findAll());
    }
*/

  /* @PostMapping("filtrar/")
  private ResponseEntity<List<Tecnico>> search(@RequestBody Tecnico tecnicoRq){
      return ResponseEntity.ok(this.tecnicoService.filtrarTecnicos(tecnicoRq));
  } */

  public onConsultar(tecnicoRq: Tecnico): Observable<any> {
    return this.http.post(`${this.API_REST}/tecnico/filtrar/`, tecnicoRq, {
      headers: this.headers,
    });
  }

  /* @PutMapping("update/")
  private ResponseEntity<Tecnico> update(@RequestBody Tecnico tecnicoRq){
      return ResponseEntity.ok(this.tecnicoService.update(tecnicoRq));
  } */

  public onUpdate(tecnicoRq: Tecnico): Observable<any> {
    return this.http.put(`${this.API_REST}/tecnico/update/`, tecnicoRq, {
      headers: this.headers,
    });
  }

  /* @DeleteMapping("delete/{id}")
  private ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
      this.tecnicoService.deleteById(id);
      return ResponseEntity.ok(!(this.tecnicoService.getById(id) != null));
  } */

  public onDeleteById(id: number): Observable<any> {
    return this.http.delete(`${this.API_REST}/tecnico/delete/${id}`);
  }

  /* @PostMapping("save/")
  private ResponseEntity<Tecnico> saveTecnico(@RequestBody Tecnico tecnicoRq){
      return ResponseEntity.ok(this.tecnicoService.save(tecnicoRq));
  } */
  public onGuardar(tecnicoRq: Tecnico): Observable<any> {
    return this.http.post(`${this.API_REST}/tecnico/save/`, tecnicoRq, {
      headers: this.headers,
    });
  }

  public getAll(): Observable<any> {
    return this.http.get(`${this.API_REST}/tecnico/`);
  }

  public reactivar(id: number): Observable<any> {
    return this.http.get(`${this.API_REST}/tecnico/activar/${id}`);
  }

  listarTecnicos(tecnicoRq: any, page: number, size: number): Observable<any> {
    const params = new HttpParams()
    .set('page', page.toString())
    .set('size', size.toString());
    return this.http.post<PaginatedResponse<Tecnico>>(`${this.API_REST}/tecnico/listar/`, tecnicoRq, { params, headers: this.headers });
  }
}
