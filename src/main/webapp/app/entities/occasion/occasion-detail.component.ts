import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Occasion } from './occasion.model';
import { OccasionService } from './occasion.service';

@Component({
    selector: 'jhi-occasion-detail',
    templateUrl: './occasion-detail.component.html'
})
export class OccasionDetailComponent implements OnInit, OnDestroy {

    occasion: Occasion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private occasionService: OccasionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOccasions();
    }

    load(id) {
        this.occasionService.find(id).subscribe((occasion) => {
            this.occasion = occasion;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOccasions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'occasionListModification',
            (response) => this.load(this.occasion.id)
        );
    }
}
