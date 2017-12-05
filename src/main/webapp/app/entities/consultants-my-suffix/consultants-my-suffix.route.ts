import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConsultantsMySuffixComponent } from './consultants-my-suffix.component';
import { ConsultantsMySuffixDetailComponent } from './consultants-my-suffix-detail.component';
import { ConsultantsMySuffixPopupComponent } from './consultants-my-suffix-dialog.component';
import { ConsultantsMySuffixDeletePopupComponent } from './consultants-my-suffix-delete-dialog.component';

@Injectable()
export class ConsultantsMySuffixResolvePagingParams implements Resolve<any> {

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

export const consultantsRoute: Routes = [
    {
        path: 'consultants-my-suffix',
        component: ConsultantsMySuffixComponent,
        resolve: {
            'pagingParams': ConsultantsMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.consultants.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'consultants-my-suffix/:id',
        component: ConsultantsMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.consultants.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const consultantsPopupRoute: Routes = [
    {
        path: 'consultants-my-suffix-new',
        component: ConsultantsMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.consultants.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultants-my-suffix/:id/edit',
        component: ConsultantsMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.consultants.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultants-my-suffix/:id/delete',
        component: ConsultantsMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.consultants.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
