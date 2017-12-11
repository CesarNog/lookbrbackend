import { BaseEntity } from './../../shared';

export class ConsultantVote implements BaseEntity {
    constructor(
        public id?: number,
        public consultantProfilePhotoUrl?: string,
        public lookId?: number,
    ) {
    }
}
