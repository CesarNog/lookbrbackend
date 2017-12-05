import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TimelineMySuffix } from './timeline-my-suffix.model';
import { TimelineMySuffixService } from './timeline-my-suffix.service';

@Component({
    selector: 'jhi-timeline-my-suffix-detail',
    templateUrl: './timeline-my-suffix-detail.component.html'
})
export class TimelineMySuffixDetailComponent implements OnInit, OnDestroy {

    timeline: TimelineMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private timelineService: TimelineMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTimelines();
    }

    load(id) {
        this.timelineService.find(id).subscribe((timeline) => {
            this.timeline = timeline;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTimelines() {
        this.eventSubscriber = this.eventManager.subscribe(
            'timelineListModification',
            (response) => this.load(this.timeline.id)
        );
    }
}
