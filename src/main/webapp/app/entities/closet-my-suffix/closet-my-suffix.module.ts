import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    ClosetMySuffixService,
    ClosetMySuffixPopupService,
    ClosetMySuffixComponent,
    ClosetMySuffixDetailComponent,
    ClosetMySuffixDialogComponent,
    ClosetMySuffixPopupComponent,
    ClosetMySuffixDeletePopupComponent,
    ClosetMySuffixDeleteDialogComponent,
    closetRoute,
    closetPopupRoute,
    ClosetMySuffixResolvePagingParams,
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
        ClosetMySuffixComponent,
        ClosetMySuffixDetailComponent,
        ClosetMySuffixDialogComponent,
        ClosetMySuffixDeleteDialogComponent,
        ClosetMySuffixPopupComponent,
        ClosetMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ClosetMySuffixComponent,
        ClosetMySuffixDialogComponent,
        ClosetMySuffixPopupComponent,
        ClosetMySuffixDeleteDialogComponent,
        ClosetMySuffixDeletePopupComponent,
    ],
    providers: [
        ClosetMySuffixService,
        ClosetMySuffixPopupService,
        ClosetMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendClosetMySuffixModule {}
