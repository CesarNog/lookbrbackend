import { BaseEntity } from './../../shared';

export class DayTime implements BaseEntity {
    constructor(
        public id?: number,
        public value?: string,
        public type?: string,
        public inspirationId?: number,
    ) {
    }
}
