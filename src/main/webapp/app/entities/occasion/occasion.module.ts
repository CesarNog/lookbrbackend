import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    OccasionService,
    OccasionPopupService,
    OccasionComponent,
    OccasionDetailComponent,
    OccasionDialogComponent,
    OccasionPopupComponent,
    OccasionDeletePopupComponent,
    OccasionDeleteDialogComponent,
    occasionRoute,
    occasionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...occasionRoute,
    ...occasionPopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OccasionComponent,
        OccasionDetailComponent,
        OccasionDialogComponent,
        OccasionDeleteDialogComponent,
        OccasionPopupComponent,
        OccasionDeletePopupComponent,
    ],
    entryComponents: [
        OccasionComponent,
        OccasionDialogComponent,
        OccasionPopupComponent,
        OccasionDeleteDialogComponent,
        OccasionDeletePopupComponent,
    ],
    providers: [
        OccasionService,
        OccasionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendOccasionModule {}
