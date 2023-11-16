package com.ellebrecht.memgraphdemo;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.HashMap;
import java.util.Map;

@Node
@Data
public class Entity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Long createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private Long lastModifiedDate;

    private String name;

    @CompositeProperty
    private Map<String, Object> props = new HashMap<>();

}
