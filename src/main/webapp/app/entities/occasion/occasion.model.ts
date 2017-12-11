import { BaseEntity } from './../../shared';

export class Occasion implements BaseEntity {
    constructor(
        public id?: number,
        public page?: number,
        public inspirationId?: number,
        public lookId?: number,
    ) {
    }
}
