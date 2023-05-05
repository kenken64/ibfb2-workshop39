import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, firstValueFrom, lastValueFrom } from 'rxjs';
import { Character } from '../model/Character';
import { Comment } from '../model/Comment';

@Injectable({
  providedIn: 'root'
})
export class MarvelCharService {
  private MARVEL_API_URL:string = "/api/characters";
  private headers = new HttpHeaders()
      .set("Content-Type", "application/json; charset=utf-8");
  constructor(private httpClient: HttpClient) { }

  getCharacters(charName:string , 
      offset: number, limit: number): Promise<any>{
    console.log(charName);
    const params = new HttpParams()
        .set("charName", charName)
        .set("limit", limit)
        .set("offset", offset);

    return lastValueFrom(this.httpClient
          .get<Character[]>(this.MARVEL_API_URL, {params: params, headers: this.headers}))

  }

  getCharacterDetails(charId: string): Promise<any>{
    return lastValueFrom(this.httpClient
      .get<Character>(this.MARVEL_API_URL + '/' + charId, {headers: this.headers}));
  }

 
  saveComment(c:Comment) : Observable<any>{
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const body=JSON.stringify(c);
    console.log("save comment !");
    console.log("save comment !" + c.charId);
    return this.httpClient
      .post<Comment>(this.MARVEL_API_URL+"/" + c.charId, body, 
      {headers: headers});
  }

  getCharComments(charId: string): Promise<Comment[]> {
    return lastValueFrom(this.httpClient
      .get<Comment[]>(this.MARVEL_API_URL + '/comments/' + charId, {headers: this.headers}))
  }

}
