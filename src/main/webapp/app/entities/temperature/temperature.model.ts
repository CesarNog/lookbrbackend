import { BaseEntity } from './../../shared';

export class Temperature implements BaseEntity {
    constructor(
        public id?: number,
        public value?: string,
        public type?: string,
        public inspirationId?: number,
        public lookId?: number,
    ) {
    }
}
