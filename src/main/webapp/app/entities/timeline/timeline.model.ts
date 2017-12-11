import { BaseEntity } from './../../shared';

export class Timeline implements BaseEntity {
    constructor(
        public id?: number,
        public page?: number,
        public inspirations?: BaseEntity[],
    ) {
    }
}
