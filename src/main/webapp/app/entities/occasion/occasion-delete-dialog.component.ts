import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Occasion } from './occasion.model';
import { OccasionPopupService } from './occasion-popup.service';
import { OccasionService } from './occasion.service';

@Component({
    selector: 'jhi-occasion-delete-dialog',
    templateUrl: './occasion-delete-dialog.component.html'
})
export class OccasionDeleteDialogComponent {

    occasion: Occasion;

    constructor(
        private occasionService: OccasionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.occasionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'occasionListModification',
                content: 'Deleted an occasion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-occasion-delete-popup',
    template: ''
})
export class OccasionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private occasionPopupService: OccasionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.occasionPopupService
                .open(OccasionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
