import { BaseEntity } from './../../shared';

export class Look implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: string,
        public temperature?: string,
        public dayTime?: any,
        public pictureIndex?: number,
        public url?: string,
        public consultantsVotes?: BaseEntity[],
        public comments?: BaseEntity[],
        public intentions?: BaseEntity[],
        public occasions?: BaseEntity[],
        public temperatures?: BaseEntity[],
        public consultants?: BaseEntity[],
        public closetId?: number,
    ) {
    }
}
