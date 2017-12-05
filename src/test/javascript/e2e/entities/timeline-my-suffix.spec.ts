import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Timeline e2e test', () => {

    let navBarPage: NavBarPage;
    let timelineDialogPage: TimelineDialogPage;
    let timelineComponentsPage: TimelineComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Timelines', () => {
        navBarPage.goToEntity('timeline-my-suffix');
        timelineComponentsPage = new TimelineComponentsPage();
        expect(timelineComponentsPage.getTitle()).toMatch(/lookbrbackendApp.timeline.home.title/);

    });

    it('should load create Timeline dialog', () => {
        timelineComponentsPage.clickOnCreateButton();
        timelineDialogPage = new TimelineDialogPage();
        expect(timelineDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.timeline.home.createOrEditLabel/);
        timelineDialogPage.close();
    });

    it('should create and save Timelines', () => {
        timelineComponentsPage.clickOnCreateButton();
        timelineDialogPage.setPageInput('5');
        expect(timelineDialogPage.getPageInput()).toMatch('5');
        timelineDialogPage.save();
        expect(timelineDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TimelineComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-timeline-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TimelineDialogPage {
    modalTitle = element(by.css('h4#myTimelineLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    pageInput = element(by.css('input#field_page'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setPageInput = function (page) {
        this.pageInput.sendKeys(page);
    }

    getPageInput = function () {
        return this.pageInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
