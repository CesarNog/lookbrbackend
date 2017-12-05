import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TimelineMySuffix } from './timeline-my-suffix.model';
import { TimelineMySuffixPopupService } from './timeline-my-suffix-popup.service';
import { TimelineMySuffixService } from './timeline-my-suffix.service';

@Component({
    selector: 'jhi-timeline-my-suffix-dialog',
    templateUrl: './timeline-my-suffix-dialog.component.html'
})
export class TimelineMySuffixDialogComponent implements OnInit {

    timeline: TimelineMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private timelineService: TimelineMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.timeline.id !== undefined) {
            this.subscribeToSaveResponse(
                this.timelineService.update(this.timeline));
        } else {
            this.subscribeToSaveResponse(
                this.timelineService.create(this.timeline));
        }
    }

    private subscribeToSaveResponse(result: Observable<TimelineMySuffix>) {
        result.subscribe((res: TimelineMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TimelineMySuffix) {
        this.eventManager.broadcast({ name: 'timelineListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-timeline-my-suffix-popup',
    template: ''
})
export class TimelineMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timelinePopupService: TimelineMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.timelinePopupService
                    .open(TimelineMySuffixDialogComponent as Component, params['id']);
            } else {
                this.timelinePopupService
                    .open(TimelineMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
