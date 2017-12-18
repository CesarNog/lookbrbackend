import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Timeline } from './timeline.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TimelineService {

    private resourceUrl = SERVER_API_URL + 'api/timelines';

    constructor(private http: Http) { }

    create(timeline: Timeline): Observable<Timeline> {
        const copy = this.convert(timeline);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(timeline: Timeline): Observable<Timeline> {
        const copy = this.convert(timeline);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Timeline> {
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
     * Convert a returned JSON object to Timeline.
     */
    private convertItemFromServer(json: any): Timeline {
        const entity: Timeline = Object.assign(new Timeline(), json);
        return entity;
    }

    /**
     * Convert a Timeline to a JSON which can be sent to the server.
     */
    private convert(timeline: Timeline): Timeline {
        const copy: Timeline = Object.assign({}, timeline);
        return copy;
    }
}
