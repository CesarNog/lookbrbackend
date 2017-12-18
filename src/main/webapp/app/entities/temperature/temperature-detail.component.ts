import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Temperature } from './temperature.model';
import { TemperatureService } from './temperature.service';

@Component({
    selector: 'jhi-temperature-detail',
    templateUrl: './temperature-detail.component.html'
})
export class TemperatureDetailComponent implements OnInit, OnDestroy {

    temperature: Temperature;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private temperatureService: TemperatureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTemperatures();
    }

    load(id) {
        this.temperatureService.find(id).subscribe((temperature) => {
            this.temperature = temperature;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTemperatures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'temperatureListModification',
            (response) => this.load(this.temperature.id)
        );
    }
}
