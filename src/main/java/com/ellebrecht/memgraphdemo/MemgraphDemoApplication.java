package com.ellebrecht.memgraphdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EnableNeo4jRepositories
@EnableNeo4jAuditing
@SpringBootApplication
public class MemgraphDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemgraphDemoApplication.class, args);
    }

}

@Component
@Slf4j
class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private EntityRepository repo;

    @Override
    public void run(String... args) {
        log.info("Start");
        try {
            Entity e = save();
            count("save");
            find(e.getId());
            e.setName("Updated");
            save(e);
            count("update");
            save();
            count("save2");
            delete(e.getId());
            count("delete");
            findAll();
        } catch (Exception e) {
            log.error("Test error", e);
        }
        log.info("Finished");
    }

    private void count(String op) {
        try {
            log.info("Count after {}: {}", op, repo.count());
        } catch (Exception e) {
            log.error("Count error", e);
        }
    }

    private void findAll() {
        try {
            log.info("Finding all");
            List<Entity> e = repo.findAll();
            log.info("Found {}", e);
        } catch (Exception e) {
            log.error("Find error", e);
        }
    }

    private void find(Long id) {
        try {
            log.info("Finding {}", id);
            Optional<Entity> e = repo.findById(id);
            log.info("Found {}", e);
        } catch (Exception e) {
            log.error("Find error", e);
        }
    }

    private Entity save() {
        Entity e = new Entity();
        e.setName("test" + UUID.randomUUID());
        e.getProps().put("key1", "value1");
        e.getProps().put("key2", "value2");
        return save(e);
    }

    private Entity save(Entity entity) {
        try {
            log.info("Saving");
            Entity saved = repo.save(entity);
            log.info("Saved {}", saved);
            return saved;
        } catch (Exception e) {
            log.error("Save error", e);
            return null;
        }
    }

    private void delete(Long id) {
        try {
            log.info("Deleting {}", id);
            repo.deleteById(id);
            log.info("Deleted {}", id);
        } catch (Exception e) {
            log.error("Delete error", e);
        }
    }

}

@Component
class DummyAuditorAware implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        return Optional.of("default");
    }

}
