import { BaseEntity } from './../../shared';

export class IntentionMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public page?: number,
    ) {
    }
}
