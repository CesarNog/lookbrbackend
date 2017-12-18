/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConsultantDetailComponent } from '../../../../../../main/webapp/app/entities/consultant/consultant-detail.component';
import { ConsultantService } from '../../../../../../main/webapp/app/entities/consultant/consultant.service';
import { Consultant } from '../../../../../../main/webapp/app/entities/consultant/consultant.model';

describe('Component Tests', () => {

    describe('Consultant Management Detail Component', () => {
        let comp: ConsultantDetailComponent;
        let fixture: ComponentFixture<ConsultantDetailComponent>;
        let service: ConsultantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [ConsultantDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConsultantService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConsultantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConsultantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsultantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Consultant(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.consultant).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
