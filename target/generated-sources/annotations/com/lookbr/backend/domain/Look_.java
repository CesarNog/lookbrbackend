package com.lookbr.backend.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Look.class)
public abstract class Look_ {

	public static volatile SetAttribute<Look, Comment> comments;
	public static volatile SingularAttribute<Look, Integer> pictureIndex;
	public static volatile SetAttribute<Look, ConsultantVote> consultantsVotes;
	public static volatile SingularAttribute<Look, Closet> closet;
	public static volatile SetAttribute<Look, Intention> intentions;
	public static volatile SingularAttribute<Look, String> userId;
	public static volatile SingularAttribute<Look, String> url;
	public static volatile SingularAttribute<Look, LocalDate> dayTime;
	public static volatile SetAttribute<Look, Temperature> temperatures;
	public static volatile SetAttribute<Look, Consultant> consultants;
	public static volatile SingularAttribute<Look, String> temperature;
	public static volatile SetAttribute<Look, Occasion> occasions;
	public static volatile SingularAttribute<Look, Long> id;

}

