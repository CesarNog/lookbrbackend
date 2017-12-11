import { BaseEntity } from './../../shared';

export class Comment implements BaseEntity {
    constructor(
        public id?: number,
        public comment?: string,
        public consultantProfilePhoto?: string,
        public consultantName?: string,
        public dateVoted?: any,
        public lookId?: number,
    ) {
    }
}
