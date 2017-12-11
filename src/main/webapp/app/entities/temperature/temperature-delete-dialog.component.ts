import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Temperature } from './temperature.model';
import { TemperaturePopupService } from './temperature-popup.service';
import { TemperatureService } from './temperature.service';

@Component({
    selector: 'jhi-temperature-delete-dialog',
    templateUrl: './temperature-delete-dialog.component.html'
})
export class TemperatureDeleteDialogComponent {

    temperature: Temperature;

    constructor(
        private temperatureService: TemperatureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.temperatureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'temperatureListModification',
                content: 'Deleted an temperature'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-temperature-delete-popup',
    template: ''
})
export class TemperatureDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private temperaturePopupService: TemperaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.temperaturePopupService
                .open(TemperatureDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
