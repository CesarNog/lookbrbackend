package com.lookbr.backend.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ {

	public static volatile SingularAttribute<Comment, String> consultantName;
	public static volatile SingularAttribute<Comment, String> consultantProfilePhoto;
	public static volatile SingularAttribute<Comment, LocalDate> dateVoted;
	public static volatile SingularAttribute<Comment, String> comment;
	public static volatile SingularAttribute<Comment, Long> id;
	public static volatile SingularAttribute<Comment, Look> look;

}

