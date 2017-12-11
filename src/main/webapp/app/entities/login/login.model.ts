import { BaseEntity } from './../../shared';

export const enum LoginType {
    'FACEBOOK',
    'EMAIL'
}

export class Login implements BaseEntity {
    constructor(
        public id?: number,
        public loginType?: LoginType,
        public token?: string,
        public userId?: number,
    ) {
    }
}
