package com.lookbr.backend.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Intention.class)
public abstract class Intention_ {

	public static volatile SingularAttribute<Intention, Long> id;
	public static volatile SingularAttribute<Intention, Integer> page;
	public static volatile SingularAttribute<Intention, Inspiration> inspiration;
	public static volatile SingularAttribute<Intention, Look> look;

}

