/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConsultantVoteDetailComponent } from '../../../../../../main/webapp/app/entities/consultant-vote/consultant-vote-detail.component';
import { ConsultantVoteService } from '../../../../../../main/webapp/app/entities/consultant-vote/consultant-vote.service';
import { ConsultantVote } from '../../../../../../main/webapp/app/entities/consultant-vote/consultant-vote.model';

describe('Component Tests', () => {

    describe('ConsultantVote Management Detail Component', () => {
        let comp: ConsultantVoteDetailComponent;
        let fixture: ComponentFixture<ConsultantVoteDetailComponent>;
        let service: ConsultantVoteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [ConsultantVoteDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConsultantVoteService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConsultantVoteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConsultantVoteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsultantVoteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ConsultantVote(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.consultantVote).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
