import { BaseEntity } from './../../shared';

export const enum Status {
    'VERDE',
    'AMARELO',
    'VERMELHO'
}

export class Consultant implements BaseEntity {
    constructor(
        public id?: number,
        public consultantId?: string,
        public consultantName?: string,
        public consultantDescription?: string,
        public consultantCoverPhotoURL?: string,
        public consultantProfilePhotoURL?: string,
        public charge?: number,
        public inspirationURL?: string,
        public profilePhoto?: string,
        public status?: Status,
        public page?: number,
        public socialMedias?: BaseEntity[],
        public lookId?: number,
    ) {
    }
}
