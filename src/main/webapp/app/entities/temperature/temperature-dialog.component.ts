import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Temperature } from './temperature.model';
import { TemperaturePopupService } from './temperature-popup.service';
import { TemperatureService } from './temperature.service';
import { Inspiration, InspirationService } from '../inspiration';
import { Look, LookService } from '../look';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-temperature-dialog',
    templateUrl: './temperature-dialog.component.html'
})
export class TemperatureDialogComponent implements OnInit {

    temperature: Temperature;
    isSaving: boolean;

    inspirations: Inspiration[];

    looks: Look[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private temperatureService: TemperatureService,
        private inspirationService: InspirationService,
        private lookService: LookService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inspirationService.query()
            .subscribe((res: ResponseWrapper) => { this.inspirations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.lookService.query()
            .subscribe((res: ResponseWrapper) => { this.looks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.temperature.id !== undefined) {
            this.subscribeToSaveResponse(
                this.temperatureService.update(this.temperature));
        } else {
            this.subscribeToSaveResponse(
                this.temperatureService.create(this.temperature));
        }
    }

    private subscribeToSaveResponse(result: Observable<Temperature>) {
        result.subscribe((res: Temperature) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Temperature) {
        this.eventManager.broadcast({ name: 'temperatureListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInspirationById(index: number, item: Inspiration) {
        return item.id;
    }

    trackLookById(index: number, item: Look) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-temperature-popup',
    template: ''
})
export class TemperaturePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private temperaturePopupService: TemperaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.temperaturePopupService
                    .open(TemperatureDialogComponent as Component, params['id']);
            } else {
                this.temperaturePopupService
                    .open(TemperatureDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
