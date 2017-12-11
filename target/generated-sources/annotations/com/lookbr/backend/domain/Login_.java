package com.lookbr.backend.domain;

import com.lookbr.backend.domain.enumeration.LoginType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Login.class)
public abstract class Login_ {

	public static volatile SingularAttribute<Login, LoginType> loginType;
	public static volatile SingularAttribute<Login, Long> id;
	public static volatile SingularAttribute<Login, User> user;
	public static volatile SingularAttribute<Login, String> token;

}

