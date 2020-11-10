/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.PersonManager;
import co.logike.roots.market.core.api.objects.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for the Person.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/market/v1/person")
@Tag(name = "person", description = "Person API")
public class PersonController {

    private final PersonManager manager;

    @Autowired
    public PersonController(PersonManager manager) {
        this.manager = manager;
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Return all persons")
    public ResponseEntity<ResponseEvent<List<PersonDTO>>> readAll() {
        log.debug("method: readAll()");
        final ResponseEvent<List<PersonDTO>> responseEvent = manager.readAll();
        log.debug("method: readAll() -> {}", responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Return person by id")
    public ResponseEntity<ResponseEvent<PersonDTO>> read(@PathVariable("id") String id) {
        log.debug("method: read({})", id);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(id);
        final ResponseEvent<PersonDTO> responseEvent = manager.read(readEvent);
        log.debug("method: read({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }
    
    @PostMapping("/login")
    @ResponseBody
    @Operation(summary = "Return person by email and password")
    public ResponseEntity<ResponseEvent<PersonDTO>> login(@RequestBody PersonDTO domain) {
        log.debug("method: create({})", domain);
        final CommandEvent<PersonDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<PersonDTO> responseEvent = manager.login(requestEvent);
        log.debug("method: create({}) -> {}", domain, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @GetMapping(value="", params="email")
    @ResponseBody
    @Operation(summary = "Return person by email")
    public ResponseEntity<ResponseEvent<PersonDTO>> readByEmail(@RequestParam("email") String email) {
        log.debug("method: read({})", email);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(email);
        final ResponseEvent<PersonDTO> responseEvent = manager.readByEmail(readEvent);
        log.debug("method: read({}) -> {}", email, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create person")
    public ResponseEntity<ResponseEvent<PersonDTO>> create(@RequestBody PersonDTO domain) {
        log.debug("method: create({})", domain);
        final CommandEvent<PersonDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<PersonDTO> responseEvent = manager.create(requestEvent);
        log.debug("method: create({}) -> {}", domain, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Update person by id")
    public ResponseEntity<ResponseEvent<PersonDTO>> update(@PathVariable("id") String id, @RequestBody PersonDTO domain) {
        log.debug("method: update id: {}, ({})", id, domain);
        final CommandEvent<PersonDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<PersonDTO> responseEvent = manager.update(requestEvent, id);
        log.debug("method: update({}) -> {}", domain, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Delete person by id")
    public ResponseEntity<ResponseEvent<String>> delete(@PathVariable("id") String id) {
        log.debug("method: deleteDomain({})", id);
        CommandEvent<String> commandEvent = new CommandEvent<>();
        commandEvent.setRequest(id);
        final ResponseEvent<String> responseEvent = manager.delete(commandEvent);
        log.debug("method: deleteDomain({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }
}
