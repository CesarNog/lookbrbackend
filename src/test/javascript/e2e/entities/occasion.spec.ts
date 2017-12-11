import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Occasion e2e test', () => {

    let navBarPage: NavBarPage;
    let occasionDialogPage: OccasionDialogPage;
    let occasionComponentsPage: OccasionComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Occasions', () => {
        navBarPage.goToEntity('occasion');
        occasionComponentsPage = new OccasionComponentsPage();
        expect(occasionComponentsPage.getTitle()).toMatch(/lookbrbackendApp.occasion.home.title/);

    });

    it('should load create Occasion dialog', () => {
        occasionComponentsPage.clickOnCreateButton();
        occasionDialogPage = new OccasionDialogPage();
        expect(occasionDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.occasion.home.createOrEditLabel/);
        occasionDialogPage.close();
    });

    it('should create and save Occasions', () => {
        occasionComponentsPage.clickOnCreateButton();
        occasionDialogPage.setPageInput('5');
        expect(occasionDialogPage.getPageInput()).toMatch('5');
        occasionDialogPage.inspirationSelectLastOption();
        occasionDialogPage.lookSelectLastOption();
        occasionDialogPage.save();
        expect(occasionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OccasionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-occasion div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OccasionDialogPage {
    modalTitle = element(by.css('h4#myOccasionLabel'));
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
