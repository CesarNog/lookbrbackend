import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Occasion } from './occasion.model';
import { OccasionPopupService } from './occasion-popup.service';
import { OccasionService } from './occasion.service';
import { Inspiration, InspirationService } from '../inspiration';
import { Look, LookService } from '../look';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-occasion-dialog',
    templateUrl: './occasion-dialog.component.html'
})
export class OccasionDialogComponent implements OnInit {

    occasion: Occasion;
    isSaving: boolean;

    inspirations: Inspiration[];

    looks: Look[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private occasionService: OccasionService,
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
        if (this.occasion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.occasionService.update(this.occasion));
        } else {
            this.subscribeToSaveResponse(
                this.occasionService.create(this.occasion));
        }
    }

    private subscribeToSaveResponse(result: Observable<Occasion>) {
        result.subscribe((res: Occasion) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Occasion) {
        this.eventManager.broadcast({ name: 'occasionListModification', content: 'OK'});
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
    selector: 'jhi-occasion-popup',
    template: ''
})
export class OccasionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private occasionPopupService: OccasionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.occasionPopupService
                    .open(OccasionDialogComponent as Component, params['id']);
            } else {
                this.occasionPopupService
                    .open(OccasionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
