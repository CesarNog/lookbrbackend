import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Intention } from './intention.model';
import { IntentionPopupService } from './intention-popup.service';
import { IntentionService } from './intention.service';

@Component({
    selector: 'jhi-intention-delete-dialog',
    templateUrl: './intention-delete-dialog.component.html'
})
export class IntentionDeleteDialogComponent {

    intention: Intention;

    constructor(
        private intentionService: IntentionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.intentionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'intentionListModification',
                content: 'Deleted an intention'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-intention-delete-popup',
    template: ''
})
export class IntentionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private intentionPopupService: IntentionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.intentionPopupService
                .open(IntentionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
