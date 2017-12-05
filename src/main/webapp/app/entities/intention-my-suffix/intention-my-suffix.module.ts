import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    IntentionMySuffixService,
    IntentionMySuffixPopupService,
    IntentionMySuffixComponent,
    IntentionMySuffixDetailComponent,
    IntentionMySuffixDialogComponent,
    IntentionMySuffixPopupComponent,
    IntentionMySuffixDeletePopupComponent,
    IntentionMySuffixDeleteDialogComponent,
    intentionRoute,
    intentionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...intentionRoute,
    ...intentionPopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IntentionMySuffixComponent,
        IntentionMySuffixDetailComponent,
        IntentionMySuffixDialogComponent,
        IntentionMySuffixDeleteDialogComponent,
        IntentionMySuffixPopupComponent,
        IntentionMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        IntentionMySuffixComponent,
        IntentionMySuffixDialogComponent,
        IntentionMySuffixPopupComponent,
        IntentionMySuffixDeleteDialogComponent,
        IntentionMySuffixDeletePopupComponent,
    ],
    providers: [
        IntentionMySuffixService,
        IntentionMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendIntentionMySuffixModule {}
