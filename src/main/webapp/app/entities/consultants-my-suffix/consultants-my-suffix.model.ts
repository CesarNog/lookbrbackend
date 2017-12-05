import { BaseEntity } from './../../shared';

export class ConsultantsMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public page?: number,
    ) {
    }
}
