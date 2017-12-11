package com.lookbr.backend.domain;

import com.lookbr.backend.domain.enumeration.LoginType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Signup.class)
public abstract class Signup_ {

	public static volatile SingularAttribute<Signup, String> password;
	public static volatile SingularAttribute<Signup, String> profilePhoto;
	public static volatile SingularAttribute<Signup, LoginType> loginType;
	public static volatile SingularAttribute<Signup, Long> id;
	public static volatile SingularAttribute<Signup, String> email;
	public static volatile SingularAttribute<Signup, String> profilePhotoUrl;
	public static volatile SingularAttribute<Signup, String> username;
	public static volatile SingularAttribute<Signup, String> token;

}

