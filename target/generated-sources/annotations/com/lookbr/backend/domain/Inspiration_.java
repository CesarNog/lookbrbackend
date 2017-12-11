package com.lookbr.backend.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Inspiration.class)
public abstract class Inspiration_ {

	public static volatile SingularAttribute<Inspiration, String> consultantName;
	public static volatile SingularAttribute<Inspiration, String> consultantProfilePhotoURL;
	public static volatile SetAttribute<Inspiration, Temperature> temperatures;
	public static volatile SetAttribute<Inspiration, DayTime> dayTimes;
	public static volatile SingularAttribute<Inspiration, Timeline> timeline;
	public static volatile SetAttribute<Inspiration, Occasion> occasions;
	public static volatile SetAttribute<Inspiration, Intention> intentions;
	public static volatile SingularAttribute<Inspiration, Long> id;
	public static volatile SingularAttribute<Inspiration, Integer> page;
	public static volatile SingularAttribute<Inspiration, String> inspirationURL;

}

