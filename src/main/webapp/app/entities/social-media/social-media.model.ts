import { BaseEntity } from './../../shared';

export class SocialMedia implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public url?: string,
        public consultantId?: number,
    ) {
    }
}
