package grepp.shop.settlement.domain;

import java.util.List;
import java.util.UUID;

public interface SellerSettlementRepository {
    void saveAll(Iterable<SellerSettlement> sellerSettlements);

    List<SellerSettlement> findByStatus(SellerSettlementStatus status);

    List<SellerSettlement> findByStatusAndSellerId(SellerSettlementStatus status, UUID sellerId);
}
