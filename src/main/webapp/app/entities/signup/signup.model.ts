import { BaseEntity } from './../../shared';

export const enum LoginType {
    'FACEBOOK',
    'EMAIL'
}

export class Signup implements BaseEntity {
    constructor(
        public id?: number,
        public email?: string,
        public loginType?: LoginType,
        public password?: string,
        public profilePhotoUrl?: string,
        public profilePhoto?: string,
        public username?: string,
        public token?: string,
    ) {
    }
}
