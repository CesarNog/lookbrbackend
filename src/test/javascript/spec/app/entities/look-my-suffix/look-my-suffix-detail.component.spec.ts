/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LookMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/look-my-suffix/look-my-suffix-detail.component';
import { LookMySuffixService } from '../../../../../../main/webapp/app/entities/look-my-suffix/look-my-suffix.service';
import { LookMySuffix } from '../../../../../../main/webapp/app/entities/look-my-suffix/look-my-suffix.model';

describe('Component Tests', () => {

    describe('LookMySuffix Management Detail Component', () => {
        let comp: LookMySuffixDetailComponent;
        let fixture: ComponentFixture<LookMySuffixDetailComponent>;
        let service: LookMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [LookMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LookMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(LookMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LookMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LookMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LookMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.look).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
