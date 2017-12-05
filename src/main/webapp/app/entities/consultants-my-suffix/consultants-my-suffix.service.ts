import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ConsultantsMySuffix } from './consultants-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConsultantsMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/consultants';

    constructor(private http: Http) { }

    create(consultants: ConsultantsMySuffix): Observable<ConsultantsMySuffix> {
        const copy = this.convert(consultants);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(consultants: ConsultantsMySuffix): Observable<ConsultantsMySuffix> {
        const copy = this.convert(consultants);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ConsultantsMySuffix> {
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
     * Convert a returned JSON object to ConsultantsMySuffix.
     */
    private convertItemFromServer(json: any): ConsultantsMySuffix {
        const entity: ConsultantsMySuffix = Object.assign(new ConsultantsMySuffix(), json);
        return entity;
    }

    /**
     * Convert a ConsultantsMySuffix to a JSON which can be sent to the server.
     */
    private convert(consultants: ConsultantsMySuffix): ConsultantsMySuffix {
        const copy: ConsultantsMySuffix = Object.assign({}, consultants);
        return copy;
    }
}
