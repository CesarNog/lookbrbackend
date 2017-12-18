import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Inspiration e2e test', () => {

    let navBarPage: NavBarPage;
    let inspirationDialogPage: InspirationDialogPage;
    let inspirationComponentsPage: InspirationComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Inspirations', () => {
        navBarPage.goToEntity('inspiration');
        inspirationComponentsPage = new InspirationComponentsPage();
        expect(inspirationComponentsPage.getTitle()).toMatch(/lookbrbackendApp.inspiration.home.title/);

    });

    it('should load create Inspiration dialog', () => {
        inspirationComponentsPage.clickOnCreateButton();
        inspirationDialogPage = new InspirationDialogPage();
        expect(inspirationDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.inspiration.home.createOrEditLabel/);
        inspirationDialogPage.close();
    });

    it('should create and save Inspirations', () => {
        inspirationComponentsPage.clickOnCreateButton();
        inspirationDialogPage.setConsultantNameInput('consultantName');
        expect(inspirationDialogPage.getConsultantNameInput()).toMatch('consultantName');
        inspirationDialogPage.setConsultantProfilePhotoURLInput('consultantProfilePhotoURL');
        expect(inspirationDialogPage.getConsultantProfilePhotoURLInput()).toMatch('consultantProfilePhotoURL');
        inspirationDialogPage.setInspirationURLInput('inspirationURL');
        expect(inspirationDialogPage.getInspirationURLInput()).toMatch('inspirationURL');
        inspirationDialogPage.setPageInput('5');
        expect(inspirationDialogPage.getPageInput()).toMatch('5');
        inspirationDialogPage.timelineSelectLastOption();
        inspirationDialogPage.save();
        expect(inspirationDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class InspirationComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-inspiration div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class InspirationDialogPage {
    modalTitle = element(by.css('h4#myInspirationLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    consultantNameInput = element(by.css('input#field_consultantName'));
    consultantProfilePhotoURLInput = element(by.css('input#field_consultantProfilePhotoURL'));
    inspirationURLInput = element(by.css('input#field_inspirationURL'));
    pageInput = element(by.css('input#field_page'));
    timelineSelect = element(by.css('select#field_timeline'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setConsultantNameInput = function (consultantName) {
        this.consultantNameInput.sendKeys(consultantName);
    }

    getConsultantNameInput = function () {
        return this.consultantNameInput.getAttribute('value');
    }

    setConsultantProfilePhotoURLInput = function (consultantProfilePhotoURL) {
        this.consultantProfilePhotoURLInput.sendKeys(consultantProfilePhotoURL);
    }

    getConsultantProfilePhotoURLInput = function () {
        return this.consultantProfilePhotoURLInput.getAttribute('value');
    }

    setInspirationURLInput = function (inspirationURL) {
        this.inspirationURLInput.sendKeys(inspirationURL);
    }

    getInspirationURLInput = function () {
        return this.inspirationURLInput.getAttribute('value');
    }

    setPageInput = function (page) {
        this.pageInput.sendKeys(page);
    }

    getPageInput = function () {
        return this.pageInput.getAttribute('value');
    }

    timelineSelectLastOption = function () {
        this.timelineSelect.all(by.tagName('option')).last().click();
    }

    timelineSelectOption = function (option) {
        this.timelineSelect.sendKeys(option);
    }

    getTimelineSelect = function () {
        return this.timelineSelect;
    }

    getTimelineSelectedOption = function () {
        return this.timelineSelect.element(by.css('option:checked')).getText();
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
