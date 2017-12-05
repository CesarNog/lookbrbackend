import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IntentionMySuffix } from './intention-my-suffix.model';
import { IntentionMySuffixPopupService } from './intention-my-suffix-popup.service';
import { IntentionMySuffixService } from './intention-my-suffix.service';

@Component({
    selector: 'jhi-intention-my-suffix-delete-dialog',
    templateUrl: './intention-my-suffix-delete-dialog.component.html'
})
export class IntentionMySuffixDeleteDialogComponent {

    intention: IntentionMySuffix;

    constructor(
        private intentionService: IntentionMySuffixService,
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
    selector: 'jhi-intention-my-suffix-delete-popup',
    template: ''
})
export class IntentionMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private intentionPopupService: IntentionMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.intentionPopupService
                .open(IntentionMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
