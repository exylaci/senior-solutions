package fleamarket;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AdvertisementService {
    private List<Advertisement> ads = new ArrayList<>();

    private AtomicLong idGenerator = new AtomicLong();
    private ModelMapper modelMapper;

    public AdvertisementService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<AdvertisementDTO> getAdvertisements(Optional<String> category, Optional<String> word) {
        return ads.stream()
                .filter(ad -> category.isEmpty() || ad.getLumberCategory() == LumberCategory.valueOf(category.get().toUpperCase(Locale.ROOT)))
                .filter(ad -> word.isEmpty() || ad.getText().toUpperCase().contains(word.get().toUpperCase()))
                .sorted(Comparator.comparing(Advertisement::getTimeStamp).reversed())
                .map(ad -> modelMapper.map(ad, AdvertisementDTO.class))
                .toList();
    }

    public AdvertisementDTO getAdvertisement(Long id) {
        return modelMapper.map(findAdvertisement(id), AdvertisementDTO.class);
    }

    public AdvertisementDTO addNewAdvertisement(CreateAdvertisementCommand command) {
        Advertisement ad = new Advertisement(idGenerator.incrementAndGet(), command);
        ads.add(ad);
        return modelMapper.map(ad, AdvertisementDTO.class);

    }

    public AdvertisementDTO updateAdvertisement(Long id, UpdateAdvertisementCommand command) {
        Advertisement ad = findAdvertisement(id);
        int index = ads.indexOf(ad);
        ad.setText(command.getText());
        ads.set(index, ad);
        return modelMapper.map(ad, AdvertisementDTO.class);
    }

    public void deleteAllAdvertisement() {
        ads.clear();
        idGenerator = new AtomicLong();
    }

    public void deleteAdvertisement(Long id) {
        Advertisement ad = findAdvertisement(id);
        ads.remove(ad);
    }

    public void deleteAdvertisement(Optional<LumberCategory> lumberCategory) {
        if (lumberCategory.isEmpty()) {
            for (LumberCategory category : LumberCategory.values()) {
                deleteOldestAdvertisement(category);
            }
        } else {
            deleteOldestAdvertisement(lumberCategory.get());
        }
    }

    private void deleteOldestAdvertisement(LumberCategory lumberCategory) {
        ads.stream()
                .filter(ad -> ad.getLumberCategory() == lumberCategory)
                .min(Comparator.comparing(Advertisement::getTimeStamp))
                .ifPresent(ad -> ads.remove(ad));
    }

    private Advertisement findAdvertisement(Long id) {
        return ads
                .stream()
                .filter(ad -> ad.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find this advertisement id: " + id));
    }
}