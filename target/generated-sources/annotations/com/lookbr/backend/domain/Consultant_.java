package com.lookbr.backend.domain;

import com.lookbr.backend.domain.enumeration.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Consultant.class)
public abstract class Consultant_ {

	public static volatile SingularAttribute<Consultant, String> consultantCoverPhotoURL;
	public static volatile SingularAttribute<Consultant, Integer> charge;
	public static volatile SingularAttribute<Consultant, String> consultantId;
	public static volatile SingularAttribute<Consultant, Look> look;
	public static volatile SingularAttribute<Consultant, String> consultantName;
	public static volatile SingularAttribute<Consultant, String> consultantProfilePhotoURL;
	public static volatile SingularAttribute<Consultant, String> profilePhoto;
	public static volatile SetAttribute<Consultant, SocialMedia> socialMedias;
	public static volatile SingularAttribute<Consultant, Long> id;
	public static volatile SingularAttribute<Consultant, Integer> page;
	public static volatile SingularAttribute<Consultant, String> consultantDescription;
	public static volatile SingularAttribute<Consultant, String> inspirationURL;
	public static volatile SingularAttribute<Consultant, Status> status;

}

