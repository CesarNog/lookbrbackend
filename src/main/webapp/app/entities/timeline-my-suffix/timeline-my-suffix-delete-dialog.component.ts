import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TimelineMySuffix } from './timeline-my-suffix.model';
import { TimelineMySuffixPopupService } from './timeline-my-suffix-popup.service';
import { TimelineMySuffixService } from './timeline-my-suffix.service';

@Component({
    selector: 'jhi-timeline-my-suffix-delete-dialog',
    templateUrl: './timeline-my-suffix-delete-dialog.component.html'
})
export class TimelineMySuffixDeleteDialogComponent {

    timeline: TimelineMySuffix;

    constructor(
        private timelineService: TimelineMySuffixService,
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
    selector: 'jhi-timeline-my-suffix-delete-popup',
    template: ''
})
export class TimelineMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timelinePopupService: TimelineMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.timelinePopupService
                .open(TimelineMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
