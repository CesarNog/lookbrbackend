import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ClosetComponent } from './closet.component';
import { ClosetDetailComponent } from './closet-detail.component';
import { ClosetPopupComponent } from './closet-dialog.component';
import { ClosetDeletePopupComponent } from './closet-delete-dialog.component';

@Injectable()
export class ClosetResolvePagingParams implements Resolve<any> {

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

export const closetRoute: Routes = [
    {
        path: 'closet',
        component: ClosetComponent,
        resolve: {
            'pagingParams': ClosetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.closet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'closet/:id',
        component: ClosetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.closet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const closetPopupRoute: Routes = [
    {
        path: 'closet-new',
        component: ClosetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.closet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'closet/:id/edit',
        component: ClosetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.closet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'closet/:id/delete',
        component: ClosetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.closet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
