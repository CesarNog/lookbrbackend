import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Signup } from './signup.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SignupService {

    private resourceUrl = SERVER_API_URL + 'api/signups';

    constructor(private http: Http) { }

    create(signup: Signup): Observable<Signup> {
        const copy = this.convert(signup);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(signup: Signup): Observable<Signup> {
        const copy = this.convert(signup);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Signup> {
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
     * Convert a returned JSON object to Signup.
     */
    private convertItemFromServer(json: any): Signup {
        const entity: Signup = Object.assign(new Signup(), json);
        return entity;
    }

    /**
     * Convert a Signup to a JSON which can be sent to the server.
     */
    private convert(signup: Signup): Signup {
        const copy: Signup = Object.assign({}, signup);
        return copy;
    }
}
