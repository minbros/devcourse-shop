package grepp.shop.settlement.infrastructure;


import grepp.shop.settlement.domain.SellerSettlement;
import grepp.shop.settlement.domain.SellerSettlementRepository;
import grepp.shop.settlement.domain.SellerSettlementStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SellerSettlementRepositoryAdapter implements SellerSettlementRepository {
    private final SellerSettlementJpaRepository sellerSettlementJpaRepository;

    @Override
    public List<SellerSettlement> findByStatusAndSellerId(SellerSettlementStatus status, UUID sellerId) {
        return sellerSettlementJpaRepository.findByStatusAndSellerId(status, sellerId);
    }

    @Override
    public List<SellerSettlement> findByStatus(SellerSettlementStatus status) {
        return sellerSettlementJpaRepository.findByStatus(status);
    }

    @Override
    public void saveAll(Iterable<SellerSettlement> sellerSettlements) {
        sellerSettlementJpaRepository.saveAll(sellerSettlements);
    }
}
