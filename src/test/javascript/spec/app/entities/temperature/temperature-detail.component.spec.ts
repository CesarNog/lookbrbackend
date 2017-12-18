/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TemperatureDetailComponent } from '../../../../../../main/webapp/app/entities/temperature/temperature-detail.component';
import { TemperatureService } from '../../../../../../main/webapp/app/entities/temperature/temperature.service';
import { Temperature } from '../../../../../../main/webapp/app/entities/temperature/temperature.model';

describe('Component Tests', () => {

    describe('Temperature Management Detail Component', () => {
        let comp: TemperatureDetailComponent;
        let fixture: ComponentFixture<TemperatureDetailComponent>;
        let service: TemperatureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [TemperatureDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TemperatureService,
                    JhiEventManager
                ]
            }).overrideTemplate(TemperatureDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TemperatureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemperatureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Temperature(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.temperature).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
