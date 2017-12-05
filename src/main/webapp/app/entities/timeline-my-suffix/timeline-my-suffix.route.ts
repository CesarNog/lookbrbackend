import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TimelineMySuffixComponent } from './timeline-my-suffix.component';
import { TimelineMySuffixDetailComponent } from './timeline-my-suffix-detail.component';
import { TimelineMySuffixPopupComponent } from './timeline-my-suffix-dialog.component';
import { TimelineMySuffixDeletePopupComponent } from './timeline-my-suffix-delete-dialog.component';

export const timelineRoute: Routes = [
    {
        path: 'timeline-my-suffix',
        component: TimelineMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.timeline.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'timeline-my-suffix/:id',
        component: TimelineMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.timeline.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timelinePopupRoute: Routes = [
    {
        path: 'timeline-my-suffix-new',
        component: TimelineMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.timeline.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'timeline-my-suffix/:id/edit',
        component: TimelineMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.timeline.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'timeline-my-suffix/:id/delete',
        component: TimelineMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.timeline.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
