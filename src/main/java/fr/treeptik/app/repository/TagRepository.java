package fr.treeptik.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.Repository;

import fr.treeptik.app.model.Tag;

@org.springframework.stereotype.Repository
public interface TagRepository extends Repository<Tag, UUID> {

	Optional<Tag> findById(UUID id);

	List<Tag> findAll();

	Tag save(Tag editor);

	void delete(Tag editor);

}