import { BaseEntity } from './../../shared';

export const enum LoginType {
    'FACEBOOK',
    'EMAIL'
}

export class SignupMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public loginType?: LoginType,
        public email?: string,
        public profilePhotoUrl?: string,
        public profilePhoto?: string,
        public username?: string,
        public password?: string,
    ) {
    }
}
