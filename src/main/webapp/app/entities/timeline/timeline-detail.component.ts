import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Timeline } from './timeline.model';
import { TimelineService } from './timeline.service';

@Component({
    selector: 'jhi-timeline-detail',
    templateUrl: './timeline-detail.component.html'
})
export class TimelineDetailComponent implements OnInit, OnDestroy {

    timeline: Timeline;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private timelineService: TimelineService,
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
