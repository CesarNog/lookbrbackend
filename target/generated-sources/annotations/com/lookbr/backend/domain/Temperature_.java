package com.lookbr.backend.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Temperature.class)
public abstract class Temperature_ {

	public static volatile SingularAttribute<Temperature, Long> id;
	public static volatile SingularAttribute<Temperature, Inspiration> inspiration;
	public static volatile SingularAttribute<Temperature, String> type;
	public static volatile SingularAttribute<Temperature, String> value;
	public static volatile SingularAttribute<Temperature, Look> look;

}

