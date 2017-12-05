import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IntentionMySuffixComponent } from './intention-my-suffix.component';
import { IntentionMySuffixDetailComponent } from './intention-my-suffix-detail.component';
import { IntentionMySuffixPopupComponent } from './intention-my-suffix-dialog.component';
import { IntentionMySuffixDeletePopupComponent } from './intention-my-suffix-delete-dialog.component';

export const intentionRoute: Routes = [
    {
        path: 'intention-my-suffix',
        component: IntentionMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.intention.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'intention-my-suffix/:id',
        component: IntentionMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.intention.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const intentionPopupRoute: Routes = [
    {
        path: 'intention-my-suffix-new',
        component: IntentionMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.intention.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'intention-my-suffix/:id/edit',
        component: IntentionMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.intention.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'intention-my-suffix/:id/delete',
        component: IntentionMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.intention.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
