import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Closet } from './closet.model';
import { ClosetPopupService } from './closet-popup.service';
import { ClosetService } from './closet.service';

@Component({
    selector: 'jhi-closet-delete-dialog',
    templateUrl: './closet-delete-dialog.component.html'
})
export class ClosetDeleteDialogComponent {

    closet: Closet;

    constructor(
        private closetService: ClosetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.closetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'closetListModification',
                content: 'Deleted an closet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-closet-delete-popup',
    template: ''
})
export class ClosetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private closetPopupService: ClosetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.closetPopupService
                .open(ClosetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
