import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Look e2e test', () => {

    let navBarPage: NavBarPage;
    let lookDialogPage: LookDialogPage;
    let lookComponentsPage: LookComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Looks', () => {
        navBarPage.goToEntity('look-my-suffix');
        lookComponentsPage = new LookComponentsPage();
        expect(lookComponentsPage.getTitle()).toMatch(/lookbrbackendApp.look.home.title/);

    });

    it('should load create Look dialog', () => {
        lookComponentsPage.clickOnCreateButton();
        lookDialogPage = new LookDialogPage();
        expect(lookDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.look.home.createOrEditLabel/);
        lookDialogPage.close();
    });

    it('should create and save Looks', () => {
        lookComponentsPage.clickOnCreateButton();
        lookDialogPage.setUserIdInput('userId');
        expect(lookDialogPage.getUserIdInput()).toMatch('userId');
        lookDialogPage.setTemperatureInput('temperature');
        expect(lookDialogPage.getTemperatureInput()).toMatch('temperature');
        lookDialogPage.setDayTimeInput('2000-12-31');
        expect(lookDialogPage.getDayTimeInput()).toMatch('2000-12-31');
        lookDialogPage.save();
        expect(lookDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LookComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-look-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class LookDialogPage {
    modalTitle = element(by.css('h4#myLookLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userIdInput = element(by.css('input#field_userId'));
    temperatureInput = element(by.css('input#field_temperature'));
    dayTimeInput = element(by.css('input#field_dayTime'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setUserIdInput = function (userId) {
        this.userIdInput.sendKeys(userId);
    }

    getUserIdInput = function () {
        return this.userIdInput.getAttribute('value');
    }

    setTemperatureInput = function (temperature) {
        this.temperatureInput.sendKeys(temperature);
    }

    getTemperatureInput = function () {
        return this.temperatureInput.getAttribute('value');
    }

    setDayTimeInput = function (dayTime) {
        this.dayTimeInput.sendKeys(dayTime);
    }

    getDayTimeInput = function () {
        return this.dayTimeInput.getAttribute('value');
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
