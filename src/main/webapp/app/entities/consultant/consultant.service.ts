import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Consultant } from './consultant.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConsultantService {

    private resourceUrl = SERVER_API_URL + 'api/consultants';

    constructor(private http: Http) { }

    create(consultant: Consultant): Observable<Consultant> {
        const copy = this.convert(consultant);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(consultant: Consultant): Observable<Consultant> {
        const copy = this.convert(consultant);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Consultant> {
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
     * Convert a returned JSON object to Consultant.
     */
    private convertItemFromServer(json: any): Consultant {
        const entity: Consultant = Object.assign(new Consultant(), json);
        return entity;
    }

    /**
     * Convert a Consultant to a JSON which can be sent to the server.
     */
    private convert(consultant: Consultant): Consultant {
        const copy: Consultant = Object.assign({}, consultant);
        return copy;
    }
}
