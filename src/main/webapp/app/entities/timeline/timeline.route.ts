import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TimelineComponent } from './timeline.component';
import { TimelineDetailComponent } from './timeline-detail.component';
import { TimelinePopupComponent } from './timeline-dialog.component';
import { TimelineDeletePopupComponent } from './timeline-delete-dialog.component';

@Injectable()
export class TimelineResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const timelineRoute: Routes = [
    {
        path: 'timeline',
        component: TimelineComponent,
        resolve: {
            'pagingParams': TimelineResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.timeline.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'timeline/:id',
        component: TimelineDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.timeline.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timelinePopupRoute: Routes = [
    {
        path: 'timeline-new',
        component: TimelinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.timeline.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'timeline/:id/edit',
        component: TimelinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.timeline.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'timeline/:id/delete',
        component: TimelineDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.timeline.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
