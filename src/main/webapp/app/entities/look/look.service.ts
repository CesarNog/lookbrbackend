import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Look } from './look.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LookService {

    private resourceUrl = SERVER_API_URL + 'api/looks';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/looks';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(look: Look): Observable<Look> {
        const copy = this.convert(look);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(look: Look): Observable<Look> {
        const copy = this.convert(look);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Look> {
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
     * Convert a returned JSON object to Look.
     */
    private convertItemFromServer(json: any): Look {
        const entity: Look = Object.assign(new Look(), json);
        entity.dayTime = this.dateUtils
            .convertLocalDateFromServer(json.dayTime);
        return entity;
    }

    /**
     * Convert a Look to a JSON which can be sent to the server.
     */
    private convert(look: Look): Look {
        const copy: Look = Object.assign({}, look);
        copy.dayTime = this.dateUtils
            .convertLocalDateToServer(look.dayTime);
        return copy;
    }
}
