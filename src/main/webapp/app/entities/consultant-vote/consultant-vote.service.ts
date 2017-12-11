import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ConsultantVote } from './consultant-vote.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConsultantVoteService {

    private resourceUrl = SERVER_API_URL + 'api/consultant-votes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/consultant-votes';

    constructor(private http: Http) { }

    create(consultantVote: ConsultantVote): Observable<ConsultantVote> {
        const copy = this.convert(consultantVote);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(consultantVote: ConsultantVote): Observable<ConsultantVote> {
        const copy = this.convert(consultantVote);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ConsultantVote> {
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
     * Convert a returned JSON object to ConsultantVote.
     */
    private convertItemFromServer(json: any): ConsultantVote {
        const entity: ConsultantVote = Object.assign(new ConsultantVote(), json);
        return entity;
    }

    /**
     * Convert a ConsultantVote to a JSON which can be sent to the server.
     */
    private convert(consultantVote: ConsultantVote): ConsultantVote {
        const copy: ConsultantVote = Object.assign({}, consultantVote);
        return copy;
    }
}
