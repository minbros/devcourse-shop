package grepp.shop.product.infrastructure;

import grepp.shop.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface ProductJpaRepository extends JpaRepository<Product, UUID> {
}
