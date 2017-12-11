import { BaseEntity } from './../../shared';

export class Intention implements BaseEntity {
    constructor(
        public id?: number,
        public page?: number,
        public inspirationId?: number,
        public lookId?: number,
    ) {
    }
}
