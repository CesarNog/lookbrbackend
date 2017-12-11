import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LookbrbackendLoginModule } from './login/login.module';
import { LookbrbackendSignupModule } from './signup/signup.module';
import { LookbrbackendTimelineModule } from './timeline/timeline.module';
import { LookbrbackendConsultantModule } from './consultant/consultant.module';
import { LookbrbackendSocialMediaModule } from './social-media/social-media.module';
import { LookbrbackendIntentionModule } from './intention/intention.module';
import { LookbrbackendInspirationModule } from './inspiration/inspiration.module';
import { LookbrbackendOccasionModule } from './occasion/occasion.module';
import { LookbrbackendLookModule } from './look/look.module';
import { LookbrbackendCommentModule } from './comment/comment.module';
import { LookbrbackendConsultantVoteModule } from './consultant-vote/consultant-vote.module';
import { LookbrbackendClosetModule } from './closet/closet.module';
import { LookbrbackendTemperatureModule } from './temperature/temperature.module';
import { LookbrbackendDayTimeModule } from './day-time/day-time.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LookbrbackendLoginModule,
        LookbrbackendSignupModule,
        LookbrbackendTimelineModule,
        LookbrbackendConsultantModule,
        LookbrbackendSocialMediaModule,
        LookbrbackendIntentionModule,
        LookbrbackendInspirationModule,
        LookbrbackendOccasionModule,
        LookbrbackendLookModule,
        LookbrbackendCommentModule,
        LookbrbackendConsultantVoteModule,
        LookbrbackendClosetModule,
        LookbrbackendTemperatureModule,
        LookbrbackendDayTimeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendEntityModule {}
