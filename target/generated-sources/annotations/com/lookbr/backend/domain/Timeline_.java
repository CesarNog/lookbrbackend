package com.lookbr.backend.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Timeline.class)
public abstract class Timeline_ {

	public static volatile SetAttribute<Timeline, Inspiration> inspirations;
	public static volatile SingularAttribute<Timeline, Long> id;
	public static volatile SingularAttribute<Timeline, Integer> page;

}

