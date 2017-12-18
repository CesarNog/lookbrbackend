import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { SocialMedia } from './social-media.model';
import { SocialMediaService } from './social-media.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-social-media',
    templateUrl: './social-media.component.html'
})
export class SocialMediaComponent implements OnInit, OnDestroy {
socialMedias: SocialMedia[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private socialMediaService: SocialMediaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.socialMediaService.query().subscribe(
            (res: ResponseWrapper) => {
                this.socialMedias = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSocialMedias();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SocialMedia) {
        return item.id;
    }
    registerChangeInSocialMedias() {
        this.eventSubscriber = this.eventManager.subscribe('socialMediaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
