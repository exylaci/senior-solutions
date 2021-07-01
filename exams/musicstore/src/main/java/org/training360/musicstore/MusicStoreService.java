package org.training360.musicstore;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MusicStoreService {
    private List<Instrument> instruments = Collections.synchronizedList(new ArrayList<>());
    private AtomicLong idGenerator = new AtomicLong();
    private ModelMapper modelMapper;

    public MusicStoreService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public InstrumentDTO getInstrument(long id) {
        return modelMapper.map(findInstrument(id), InstrumentDTO.class);
    }

    public List<InstrumentDTO> getInstruments(Optional<String> brand, Optional<Integer> price) {
        List<InstrumentDTO> instrumentsDTO = instruments
                .stream()
                .filter(instrument -> brand.isEmpty() || instrument.getBrand().equalsIgnoreCase(brand.get()))
                .filter(instrument -> price.isEmpty() || instrument.getPrice() == price.get())
                .map(instrument -> modelMapper.map(instrument, InstrumentDTO.class))
                .toList();

        if (instrumentsDTO.isEmpty()) {
            throw new IllegalArgumentException("Can't find instrument!");
        }
        return instrumentsDTO;
    }

    public InstrumentDTO createInstrument(CreateInstrumentCommand command) {
        Instrument instrument = new Instrument(idGenerator.incrementAndGet(), command);
        instruments.add(instrument);
        return modelMapper.map(instrument, InstrumentDTO.class);
    }


    public void deleteAllInstrument() {
        instruments = Collections.synchronizedList(new ArrayList<>());
        idGenerator = new AtomicLong();
    }

    public void deleteInstrument(long id) {
        Instrument instrument = findInstrument(id);
        instruments.remove(instrument);
    }

    private Instrument findInstrument(long id) {
        return instruments.stream().filter(instrument -> instrument.getId() == id).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Instrument with " + id + " id doesn't exists!"));
    }

    public void updateInstrumentPrice(long id, UpdatePriceCommand command) {
        Instrument instrument = findInstrument(id);
        if (instrument.getPrice() != command.getPrice()) {
            instrument.setPrice(command.getPrice());
            instrument.setPostDate();
        }
    }
}
