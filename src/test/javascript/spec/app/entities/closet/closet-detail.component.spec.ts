/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClosetDetailComponent } from '../../../../../../main/webapp/app/entities/closet/closet-detail.component';
import { ClosetService } from '../../../../../../main/webapp/app/entities/closet/closet.service';
import { Closet } from '../../../../../../main/webapp/app/entities/closet/closet.model';

describe('Component Tests', () => {

    describe('Closet Management Detail Component', () => {
        let comp: ClosetDetailComponent;
        let fixture: ComponentFixture<ClosetDetailComponent>;
        let service: ClosetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [ClosetDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClosetService,
                    JhiEventManager
                ]
            }).overrideTemplate(ClosetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClosetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClosetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Closet(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.closet).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
