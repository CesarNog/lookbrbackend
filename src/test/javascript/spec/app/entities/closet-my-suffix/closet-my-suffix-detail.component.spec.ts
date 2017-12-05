/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClosetMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/closet-my-suffix/closet-my-suffix-detail.component';
import { ClosetMySuffixService } from '../../../../../../main/webapp/app/entities/closet-my-suffix/closet-my-suffix.service';
import { ClosetMySuffix } from '../../../../../../main/webapp/app/entities/closet-my-suffix/closet-my-suffix.model';

describe('Component Tests', () => {

    describe('ClosetMySuffix Management Detail Component', () => {
        let comp: ClosetMySuffixDetailComponent;
        let fixture: ComponentFixture<ClosetMySuffixDetailComponent>;
        let service: ClosetMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [ClosetMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClosetMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ClosetMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClosetMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClosetMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClosetMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.closet).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
