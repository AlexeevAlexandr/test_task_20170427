package integration.helper;

import com.opinta.entity.*;
import com.opinta.mapper.ParcelItemMapper;
import com.opinta.mapper.ParcelMapper;
import com.opinta.service.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TestHelper {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CounterpartyService counterpartyService;
    @Autowired
    private PostcodePoolService postcodePoolService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private ParcelService parcelService;
    @Autowired
    private ParcelItemService parcelItemService;
    @Autowired
    private PostOfficeService postOfficeService;
    @Autowired
    private ParcelMapper parcelMapper;
    @Autowired
    private ParcelItemMapper parcelItemMapper;

    public PostOffice createPostOffice() {
        PostOffice postOffice = new PostOffice("Lviv post office", createAddress(), createPostcodePool());
        return postOfficeService.saveEntity(postOffice);
    }

    public void deletePostOffice(PostOffice postOffice) {
        postOfficeService.delete(postOffice.getId());
        postcodePoolService.delete(postOffice.getPostcodePool().getId());
    }

    public Shipment createShipment() {
        List<Parcel> parcelList = getListParcel();
        float price = (float) parcelList.stream().mapToDouble(e -> Float.parseFloat(e.getPrice().toString())).sum();
        Shipment shipment = new Shipment(createClient(), createClient(), DeliveryType.D2D,
                new BigDecimal(String.valueOf(price)), new BigDecimal(35.2), parcelList);
        return shipmentService.saveEntity(shipment);
    }

    public void deleteShipment(Shipment shipment) {
        shipmentService.delete(shipment.getId());
        clientService.delete(shipment.getSender().getId());
        clientService.delete(shipment.getRecipient().getId());
    }

    public Client createClient() {
        Client newClient = new Client("FOP Ivanov", "001", createAddress(), createCounterparty());
        return clientService.saveEntity(newClient);
    }

    public void deleteClient(Client client) {
        clientService.delete(client.getId());
        addressService.delete(client.getAddress().getId());
        deleteCounterpartyWithPostcodePool(client.getCounterparty());
    }

    public Address createAddress() {
        Address address = new Address("00001", "Ternopil", "Monastiriska", "Monastiriska",
                "Sadova", "51", "");
        return addressService.saveEntity(address);
    }

    public Counterparty createCounterparty() {
        Counterparty counterparty = new Counterparty("Modna kasta", createPostcodePool());
        return counterpartyService.saveEntity(counterparty);
    }

    public PostcodePool createPostcodePool() {
        return postcodePoolService.saveEntity(new PostcodePool("12345", false));
    }

    public void deleteCounterpartyWithPostcodePool(Counterparty counterparty) {
        counterpartyService.delete(counterparty.getId());
        postcodePoolService.delete(counterparty.getPostcodePool().getId());
    }

    public JSONObject getJsonObjectFromFile(String filePath) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(new FileReader(getFileFromResources(filePath)));
    }

    public String getJsonFromFile(String filePath) throws IOException, ParseException {
        return getJsonObjectFromFile(filePath).toString();
    }

    private File getFileFromResources(String path) {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(path)).getFile());
    }

    private List<Parcel> getListParcel(){
        List<ParcelItem> parcelItemList = createParcelItem();
        List<Parcel> parcelList = new ArrayList<>();
        float weight = (float) parcelItemList.stream().mapToDouble(ParcelItem::getWeight).sum();
        float price = (float) parcelItemList.stream().mapToDouble(e -> Float.parseFloat(e.getPrice().toString())).sum();
        Parcel parcel = new Parcel(weight, 1f, 1f, 1f, new BigDecimal("1"),
                new BigDecimal(String.valueOf(price)), parcelItemList);
        parcelList.add(parcelMapper.toEntity(parcelService.save(parcelMapper.toDto(parcel))));
        return parcelList;
    }

    private List<ParcelItem> createParcelItem(){
        List<ParcelItem> parcelItemList = new ArrayList<>();
        ParcelItem parcelItem = new ParcelItem("Laptop", 1f, 2f, new BigDecimal("3"));
        parcelItemList.add(parcelItemMapper.toEntity(parcelItemService.save(parcelItemMapper.toDto(parcelItem))));
        return parcelItemList;
    }
}
