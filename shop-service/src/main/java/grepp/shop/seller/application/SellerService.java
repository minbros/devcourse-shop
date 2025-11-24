package grepp.shop.seller.application;

import grepp.shop.common.ResponseEntity;
import grepp.shop.seller.application.dto.SellerCommand;
import grepp.shop.seller.application.dto.SellerResponse;
import grepp.shop.seller.domain.Seller;
import grepp.shop.seller.domain.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    public ResponseEntity<List<SellerResponse>> getSellers(Pageable pageable) {
        Page<Seller> page = sellerRepository.findAll(pageable);
        List<SellerResponse> response = page.stream()
                .map(SellerResponse::from)
                .toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), page.getNumberOfElements(), response);
    }

    public ResponseEntity<SellerResponse> createSeller(SellerCommand command) {
        Seller seller = Seller.from(command);
        Seller createdSeller = sellerRepository.save(seller);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), 1, SellerResponse.from(createdSeller));
    }

    public ResponseEntity<SellerResponse> updateSeller(SellerCommand command, UUID id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found: " + id));

        seller.update(command);
        Seller updatedSeller = sellerRepository.save(seller);
        return new ResponseEntity<>(HttpStatus.OK.value(), 1, SellerResponse.from(updatedSeller));
    }

    public ResponseEntity<Void> deleteSeller(UUID uuid) {
        sellerRepository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), 0, null);
    }
}
