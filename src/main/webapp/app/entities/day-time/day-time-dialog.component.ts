import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DayTime } from './day-time.model';
import { DayTimePopupService } from './day-time-popup.service';
import { DayTimeService } from './day-time.service';
import { Inspiration, InspirationService } from '../inspiration';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-day-time-dialog',
    templateUrl: './day-time-dialog.component.html'
})
export class DayTimeDialogComponent implements OnInit {

    dayTime: DayTime;
    isSaving: boolean;

    inspirations: Inspiration[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dayTimeService: DayTimeService,
        private inspirationService: InspirationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inspirationService.query()
            .subscribe((res: ResponseWrapper) => { this.inspirations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dayTime.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dayTimeService.update(this.dayTime));
        } else {
            this.subscribeToSaveResponse(
                this.dayTimeService.create(this.dayTime));
        }
    }

    private subscribeToSaveResponse(result: Observable<DayTime>) {
        result.subscribe((res: DayTime) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DayTime) {
        this.eventManager.broadcast({ name: 'dayTimeListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-day-time-popup',
    template: ''
})
export class DayTimePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dayTimePopupService: DayTimePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dayTimePopupService
                    .open(DayTimeDialogComponent as Component, params['id']);
            } else {
                this.dayTimePopupService
                    .open(DayTimeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
