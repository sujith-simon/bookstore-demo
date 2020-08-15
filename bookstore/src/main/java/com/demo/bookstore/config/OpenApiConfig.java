package com.demo.bookstore.config;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springdoc.core.SpringDocUtils;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.media.Schema;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@Component
public class OpenApiConfig {
    @PostConstruct
    public void configure() {

        Schema<Pageable> pageableSchema = new Schema<>();
        pageableSchema.setDefault(new Pageable(0, 20, Collections.emptyList()));

        SpringDocUtils.getConfig()
                .replaceWithSchema(org.springframework.data.domain.Pageable.class, pageableSchema)
                .replaceWithSchema(org.springframework.data.domain.PageRequest.class, pageableSchema);
    }
}
