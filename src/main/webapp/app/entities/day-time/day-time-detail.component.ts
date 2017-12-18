import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DayTime } from './day-time.model';
import { DayTimeService } from './day-time.service';

@Component({
    selector: 'jhi-day-time-detail',
    templateUrl: './day-time-detail.component.html'
})
export class DayTimeDetailComponent implements OnInit, OnDestroy {

    dayTime: DayTime;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dayTimeService: DayTimeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDayTimes();
    }

    load(id) {
        this.dayTimeService.find(id).subscribe((dayTime) => {
            this.dayTime = dayTime;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDayTimes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dayTimeListModification',
            (response) => this.load(this.dayTime.id)
        );
    }
}
