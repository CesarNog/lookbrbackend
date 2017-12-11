import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Intention } from './intention.model';
import { IntentionPopupService } from './intention-popup.service';
import { IntentionService } from './intention.service';
import { Inspiration, InspirationService } from '../inspiration';
import { Look, LookService } from '../look';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-intention-dialog',
    templateUrl: './intention-dialog.component.html'
})
export class IntentionDialogComponent implements OnInit {

    intention: Intention;
    isSaving: boolean;

    inspirations: Inspiration[];

    looks: Look[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private intentionService: IntentionService,
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
        if (this.intention.id !== undefined) {
            this.subscribeToSaveResponse(
                this.intentionService.update(this.intention));
        } else {
            this.subscribeToSaveResponse(
                this.intentionService.create(this.intention));
        }
    }

    private subscribeToSaveResponse(result: Observable<Intention>) {
        result.subscribe((res: Intention) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Intention) {
        this.eventManager.broadcast({ name: 'intentionListModification', content: 'OK'});
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
    selector: 'jhi-intention-popup',
    template: ''
})
export class IntentionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private intentionPopupService: IntentionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.intentionPopupService
                    .open(IntentionDialogComponent as Component, params['id']);
            } else {
                this.intentionPopupService
                    .open(IntentionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
