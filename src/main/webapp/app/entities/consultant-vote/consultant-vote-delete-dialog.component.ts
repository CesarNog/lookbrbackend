import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ConsultantVote } from './consultant-vote.model';
import { ConsultantVotePopupService } from './consultant-vote-popup.service';
import { ConsultantVoteService } from './consultant-vote.service';

@Component({
    selector: 'jhi-consultant-vote-delete-dialog',
    templateUrl: './consultant-vote-delete-dialog.component.html'
})
export class ConsultantVoteDeleteDialogComponent {

    consultantVote: ConsultantVote;

    constructor(
        private consultantVoteService: ConsultantVoteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.consultantVoteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'consultantVoteListModification',
                content: 'Deleted an consultantVote'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-consultant-vote-delete-popup',
    template: ''
})
export class ConsultantVoteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultantVotePopupService: ConsultantVotePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.consultantVotePopupService
                .open(ConsultantVoteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
