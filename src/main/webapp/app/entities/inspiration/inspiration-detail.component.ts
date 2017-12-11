import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Inspiration } from './inspiration.model';
import { InspirationService } from './inspiration.service';

@Component({
    selector: 'jhi-inspiration-detail',
    templateUrl: './inspiration-detail.component.html'
})
export class InspirationDetailComponent implements OnInit, OnDestroy {

    inspiration: Inspiration;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inspirationService: InspirationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInspirations();
    }

    load(id) {
        this.inspirationService.find(id).subscribe((inspiration) => {
            this.inspiration = inspiration;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInspirations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inspirationListModification',
            (response) => this.load(this.inspiration.id)
        );
    }
}
