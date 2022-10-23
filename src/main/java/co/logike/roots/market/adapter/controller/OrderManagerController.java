package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.OrderProductManager;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.app.components.OrderGroupingProducerExcelExporter;
import co.logike.roots.market.core.app.components.UserExcelExporter;
import co.logike.roots.market.core.app.entity.OrderGroupingProducerReport;
import co.logike.roots.market.core.app.entity.OrderReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Rest controller for the Order Manager.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2021-04-01
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager/v1/order")
@Tag(name = "Order Manager", description = "Order Manager API")
public class OrderManagerController {

    //    @GetMapping("/report/{start-date}-{end-date}")
//    @ResponseBody
//    @Operation(summary = "Return order report by dates")
    private final OrderProductManager manager;

    @Autowired
    public OrderManagerController(OrderProductManager manager) {
        this.manager = manager;
    }

    @GetMapping("/users/export/excel/{sDate}/{eDate}")
    public void exportToExcel(@PathVariable("sDate") String sDate,@PathVariable("eDate") String eDate,HttpServletResponse response ) throws IOException {

        log.info(eDate+sDate);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest("1");
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        ResponseEvent<List<OrderReport>> orderReport = manager.getOrders(sDate,eDate);
//        List<User> listUsers = service.listAll();
//
        UserExcelExporter excelExporter = new UserExcelExporter(orderReport.getData());
//
        excelExporter.export(response);
    }

    @GetMapping("/orders_grouping_producer/export/excel/{sDate}/{eDate}")
    public void exportExcelOrdersProducer(@PathVariable("sDate") String sDate,@PathVariable("eDate") String eDate,HttpServletResponse response ) throws IOException {

        log.info(eDate+sDate);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest("1");
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_producer_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        ResponseEvent<List<OrderGroupingProducerReport>> orderReport = manager.getOrdersGroupingProducer(sDate,eDate);
//        List<User> listUsers = service.listAll();
//
        OrderGroupingProducerExcelExporter excelExporter = new OrderGroupingProducerExcelExporter(orderReport.getData());
//
        excelExporter.export(response);
    }
}
