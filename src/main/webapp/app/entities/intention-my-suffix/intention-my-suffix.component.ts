import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IntentionMySuffix } from './intention-my-suffix.model';
import { IntentionMySuffixService } from './intention-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-intention-my-suffix',
    templateUrl: './intention-my-suffix.component.html'
})
export class IntentionMySuffixComponent implements OnInit, OnDestroy {
intentions: IntentionMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private intentionService: IntentionMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.intentionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.intentions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIntentions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IntentionMySuffix) {
        return item.id;
    }
    registerChangeInIntentions() {
        this.eventSubscriber = this.eventManager.subscribe('intentionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
