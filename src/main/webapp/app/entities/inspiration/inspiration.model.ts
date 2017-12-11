import { BaseEntity } from './../../shared';

export class Inspiration implements BaseEntity {
    constructor(
        public id?: number,
        public consultantName?: string,
        public consultantProfilePhotoURL?: string,
        public inspirationURL?: string,
        public page?: number,
        public timelineId?: number,
        public intentions?: BaseEntity[],
        public occasions?: BaseEntity[],
        public temperatures?: BaseEntity[],
        public dayTimes?: BaseEntity[],
    ) {
    }
}
