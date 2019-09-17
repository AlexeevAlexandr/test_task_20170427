package com.opinta.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opinta.dto.ShipmentDto;
import com.opinta.entity.Client;
import com.opinta.entity.DeliveryType;
import com.opinta.entity.Parcel;
import com.opinta.entity.Shipment;
import com.opinta.mapper.ClientMapper;
import com.opinta.service.ClientService;
import com.opinta.service.PDFGeneratorService;
import com.opinta.service.ParcelService;
import com.opinta.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {
    private ShipmentService shipmentService;
    private ParcelService parcelService;
    private PDFGeneratorService pdfGeneratorService;
    private ClientService clientService;
    private ClientMapper clientMapper;

    @Autowired
    public ShipmentController(ClientMapper clientMapper, ClientService clientService, ParcelService parcelService,
                              ShipmentService shipmentService, PDFGeneratorService pdfGeneratorService) {
        this.clientMapper = clientMapper;
        this.clientService = clientService;
        this.parcelService = parcelService;
        this.shipmentService = shipmentService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<ShipmentDto> getShipments() {
        return shipmentService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getShipment(@PathVariable("id") long id) {
        ShipmentDto shipmentDto = shipmentService.getById(id);
        if (shipmentDto == null) {
            return new ResponseEntity<>(format("No Shipment found for ID %d", id), NOT_FOUND);
        }
        return new ResponseEntity<>(shipmentDto, OK);
    }

    @GetMapping("{id}/label-form")
    public ResponseEntity<?> getShipmentLabelForm(@PathVariable("id") long id) {
        byte[] data = pdfGeneratorService.generateLabel(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "labelform" + id + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(data, headers, OK);
    }

    @GetMapping("{id}/postpay-form")
    public ResponseEntity<?> getShipmentPostpayForm(@PathVariable("id") long id) {
        byte[] data = pdfGeneratorService.generatePostpay(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "postpayform" + id + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(data, headers, OK);
    }

    @PostMapping
    @ResponseStatus(OK)
    public Shipment createShipment(@RequestBody String string) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DeliveryType[] deliveryTypes = DeliveryType.values();
        Shipment shipment = new Shipment();
        String stringDeliveryType = jsonNode.get("deliveryType").toString().replaceAll("\"", "");
        for (DeliveryType d : deliveryTypes) {
            if (d.toString().equalsIgnoreCase(stringDeliveryType)){
                shipment.setDeliveryType(d);
            }
        }
        Client client = clientMapper.toEntity(clientService.getById(Long.parseLong(jsonNode.get("senderId").toString())));
        shipment.setSender(client);
        client = clientMapper.toEntity(clientService.getById(Long.parseLong(jsonNode.get("recipientId").toString())));
        shipment.setRecipient(client);
        shipment.setPostPay(new BigDecimal(jsonNode.get("postPay").toString()));
        List<Parcel> parcelList = parcelService.getAll();
        float price = (float) parcelList.stream().mapToDouble(e -> Float.parseFloat(e.getPrice().toString())).sum();
        shipment.setPrice(new BigDecimal(String.valueOf(price)));
        shipment.setParcelList(parcelList);
        return shipmentService.saveEntity(shipment);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateShipment(@PathVariable long id, @RequestBody ShipmentDto shipmentDto) {
        shipmentDto = shipmentService.update(id, shipmentDto);
        if (shipmentDto == null) {
            return new ResponseEntity<>(format("No Shipment found for ID %d", id), NOT_FOUND);
        }
        return new ResponseEntity<>(shipmentDto, OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteShipment(@PathVariable long id) {
        if (!shipmentService.delete(id)) {
            return new ResponseEntity<>(format("No Shipment found for ID %d", id), NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }
}
