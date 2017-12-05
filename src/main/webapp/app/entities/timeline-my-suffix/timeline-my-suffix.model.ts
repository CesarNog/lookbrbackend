import { BaseEntity } from './../../shared';

export class TimelineMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public page?: number,
    ) {
    }
}
