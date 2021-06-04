/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.ProductManager;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.app.entity.Category;
import co.logike.roots.market.core.app.entity.PersonRoleOrganization;
import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.entity.Unit;
import co.logike.roots.market.core.app.parser.ProductParser;
import co.logike.roots.market.core.app.repository.CategoryRepository;
import co.logike.roots.market.core.app.repository.PersonRoleOrganizationRepository;
import co.logike.roots.market.core.app.repository.ProductRepository;
import co.logike.roots.market.core.app.repository.UnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for {@link ProductManager}.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Service
@Slf4j
public class ProductManagerHandler implements ProductManager {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final UnitRepository unitRepository;
    private final PersonRoleOrganizationRepository personRoleOrganizationRepository;

    @Autowired
    public ProductManagerHandler(ProductRepository repository, CategoryRepository categoryRepository,
                                 UnitRepository unitRepository, PersonRoleOrganizationRepository personRoleOrganizationRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.unitRepository = unitRepository;
        this.personRoleOrganizationRepository = personRoleOrganizationRepository;
    }

    @Override
    public ResponseEvent<List<ProductDTO>> readAll() {
        log.debug("method: readAll()");
        try {
            List<ProductDTO> finalList = new ArrayList<>();
            List<Product> productList = repository.findAll();
            for (Product product : productList) {
                ProductDTO productDTO = ProductParser.setProductDTO(product);
                finalList.add(productDTO);
            }
            return new ResponseEvent<List<ProductDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<ProductDTO>>().conflict(ex.getMessage());
        }
    }


    @Override
    public ResponseEvent<List<ProductDTO>> readAllOrderBy(String orderBy) {
        log.debug("method: readAll()");
        try {
            List<ProductDTO> finalList = new ArrayList<>();
            List<Product> productList = repository.findAll(Sort.by(Sort.Direction.ASC, orderBy));
            for (Product product : productList) {
                ProductDTO productDTO = ProductParser.setProductDTO(product);
                finalList.add(productDTO);
            }
            return new ResponseEvent<List<ProductDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<ProductDTO>>().conflict(ex.getMessage());
        }
    }



    @Override
    public ResponseEvent<List<ProductDTO>> readByPurchaseOrder(QueryPKEvent<String> requestEvent) {
        log.debug("method: readByPurchaseOrder()");
        try {
            if (requestEvent == null)
                return new ResponseEvent<List<ProductDTO>>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<List<ProductDTO>>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            List<ProductDTO> finalList = new ArrayList<>();
            List<Product> productList = repository.findByPurchaseOrder(idL);
            for (Product product : productList) {
                ProductDTO productDTO = ProductParser.setProductDTO(product);
                finalList.add(productDTO);
            }
            return new ResponseEvent<List<ProductDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<ProductDTO>>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<ProductDTO> read(QueryPKEvent<String> requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<ProductDTO>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<ProductDTO>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            Product product = repository.findByIdent(idL);
            ProductDTO productDTO = ProductParser.setProductDTO(product);
            return new ResponseEvent<ProductDTO>().ok("Success", productDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<ProductDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<ProductDTO> create(CommandEvent<ProductDTO> requestEvent) {
        log.info("method: create({})", requestEvent);
        try {
            if (requestEvent == null) {
                return new ResponseEvent<ProductDTO>().badRequest("event is null.");
            }
            ProductDTO product = requestEvent.getRequest();
            Category category = categoryRepository.findByIdent(Long.valueOf(product.getCategory()));
            Unit unit = unitRepository.findByIdent(Long.valueOf(product.getUnit()));
            PersonRoleOrganization personRoleOrganization = personRoleOrganizationRepository.findByIdent(Long.valueOf(product.getProducer()));

            Product entity = ProductParser.setProduct(product, category, unit, personRoleOrganization);
            repository.save(entity);
            repository.flush();
            product.setId(entity.getId().toString());
            return new ResponseEvent<ProductDTO>().ok("Success", product);
        } catch (Exception ex) {
            log.error("method: create({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<ProductDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<ProductDTO> update(CommandEvent<ProductDTO> requestEvent, String id) {
        log.info("method: update ({},{})", requestEvent, id);
        try {
            if (requestEvent != null) {
                ProductDTO product = requestEvent.getRequest();
                Product productToUpdate = repository.findByIdent(Long.valueOf(id));
                Category category = categoryRepository.findByIdent(Long.valueOf(product.getCategory()));
                Unit unit = unitRepository.findByIdent(Long.valueOf(product.getUnit()));
                PersonRoleOrganization personRoleOrganization = personRoleOrganizationRepository.findByIdent(Long.valueOf(product.getProducer()));

                ProductParser.setProduct(productToUpdate, product, category, unit, personRoleOrganization);
                repository.save(productToUpdate);
                return new ResponseEvent<ProductDTO>().ok("Success", product);
            } else {
                return new ResponseEvent<ProductDTO>().badRequest("event is null.");
            }
        } catch (Exception ex) {
            log.error("method: update({},{})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<ProductDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<String> delete(CommandEvent<String> requestEvent) {
        log.info("method: delete ({})", requestEvent);
        try {
            if (requestEvent != null) {
                final String productId = requestEvent.getRequest();
                Long ident = Long.valueOf(productId);
                Product productToDelete = repository.findByIdent(ident);
                repository.delete(productToDelete);
                return new ResponseEvent<String>().ok("Success", productId);
            } else {
                return new ResponseEvent<String>().badRequest("event is null.");
            }
        } catch (Exception ex) {
            log.error("method: delete({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<String>().conflict(ex.getMessage());
        }
    }

}
