import { BaseEntity } from './../../shared';

export const enum LoginType {
    'FACEBOOK',
    'EMAIL'
}

export class LoginMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public loginType?: LoginType,
        public username?: string,
        public email?: string,
        public password?: string,
    ) {
    }
}
