import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Inspiration } from './inspiration.model';
import { InspirationPopupService } from './inspiration-popup.service';
import { InspirationService } from './inspiration.service';

@Component({
    selector: 'jhi-inspiration-delete-dialog',
    templateUrl: './inspiration-delete-dialog.component.html'
})
export class InspirationDeleteDialogComponent {

    inspiration: Inspiration;

    constructor(
        private inspirationService: InspirationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inspirationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inspirationListModification',
                content: 'Deleted an inspiration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inspiration-delete-popup',
    template: ''
})
export class InspirationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inspirationPopupService: InspirationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inspirationPopupService
                .open(InspirationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
