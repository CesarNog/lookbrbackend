import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    DayTimeService,
    DayTimePopupService,
    DayTimeComponent,
    DayTimeDetailComponent,
    DayTimeDialogComponent,
    DayTimePopupComponent,
    DayTimeDeletePopupComponent,
    DayTimeDeleteDialogComponent,
    dayTimeRoute,
    dayTimePopupRoute,
} from './';

const ENTITY_STATES = [
    ...dayTimeRoute,
    ...dayTimePopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DayTimeComponent,
        DayTimeDetailComponent,
        DayTimeDialogComponent,
        DayTimeDeleteDialogComponent,
        DayTimePopupComponent,
        DayTimeDeletePopupComponent,
    ],
    entryComponents: [
        DayTimeComponent,
        DayTimeDialogComponent,
        DayTimePopupComponent,
        DayTimeDeleteDialogComponent,
        DayTimeDeletePopupComponent,
    ],
    providers: [
        DayTimeService,
        DayTimePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendDayTimeModule {}
