import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Timeline } from './timeline.model';
import { TimelinePopupService } from './timeline-popup.service';
import { TimelineService } from './timeline.service';

@Component({
    selector: 'jhi-timeline-dialog',
    templateUrl: './timeline-dialog.component.html'
})
export class TimelineDialogComponent implements OnInit {

    timeline: Timeline;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private timelineService: TimelineService,
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

    private subscribeToSaveResponse(result: Observable<Timeline>) {
        result.subscribe((res: Timeline) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Timeline) {
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
    selector: 'jhi-timeline-popup',
    template: ''
})
export class TimelinePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timelinePopupService: TimelinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.timelinePopupService
                    .open(TimelineDialogComponent as Component, params['id']);
            } else {
                this.timelinePopupService
                    .open(TimelineDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
