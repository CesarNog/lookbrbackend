package com.lookbr.backend.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DayTime.class)
public abstract class DayTime_ {

	public static volatile SingularAttribute<DayTime, Long> id;
	public static volatile SingularAttribute<DayTime, Inspiration> inspiration;
	public static volatile SingularAttribute<DayTime, String> type;
	public static volatile SingularAttribute<DayTime, String> value;

}

