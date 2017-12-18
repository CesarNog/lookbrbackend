import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { DayTime } from './day-time.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DayTimeService {

    private resourceUrl = SERVER_API_URL + 'api/day-times';

    constructor(private http: Http) { }

    create(dayTime: DayTime): Observable<DayTime> {
        const copy = this.convert(dayTime);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(dayTime: DayTime): Observable<DayTime> {
        const copy = this.convert(dayTime);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DayTime> {
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
     * Convert a returned JSON object to DayTime.
     */
    private convertItemFromServer(json: any): DayTime {
        const entity: DayTime = Object.assign(new DayTime(), json);
        return entity;
    }

    /**
     * Convert a DayTime to a JSON which can be sent to the server.
     */
    private convert(dayTime: DayTime): DayTime {
        const copy: DayTime = Object.assign({}, dayTime);
        return copy;
    }
}
