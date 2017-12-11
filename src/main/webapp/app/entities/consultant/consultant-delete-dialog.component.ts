import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Consultant } from './consultant.model';
import { ConsultantPopupService } from './consultant-popup.service';
import { ConsultantService } from './consultant.service';

@Component({
    selector: 'jhi-consultant-delete-dialog',
    templateUrl: './consultant-delete-dialog.component.html'
})
export class ConsultantDeleteDialogComponent {

    consultant: Consultant;

    constructor(
        private consultantService: ConsultantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.consultantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'consultantListModification',
                content: 'Deleted an consultant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-consultant-delete-popup',
    template: ''
})
export class ConsultantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultantPopupService: ConsultantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.consultantPopupService
                .open(ConsultantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
