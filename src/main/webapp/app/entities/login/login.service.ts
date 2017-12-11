import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Login } from './login.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LoginService {

    private resourceUrl = SERVER_API_URL + 'api/logins';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/logins';

    constructor(private http: Http) { }

    create(login: Login): Observable<Login> {
        const copy = this.convert(login);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(login: Login): Observable<Login> {
        const copy = this.convert(login);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Login> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Login.
     */
    private convertItemFromServer(json: any): Login {
        const entity: Login = Object.assign(new Login(), json);
        return entity;
    }

    /**
     * Convert a Login to a JSON which can be sent to the server.
     */
    private convert(login: Login): Login {
        const copy: Login = Object.assign({}, login);
        return copy;
    }
}
