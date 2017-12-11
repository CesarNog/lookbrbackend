import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Comment e2e test', () => {

    let navBarPage: NavBarPage;
    let commentDialogPage: CommentDialogPage;
    let commentComponentsPage: CommentComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Comments', () => {
        navBarPage.goToEntity('comment');
        commentComponentsPage = new CommentComponentsPage();
        expect(commentComponentsPage.getTitle()).toMatch(/lookbrbackendApp.comment.home.title/);

    });

    it('should load create Comment dialog', () => {
        commentComponentsPage.clickOnCreateButton();
        commentDialogPage = new CommentDialogPage();
        expect(commentDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.comment.home.createOrEditLabel/);
        commentDialogPage.close();
    });

    it('should create and save Comments', () => {
        commentComponentsPage.clickOnCreateButton();
        commentDialogPage.setCommentInput('comment');
        expect(commentDialogPage.getCommentInput()).toMatch('comment');
        commentDialogPage.setConsultantProfilePhotoInput('consultantProfilePhoto');
        expect(commentDialogPage.getConsultantProfilePhotoInput()).toMatch('consultantProfilePhoto');
        commentDialogPage.setConsultantNameInput('consultantName');
        expect(commentDialogPage.getConsultantNameInput()).toMatch('consultantName');
        commentDialogPage.setDateVotedInput('2000-12-31');
        expect(commentDialogPage.getDateVotedInput()).toMatch('2000-12-31');
        commentDialogPage.lookSelectLastOption();
        commentDialogPage.save();
        expect(commentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CommentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-comment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CommentDialogPage {
    modalTitle = element(by.css('h4#myCommentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    commentInput = element(by.css('input#field_comment'));
    consultantProfilePhotoInput = element(by.css('input#field_consultantProfilePhoto'));
    consultantNameInput = element(by.css('input#field_consultantName'));
    dateVotedInput = element(by.css('input#field_dateVoted'));
    lookSelect = element(by.css('select#field_look'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCommentInput = function (comment) {
        this.commentInput.sendKeys(comment);
    }

    getCommentInput = function () {
        return this.commentInput.getAttribute('value');
    }

    setConsultantProfilePhotoInput = function (consultantProfilePhoto) {
        this.consultantProfilePhotoInput.sendKeys(consultantProfilePhoto);
    }

    getConsultantProfilePhotoInput = function () {
        return this.consultantProfilePhotoInput.getAttribute('value');
    }

    setConsultantNameInput = function (consultantName) {
        this.consultantNameInput.sendKeys(consultantName);
    }

    getConsultantNameInput = function () {
        return this.consultantNameInput.getAttribute('value');
    }

    setDateVotedInput = function (dateVoted) {
        this.dateVotedInput.sendKeys(dateVoted);
    }

    getDateVotedInput = function () {
        return this.dateVotedInput.getAttribute('value');
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
