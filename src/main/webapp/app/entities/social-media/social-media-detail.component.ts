import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SocialMedia } from './social-media.model';
import { SocialMediaService } from './social-media.service';

@Component({
    selector: 'jhi-social-media-detail',
    templateUrl: './social-media-detail.component.html'
})
export class SocialMediaDetailComponent implements OnInit, OnDestroy {

    socialMedia: SocialMedia;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private socialMediaService: SocialMediaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSocialMedias();
    }

    load(id) {
        this.socialMediaService.find(id).subscribe((socialMedia) => {
            this.socialMedia = socialMedia;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSocialMedias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'socialMediaListModification',
            (response) => this.load(this.socialMedia.id)
        );
    }
}
