import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ClosetMySuffix } from './closet-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ClosetMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/closets';

    constructor(private http: Http) { }

    create(closet: ClosetMySuffix): Observable<ClosetMySuffix> {
        const copy = this.convert(closet);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(closet: ClosetMySuffix): Observable<ClosetMySuffix> {
        const copy = this.convert(closet);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ClosetMySuffix> {
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
     * Convert a returned JSON object to ClosetMySuffix.
     */
    private convertItemFromServer(json: any): ClosetMySuffix {
        const entity: ClosetMySuffix = Object.assign(new ClosetMySuffix(), json);
        return entity;
    }

    /**
     * Convert a ClosetMySuffix to a JSON which can be sent to the server.
     */
    private convert(closet: ClosetMySuffix): ClosetMySuffix {
        const copy: ClosetMySuffix = Object.assign({}, closet);
        return copy;
    }
}
