import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Inspiration } from './inspiration.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InspirationService {

    private resourceUrl = SERVER_API_URL + 'api/inspirations';

    constructor(private http: Http) { }

    create(inspiration: Inspiration): Observable<Inspiration> {
        const copy = this.convert(inspiration);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(inspiration: Inspiration): Observable<Inspiration> {
        const copy = this.convert(inspiration);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Inspiration> {
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
     * Convert a returned JSON object to Inspiration.
     */
    private convertItemFromServer(json: any): Inspiration {
        const entity: Inspiration = Object.assign(new Inspiration(), json);
        return entity;
    }

    /**
     * Convert a Inspiration to a JSON which can be sent to the server.
     */
    private convert(inspiration: Inspiration): Inspiration {
        const copy: Inspiration = Object.assign({}, inspiration);
        return copy;
    }
}
