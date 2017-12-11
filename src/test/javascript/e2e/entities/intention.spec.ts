import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Intention e2e test', () => {

    let navBarPage: NavBarPage;
    let intentionDialogPage: IntentionDialogPage;
    let intentionComponentsPage: IntentionComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Intentions', () => {
        navBarPage.goToEntity('intention');
        intentionComponentsPage = new IntentionComponentsPage();
        expect(intentionComponentsPage.getTitle()).toMatch(/lookbrbackendApp.intention.home.title/);

    });

    it('should load create Intention dialog', () => {
        intentionComponentsPage.clickOnCreateButton();
        intentionDialogPage = new IntentionDialogPage();
        expect(intentionDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.intention.home.createOrEditLabel/);
        intentionDialogPage.close();
    });

    it('should create and save Intentions', () => {
        intentionComponentsPage.clickOnCreateButton();
        intentionDialogPage.setPageInput('5');
        expect(intentionDialogPage.getPageInput()).toMatch('5');
        intentionDialogPage.inspirationSelectLastOption();
        intentionDialogPage.lookSelectLastOption();
        intentionDialogPage.save();
        expect(intentionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IntentionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-intention div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IntentionDialogPage {
    modalTitle = element(by.css('h4#myIntentionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    pageInput = element(by.css('input#field_page'));
    inspirationSelect = element(by.css('select#field_inspiration'));
    lookSelect = element(by.css('select#field_look'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setPageInput = function (page) {
        this.pageInput.sendKeys(page);
    }

    getPageInput = function () {
        return this.pageInput.getAttribute('value');
    }

    inspirationSelectLastOption = function () {
        this.inspirationSelect.all(by.tagName('option')).last().click();
    }

    inspirationSelectOption = function (option) {
        this.inspirationSelect.sendKeys(option);
    }

    getInspirationSelect = function () {
        return this.inspirationSelect;
    }

    getInspirationSelectedOption = function () {
        return this.inspirationSelect.element(by.css('option:checked')).getText();
    }

    lookSelectLastOption = function () {
        this.lookSelect.all(by.tagName('option')).last().click();
    }

    lookSelectOption = function (option) {
        this.lookSelect.sendKeys(option);
    }

    getLookSelect = function () {
        return this.lookSelect;
    }

    getLookSelectedOption = function () {
        return this.lookSelect.element(by.css('option:checked')).getText();
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
