package fr.treeptik.app.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Relation(collectionRelation = "tags", value = "tag")
public class TagResource extends ResourceSupport {

	private String name;

}
