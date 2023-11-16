package com.ellebrecht.memgraphdemo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends ListCrudRepository<Entity, Long> {
}
