import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    TimelineMySuffixService,
    TimelineMySuffixPopupService,
    TimelineMySuffixComponent,
    TimelineMySuffixDetailComponent,
    TimelineMySuffixDialogComponent,
    TimelineMySuffixPopupComponent,
    TimelineMySuffixDeletePopupComponent,
    TimelineMySuffixDeleteDialogComponent,
    timelineRoute,
    timelinePopupRoute,
} from './';

const ENTITY_STATES = [
    ...timelineRoute,
    ...timelinePopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TimelineMySuffixComponent,
        TimelineMySuffixDetailComponent,
        TimelineMySuffixDialogComponent,
        TimelineMySuffixDeleteDialogComponent,
        TimelineMySuffixPopupComponent,
        TimelineMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        TimelineMySuffixComponent,
        TimelineMySuffixDialogComponent,
        TimelineMySuffixPopupComponent,
        TimelineMySuffixDeleteDialogComponent,
        TimelineMySuffixDeletePopupComponent,
    ],
    providers: [
        TimelineMySuffixService,
        TimelineMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendTimelineMySuffixModule {}
