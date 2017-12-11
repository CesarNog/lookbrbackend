import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    ClosetService,
    ClosetPopupService,
    ClosetComponent,
    ClosetDetailComponent,
    ClosetDialogComponent,
    ClosetPopupComponent,
    ClosetDeletePopupComponent,
    ClosetDeleteDialogComponent,
    closetRoute,
    closetPopupRoute,
    ClosetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...closetRoute,
    ...closetPopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ClosetComponent,
        ClosetDetailComponent,
        ClosetDialogComponent,
        ClosetDeleteDialogComponent,
        ClosetPopupComponent,
        ClosetDeletePopupComponent,
    ],
    entryComponents: [
        ClosetComponent,
        ClosetDialogComponent,
        ClosetPopupComponent,
        ClosetDeleteDialogComponent,
        ClosetDeletePopupComponent,
    ],
    providers: [
        ClosetService,
        ClosetPopupService,
        ClosetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendClosetModule {}
