import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { IntentionMySuffix } from './intention-my-suffix.model';
import { IntentionMySuffixService } from './intention-my-suffix.service';

@Component({
    selector: 'jhi-intention-my-suffix-detail',
    templateUrl: './intention-my-suffix-detail.component.html'
})
export class IntentionMySuffixDetailComponent implements OnInit, OnDestroy {

    intention: IntentionMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private intentionService: IntentionMySuffixService,
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
