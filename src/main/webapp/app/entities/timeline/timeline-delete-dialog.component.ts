import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Timeline } from './timeline.model';
import { TimelinePopupService } from './timeline-popup.service';
import { TimelineService } from './timeline.service';

@Component({
    selector: 'jhi-timeline-delete-dialog',
    templateUrl: './timeline-delete-dialog.component.html'
})
export class TimelineDeleteDialogComponent {

    timeline: Timeline;

    constructor(
        private timelineService: TimelineService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timelineService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'timelineListModification',
                content: 'Deleted an timeline'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-timeline-delete-popup',
    template: ''
})
export class TimelineDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timelinePopupService: TimelinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.timelinePopupService
                .open(TimelineDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
