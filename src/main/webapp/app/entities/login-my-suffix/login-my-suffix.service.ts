import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { LoginMySuffix } from './login-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LoginMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/logins';

    constructor(private http: Http) { }

    create(login: LoginMySuffix): Observable<LoginMySuffix> {
        const copy = this.convert(login);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(login: LoginMySuffix): Observable<LoginMySuffix> {
        const copy = this.convert(login);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<LoginMySuffix> {
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to LoginMySuffix.
     */
    private convertItemFromServer(json: any): LoginMySuffix {
        const entity: LoginMySuffix = Object.assign(new LoginMySuffix(), json);
        return entity;
    }

    /**
     * Convert a LoginMySuffix to a JSON which can be sent to the server.
     */
    private convert(login: LoginMySuffix): LoginMySuffix {
        const copy: LoginMySuffix = Object.assign({}, login);
        return copy;
    }
}
