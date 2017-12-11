import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Temperature e2e test', () => {

    let navBarPage: NavBarPage;
    let temperatureDialogPage: TemperatureDialogPage;
    let temperatureComponentsPage: TemperatureComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Temperatures', () => {
        navBarPage.goToEntity('temperature');
        temperatureComponentsPage = new TemperatureComponentsPage();
        expect(temperatureComponentsPage.getTitle()).toMatch(/lookbrbackendApp.temperature.home.title/);

    });

    it('should load create Temperature dialog', () => {
        temperatureComponentsPage.clickOnCreateButton();
        temperatureDialogPage = new TemperatureDialogPage();
        expect(temperatureDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.temperature.home.createOrEditLabel/);
        temperatureDialogPage.close();
    });

    it('should create and save Temperatures', () => {
        temperatureComponentsPage.clickOnCreateButton();
        temperatureDialogPage.setValueInput('value');
        expect(temperatureDialogPage.getValueInput()).toMatch('value');
        temperatureDialogPage.setTypeInput('type');
        expect(temperatureDialogPage.getTypeInput()).toMatch('type');
        temperatureDialogPage.inspirationSelectLastOption();
        temperatureDialogPage.lookSelectLastOption();
        temperatureDialogPage.save();
        expect(temperatureDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TemperatureComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-temperature div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TemperatureDialogPage {
    modalTitle = element(by.css('h4#myTemperatureLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    valueInput = element(by.css('input#field_value'));
    typeInput = element(by.css('input#field_type'));
    inspirationSelect = element(by.css('select#field_inspiration'));
    lookSelect = element(by.css('select#field_look'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setValueInput = function (value) {
        this.valueInput.sendKeys(value);
    }

    getValueInput = function () {
        return this.valueInput.getAttribute('value');
    }

    setTypeInput = function (type) {
        this.typeInput.sendKeys(type);
    }

    getTypeInput = function () {
        return this.typeInput.getAttribute('value');
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
