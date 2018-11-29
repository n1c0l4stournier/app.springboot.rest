package fr.treeptik.app.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import fr.treeptik.app.model.Tag;
import fr.treeptik.app.resource.TagResource;

@Component
public class TagResourceAssembler extends ResourceAssemblerSupport<Tag, TagResource> {

	@Autowired
	private ModelMapper mapper;

	public TagResourceAssembler() {
		super(TagController.class, TagResource.class);
	}

	@Override
	public TagResource toResource(Tag entity) {
		TagResource resource = createResourceWithId(entity.getId(), entity);
		mapper.map(entity, resource);
		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(TagController.class).list())
				.withRel("tags"));
		return resource;
	}

}
