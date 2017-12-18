/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InspirationDetailComponent } from '../../../../../../main/webapp/app/entities/inspiration/inspiration-detail.component';
import { InspirationService } from '../../../../../../main/webapp/app/entities/inspiration/inspiration.service';
import { Inspiration } from '../../../../../../main/webapp/app/entities/inspiration/inspiration.model';

describe('Component Tests', () => {

    describe('Inspiration Management Detail Component', () => {
        let comp: InspirationDetailComponent;
        let fixture: ComponentFixture<InspirationDetailComponent>;
        let service: InspirationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [InspirationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InspirationService,
                    JhiEventManager
                ]
            }).overrideTemplate(InspirationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InspirationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InspirationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Inspiration(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.inspiration).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
