import { BaseEntity } from './../../shared';

export class Closet implements BaseEntity {
    constructor(
        public id?: number,
        public page?: number,
        public looks?: BaseEntity[],
    ) {
    }
}
