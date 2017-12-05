import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ConsultantsMySuffix } from './consultants-my-suffix.model';
import { ConsultantsMySuffixPopupService } from './consultants-my-suffix-popup.service';
import { ConsultantsMySuffixService } from './consultants-my-suffix.service';

@Component({
    selector: 'jhi-consultants-my-suffix-delete-dialog',
    templateUrl: './consultants-my-suffix-delete-dialog.component.html'
})
export class ConsultantsMySuffixDeleteDialogComponent {

    consultants: ConsultantsMySuffix;

    constructor(
        private consultantsService: ConsultantsMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.consultantsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'consultantsListModification',
                content: 'Deleted an consultants'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-consultants-my-suffix-delete-popup',
    template: ''
})
export class ConsultantsMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultantsPopupService: ConsultantsMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.consultantsPopupService
                .open(ConsultantsMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
