import { BaseEntity } from './../../shared';

export class ClosetMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public page?: number,
    ) {
    }
}
