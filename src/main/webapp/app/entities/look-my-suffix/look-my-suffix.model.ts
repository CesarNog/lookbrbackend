import { BaseEntity } from './../../shared';

export class LookMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: string,
        public temperature?: string,
        public dayTime?: any,
    ) {
    }
}
