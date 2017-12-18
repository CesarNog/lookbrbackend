import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { SocialMedia } from './social-media.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SocialMediaService {

    private resourceUrl = SERVER_API_URL + 'api/social-medias';

    constructor(private http: Http) { }

    create(socialMedia: SocialMedia): Observable<SocialMedia> {
        const copy = this.convert(socialMedia);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(socialMedia: SocialMedia): Observable<SocialMedia> {
        const copy = this.convert(socialMedia);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SocialMedia> {
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
     * Convert a returned JSON object to SocialMedia.
     */
    private convertItemFromServer(json: any): SocialMedia {
        const entity: SocialMedia = Object.assign(new SocialMedia(), json);
        return entity;
    }

    /**
     * Convert a SocialMedia to a JSON which can be sent to the server.
     */
    private convert(socialMedia: SocialMedia): SocialMedia {
        const copy: SocialMedia = Object.assign({}, socialMedia);
        return copy;
    }
}
