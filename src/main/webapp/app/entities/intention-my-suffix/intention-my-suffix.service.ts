import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { IntentionMySuffix } from './intention-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class IntentionMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/intentions';

    constructor(private http: Http) { }

    create(intention: IntentionMySuffix): Observable<IntentionMySuffix> {
        const copy = this.convert(intention);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(intention: IntentionMySuffix): Observable<IntentionMySuffix> {
        const copy = this.convert(intention);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<IntentionMySuffix> {
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
     * Convert a returned JSON object to IntentionMySuffix.
     */
    private convertItemFromServer(json: any): IntentionMySuffix {
        const entity: IntentionMySuffix = Object.assign(new IntentionMySuffix(), json);
        return entity;
    }

    /**
     * Convert a IntentionMySuffix to a JSON which can be sent to the server.
     */
    private convert(intention: IntentionMySuffix): IntentionMySuffix {
        const copy: IntentionMySuffix = Object.assign({}, intention);
        return copy;
    }
}
