import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DayTime } from './day-time.model';
import { DayTimePopupService } from './day-time-popup.service';
import { DayTimeService } from './day-time.service';

@Component({
    selector: 'jhi-day-time-delete-dialog',
    templateUrl: './day-time-delete-dialog.component.html'
})
export class DayTimeDeleteDialogComponent {

    dayTime: DayTime;

    constructor(
        private dayTimeService: DayTimeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dayTimeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dayTimeListModification',
                content: 'Deleted an dayTime'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-day-time-delete-popup',
    template: ''
})
export class DayTimeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dayTimePopupService: DayTimePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dayTimePopupService
                .open(DayTimeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
