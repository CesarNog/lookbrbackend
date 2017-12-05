import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LookbrbackendLoginMySuffixModule } from './login-my-suffix/login-my-suffix.module';
import { LookbrbackendSignupMySuffixModule } from './signup-my-suffix/signup-my-suffix.module';
import { LookbrbackendTimelineMySuffixModule } from './timeline-my-suffix/timeline-my-suffix.module';
import { LookbrbackendConsultantsMySuffixModule } from './consultants-my-suffix/consultants-my-suffix.module';
import { LookbrbackendIntentionMySuffixModule } from './intention-my-suffix/intention-my-suffix.module';
import { LookbrbackendLookMySuffixModule } from './look-my-suffix/look-my-suffix.module';
import { LookbrbackendClosetMySuffixModule } from './closet-my-suffix/closet-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LookbrbackendLoginMySuffixModule,
        LookbrbackendSignupMySuffixModule,
        LookbrbackendTimelineMySuffixModule,
        LookbrbackendConsultantsMySuffixModule,
        LookbrbackendIntentionMySuffixModule,
        LookbrbackendLookMySuffixModule,
        LookbrbackendClosetMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendEntityModule {}
