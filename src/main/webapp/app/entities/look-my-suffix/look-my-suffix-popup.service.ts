import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LookMySuffix } from './look-my-suffix.model';
import { LookMySuffixService } from './look-my-suffix.service';

@Injectable()
export class LookMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private lookService: LookMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.lookService.find(id).subscribe((look) => {
                    if (look.dayTime) {
                        look.dayTime = {
                            year: look.dayTime.getFullYear(),
                            month: look.dayTime.getMonth() + 1,
                            day: look.dayTime.getDate()
                        };
                    }
                    this.ngbModalRef = this.lookModalRef(component, look);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.lookModalRef(component, new LookMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    lookModalRef(component: Component, look: LookMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.look = look;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
