import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Inspiration } from './inspiration.model';
import { InspirationPopupService } from './inspiration-popup.service';
import { InspirationService } from './inspiration.service';
import { Timeline, TimelineService } from '../timeline';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-inspiration-dialog',
    templateUrl: './inspiration-dialog.component.html'
})
export class InspirationDialogComponent implements OnInit {

    inspiration: Inspiration;
    isSaving: boolean;

    timelines: Timeline[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private inspirationService: InspirationService,
        private timelineService: TimelineService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.timelineService.query()
            .subscribe((res: ResponseWrapper) => { this.timelines = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inspiration.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inspirationService.update(this.inspiration));
        } else {
            this.subscribeToSaveResponse(
                this.inspirationService.create(this.inspiration));
        }
    }

    private subscribeToSaveResponse(result: Observable<Inspiration>) {
        result.subscribe((res: Inspiration) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Inspiration) {
        this.eventManager.broadcast({ name: 'inspirationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTimelineById(index: number, item: Timeline) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-inspiration-popup',
    template: ''
})
export class InspirationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inspirationPopupService: InspirationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inspirationPopupService
                    .open(InspirationDialogComponent as Component, params['id']);
            } else {
                this.inspirationPopupService
                    .open(InspirationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
