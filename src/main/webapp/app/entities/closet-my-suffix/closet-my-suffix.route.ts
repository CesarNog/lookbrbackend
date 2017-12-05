import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ClosetMySuffixComponent } from './closet-my-suffix.component';
import { ClosetMySuffixDetailComponent } from './closet-my-suffix-detail.component';
import { ClosetMySuffixPopupComponent } from './closet-my-suffix-dialog.component';
import { ClosetMySuffixDeletePopupComponent } from './closet-my-suffix-delete-dialog.component';

@Injectable()
export class ClosetMySuffixResolvePagingParams implements Resolve<any> {

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
        path: 'closet-my-suffix',
        component: ClosetMySuffixComponent,
        resolve: {
            'pagingParams': ClosetMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.closet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'closet-my-suffix/:id',
        component: ClosetMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.closet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const closetPopupRoute: Routes = [
    {
        path: 'closet-my-suffix-new',
        component: ClosetMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.closet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'closet-my-suffix/:id/edit',
        component: ClosetMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.closet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'closet-my-suffix/:id/delete',
        component: ClosetMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.closet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
