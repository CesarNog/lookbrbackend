import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('DayTime e2e test', () => {

    let navBarPage: NavBarPage;
    let dayTimeDialogPage: DayTimeDialogPage;
    let dayTimeComponentsPage: DayTimeComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DayTimes', () => {
        navBarPage.goToEntity('day-time');
        dayTimeComponentsPage = new DayTimeComponentsPage();
        expect(dayTimeComponentsPage.getTitle()).toMatch(/lookbrbackendApp.dayTime.home.title/);

    });

    it('should load create DayTime dialog', () => {
        dayTimeComponentsPage.clickOnCreateButton();
        dayTimeDialogPage = new DayTimeDialogPage();
        expect(dayTimeDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.dayTime.home.createOrEditLabel/);
        dayTimeDialogPage.close();
    });

    it('should create and save DayTimes', () => {
        dayTimeComponentsPage.clickOnCreateButton();
        dayTimeDialogPage.setValueInput('value');
        expect(dayTimeDialogPage.getValueInput()).toMatch('value');
        dayTimeDialogPage.setTypeInput('type');
        expect(dayTimeDialogPage.getTypeInput()).toMatch('type');
        dayTimeDialogPage.inspirationSelectLastOption();
        dayTimeDialogPage.save();
        expect(dayTimeDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DayTimeComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-day-time div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DayTimeDialogPage {
    modalTitle = element(by.css('h4#myDayTimeLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    valueInput = element(by.css('input#field_value'));
    typeInput = element(by.css('input#field_type'));
    inspirationSelect = element(by.css('select#field_inspiration'));

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
