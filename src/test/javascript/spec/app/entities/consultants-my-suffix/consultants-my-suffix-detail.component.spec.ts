/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConsultantsMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/consultants-my-suffix/consultants-my-suffix-detail.component';
import { ConsultantsMySuffixService } from '../../../../../../main/webapp/app/entities/consultants-my-suffix/consultants-my-suffix.service';
import { ConsultantsMySuffix } from '../../../../../../main/webapp/app/entities/consultants-my-suffix/consultants-my-suffix.model';

describe('Component Tests', () => {

    describe('ConsultantsMySuffix Management Detail Component', () => {
        let comp: ConsultantsMySuffixDetailComponent;
        let fixture: ComponentFixture<ConsultantsMySuffixDetailComponent>;
        let service: ConsultantsMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [ConsultantsMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConsultantsMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConsultantsMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConsultantsMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsultantsMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ConsultantsMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.consultants).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
