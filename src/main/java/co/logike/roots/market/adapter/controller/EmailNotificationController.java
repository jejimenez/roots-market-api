/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.EmailNotificationManager;
import co.logike.roots.market.core.api.objects.EmailNotificationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for the DeliveryCycle.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/market/v1/send_notification")
@Tag(name = "emailNotification", description = "Email Notification API")
public class EmailNotificationController {

  //  private final EmailNotificationManager manager;



//    @PostMapping
//    @ResponseBody
//    @Operation(summary = "Return all delivery cycles")
//    public ResponseEntity<ResponseEvent<Boolean>> sendNotification(@RequestBody EmailNotificationDTO email) {
//        //log.debug("method: readAll()");
//        //final Responseent<Boolean> responseEvent = manager.sendNotification(email);
//        //log.debug("method: readAll() -> {}", responseEvent.getMessage());
//        return ResponseEntityUtility.buildHttpResponse(responseEvent);
//    }


}
