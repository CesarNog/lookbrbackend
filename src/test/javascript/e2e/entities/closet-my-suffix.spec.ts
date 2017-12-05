import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Closet e2e test', () => {

    let navBarPage: NavBarPage;
    let closetDialogPage: ClosetDialogPage;
    let closetComponentsPage: ClosetComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Closets', () => {
        navBarPage.goToEntity('closet-my-suffix');
        closetComponentsPage = new ClosetComponentsPage();
        expect(closetComponentsPage.getTitle()).toMatch(/lookbrbackendApp.closet.home.title/);

    });

    it('should load create Closet dialog', () => {
        closetComponentsPage.clickOnCreateButton();
        closetDialogPage = new ClosetDialogPage();
        expect(closetDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.closet.home.createOrEditLabel/);
        closetDialogPage.close();
    });

    it('should create and save Closets', () => {
        closetComponentsPage.clickOnCreateButton();
        closetDialogPage.setPageInput('5');
        expect(closetDialogPage.getPageInput()).toMatch('5');
        closetDialogPage.save();
        expect(closetDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ClosetComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-closet-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ClosetDialogPage {
    modalTitle = element(by.css('h4#myClosetLabel'));
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
