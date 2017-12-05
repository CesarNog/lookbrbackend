import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LookMySuffix } from './look-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LookMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/looks';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(look: LookMySuffix): Observable<LookMySuffix> {
        const copy = this.convert(look);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(look: LookMySuffix): Observable<LookMySuffix> {
        const copy = this.convert(look);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<LookMySuffix> {
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
     * Convert a returned JSON object to LookMySuffix.
     */
    private convertItemFromServer(json: any): LookMySuffix {
        const entity: LookMySuffix = Object.assign(new LookMySuffix(), json);
        entity.dayTime = this.dateUtils
            .convertLocalDateFromServer(json.dayTime);
        return entity;
    }

    /**
     * Convert a LookMySuffix to a JSON which can be sent to the server.
     */
    private convert(look: LookMySuffix): LookMySuffix {
        const copy: LookMySuffix = Object.assign({}, look);
        copy.dayTime = this.dateUtils
            .convertLocalDateToServer(look.dayTime);
        return copy;
    }
}
