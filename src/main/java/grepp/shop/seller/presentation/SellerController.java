package grepp.shop.seller.presentation;

import grepp.shop.common.ResponseEntity;
import grepp.shop.seller.application.SellerService;
import grepp.shop.seller.application.dto.SellerCommand;
import grepp.shop.seller.application.dto.SellerResponse;
import grepp.shop.seller.presentation.dto.SellerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/sellers")
public class SellerController {
    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<SellerResponse>> getSellers(Pageable pageable) {
        return sellerService.getSellers(pageable);
    }

    @PostMapping
    public ResponseEntity<SellerResponse> createSeller(@RequestBody SellerRequest request) {
        return sellerService.createSeller(SellerCommand.from(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<SellerResponse> updateSeller(@RequestBody SellerRequest request, @PathVariable UUID id) {
        return sellerService.updateSeller(SellerCommand.from(request), id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable UUID id) {
        return sellerService.deleteSeller(id);
    }
}
