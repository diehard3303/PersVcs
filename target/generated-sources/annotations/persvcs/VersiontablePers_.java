package persvcs;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-09-22T22:43:15")
@StaticMetamodel(VersiontablePers.class)
public class VersiontablePers_ { 

    public static volatile SingularAttribute<VersiontablePers, Integer> id;
    public static volatile SingularAttribute<VersiontablePers, String> repository;
    public static volatile SingularAttribute<VersiontablePers, String> location;
    public static volatile SingularAttribute<VersiontablePers, String> filename;
    public static volatile SingularAttribute<VersiontablePers, Integer> version;

}