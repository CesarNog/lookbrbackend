/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LookDetailComponent } from '../../../../../../main/webapp/app/entities/look/look-detail.component';
import { LookService } from '../../../../../../main/webapp/app/entities/look/look.service';
import { Look } from '../../../../../../main/webapp/app/entities/look/look.model';

describe('Component Tests', () => {

    describe('Look Management Detail Component', () => {
        let comp: LookDetailComponent;
        let fixture: ComponentFixture<LookDetailComponent>;
        let service: LookService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [LookDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LookService,
                    JhiEventManager
                ]
            }).overrideTemplate(LookDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LookDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LookService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Look(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.look).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
