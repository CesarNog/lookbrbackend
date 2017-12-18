import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Inspiration } from './inspiration.model';
import { InspirationService } from './inspiration.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-inspiration',
    templateUrl: './inspiration.component.html'
})
export class InspirationComponent implements OnInit, OnDestroy {
inspirations: Inspiration[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private inspirationService: InspirationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.inspirationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.inspirations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInspirations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Inspiration) {
        return item.id;
    }
    registerChangeInInspirations() {
        this.eventSubscriber = this.eventManager.subscribe('inspirationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
