import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Comment } from './comment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CommentService {

    private resourceUrl = SERVER_API_URL + 'api/comments';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/comments';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(comment: Comment): Observable<Comment> {
        const copy = this.convert(comment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(comment: Comment): Observable<Comment> {
        const copy = this.convert(comment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Comment> {
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
     * Convert a returned JSON object to Comment.
     */
    private convertItemFromServer(json: any): Comment {
        const entity: Comment = Object.assign(new Comment(), json);
        entity.dateVoted = this.dateUtils
            .convertLocalDateFromServer(json.dateVoted);
        return entity;
    }

    /**
     * Convert a Comment to a JSON which can be sent to the server.
     */
    private convert(comment: Comment): Comment {
        const copy: Comment = Object.assign({}, comment);
        copy.dateVoted = this.dateUtils
            .convertLocalDateToServer(comment.dateVoted);
        return copy;
    }
}
