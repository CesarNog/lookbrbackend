import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Look } from './look.model';
import { LookPopupService } from './look-popup.service';
import { LookService } from './look.service';

@Component({
    selector: 'jhi-look-delete-dialog',
    templateUrl: './look-delete-dialog.component.html'
})
export class LookDeleteDialogComponent {

    look: Look;

    constructor(
        private lookService: LookService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lookService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lookListModification',
                content: 'Deleted an look'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-look-delete-popup',
    template: ''
})
export class LookDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lookPopupService: LookPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lookPopupService
                .open(LookDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
