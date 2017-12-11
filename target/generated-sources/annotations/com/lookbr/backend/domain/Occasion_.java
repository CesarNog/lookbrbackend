package com.lookbr.backend.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Occasion.class)
public abstract class Occasion_ {

	public static volatile SingularAttribute<Occasion, Long> id;
	public static volatile SingularAttribute<Occasion, Integer> page;
	public static volatile SingularAttribute<Occasion, Inspiration> inspiration;
	public static volatile SingularAttribute<Occasion, Look> look;

}

