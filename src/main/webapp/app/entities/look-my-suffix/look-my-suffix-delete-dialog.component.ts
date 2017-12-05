import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LookMySuffix } from './look-my-suffix.model';
import { LookMySuffixPopupService } from './look-my-suffix-popup.service';
import { LookMySuffixService } from './look-my-suffix.service';

@Component({
    selector: 'jhi-look-my-suffix-delete-dialog',
    templateUrl: './look-my-suffix-delete-dialog.component.html'
})
export class LookMySuffixDeleteDialogComponent {

    look: LookMySuffix;

    constructor(
        private lookService: LookMySuffixService,
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
    selector: 'jhi-look-my-suffix-delete-popup',
    template: ''
})
export class LookMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lookPopupService: LookMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lookPopupService
                .open(LookMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
