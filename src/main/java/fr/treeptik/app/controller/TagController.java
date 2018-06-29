package fr.treeptik.app.controller;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.treeptik.app.model.Tag;
import fr.treeptik.app.repository.TagRepository;
import fr.treeptik.app.resource.TagResource;

@RestController
@RequestMapping("/tags")
public class TagController {
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private TagRepository repository;
	
	@Autowired
	private TagResourceAssembler assembler;

	@GetMapping
	@ResponseBody
	public ResponseEntity<Resources<TagResource>> list() {
		// test 
		Resources<TagResource> resources = new Resources<>(assembler.toResources(repository.findAll()));
		resources.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(TagController.class).list())
				.withSelfRel());
		return ResponseEntity.ok(resources);
	}
	
	@GetMapping(value = "/{id}")
	@ResponseBody
	public ResponseEntity<TagResource> find(@PathVariable UUID id) {
		Optional<Tag> editor = repository.findById(id);
		if (!editor.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			TagResource resource = assembler.toResource(editor.get());
			return ResponseEntity.ok(resource);
		}
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<TagResource> create(@RequestBody TagResource request) {
		Tag model = mapper.map(request, Tag.class);
		Tag result = repository.save(model);
		TagResource resource = mapper.map(result, TagResource.class);
		return ResponseEntity.created(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(TagController.class).find(result.getId()))
				.toUri()).body(resource);
	}

	@PutMapping(value = "/{id}")
	@ResponseBody
	public ResponseEntity<TagResource> update(@PathVariable UUID id, @RequestBody TagResource request) {
		Optional<Tag> editor = repository.findById(id);
		if (!editor.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			Tag model = mapper.map(request, Tag.class);
			model.setId(id);
			Tag result = repository.save(model);
			TagResource resource = mapper.map(result, TagResource.class);
			return ResponseEntity.ok(resource);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseBody
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		Optional<Tag> editor = repository.findById(id);
		if (!editor.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			repository.delete(editor.get());
			return ResponseEntity.noContent().build();
		}
	}

}
