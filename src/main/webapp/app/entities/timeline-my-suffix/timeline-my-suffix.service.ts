import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { TimelineMySuffix } from './timeline-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TimelineMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/timelines';

    constructor(private http: Http) { }

    create(timeline: TimelineMySuffix): Observable<TimelineMySuffix> {
        const copy = this.convert(timeline);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(timeline: TimelineMySuffix): Observable<TimelineMySuffix> {
        const copy = this.convert(timeline);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<TimelineMySuffix> {
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
     * Convert a returned JSON object to TimelineMySuffix.
     */
    private convertItemFromServer(json: any): TimelineMySuffix {
        const entity: TimelineMySuffix = Object.assign(new TimelineMySuffix(), json);
        return entity;
    }

    /**
     * Convert a TimelineMySuffix to a JSON which can be sent to the server.
     */
    private convert(timeline: TimelineMySuffix): TimelineMySuffix {
        const copy: TimelineMySuffix = Object.assign({}, timeline);
        return copy;
    }
}
