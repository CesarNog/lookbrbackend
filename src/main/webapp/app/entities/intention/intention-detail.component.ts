import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Intention } from './intention.model';
import { IntentionService } from './intention.service';

@Component({
    selector: 'jhi-intention-detail',
    templateUrl: './intention-detail.component.html'
})
export class IntentionDetailComponent implements OnInit, OnDestroy {

    intention: Intention;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private intentionService: IntentionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIntentions();
    }

    load(id) {
        this.intentionService.find(id).subscribe((intention) => {
            this.intention = intention;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIntentions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'intentionListModification',
            (response) => this.load(this.intention.id)
        );
    }
}
