package com.lookbr.backend.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SocialUserConnection.class)
public abstract class SocialUserConnection_ {

	public static volatile SingularAttribute<SocialUserConnection, String> profileURL;
	public static volatile SingularAttribute<SocialUserConnection, Long> expireTime;
	public static volatile SingularAttribute<SocialUserConnection, String> providerId;
	public static volatile SingularAttribute<SocialUserConnection, String> displayName;
	public static volatile SingularAttribute<SocialUserConnection, String> imageURL;
	public static volatile SingularAttribute<SocialUserConnection, Long> rank;
	public static volatile SingularAttribute<SocialUserConnection, Long> id;
	public static volatile SingularAttribute<SocialUserConnection, String> secret;
	public static volatile SingularAttribute<SocialUserConnection, String> accessToken;
	public static volatile SingularAttribute<SocialUserConnection, String> userId;
	public static volatile SingularAttribute<SocialUserConnection, String> providerUserId;
	public static volatile SingularAttribute<SocialUserConnection, String> refreshToken;

}

