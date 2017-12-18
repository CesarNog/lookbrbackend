import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    InspirationService,
    InspirationPopupService,
    InspirationComponent,
    InspirationDetailComponent,
    InspirationDialogComponent,
    InspirationPopupComponent,
    InspirationDeletePopupComponent,
    InspirationDeleteDialogComponent,
    inspirationRoute,
    inspirationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...inspirationRoute,
    ...inspirationPopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InspirationComponent,
        InspirationDetailComponent,
        InspirationDialogComponent,
        InspirationDeleteDialogComponent,
        InspirationPopupComponent,
        InspirationDeletePopupComponent,
    ],
    entryComponents: [
        InspirationComponent,
        InspirationDialogComponent,
        InspirationPopupComponent,
        InspirationDeleteDialogComponent,
        InspirationDeletePopupComponent,
    ],
    providers: [
        InspirationService,
        InspirationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendInspirationModule {}
