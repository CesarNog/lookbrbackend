import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ClosetMySuffix } from './closet-my-suffix.model';
import { ClosetMySuffixPopupService } from './closet-my-suffix-popup.service';
import { ClosetMySuffixService } from './closet-my-suffix.service';

@Component({
    selector: 'jhi-closet-my-suffix-delete-dialog',
    templateUrl: './closet-my-suffix-delete-dialog.component.html'
})
export class ClosetMySuffixDeleteDialogComponent {

    closet: ClosetMySuffix;

    constructor(
        private closetService: ClosetMySuffixService,
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
    selector: 'jhi-closet-my-suffix-delete-popup',
    template: ''
})
export class ClosetMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private closetPopupService: ClosetMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.closetPopupService
                .open(ClosetMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
