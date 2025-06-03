package trixo.api.trixo_api.Mappers;

import trixo.api.trixo_api.dto.ProductDto;
import trixo.api.trixo_api.entities.Image;
import trixo.api.trixo_api.entities.Product;

import java.sql.Timestamp;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        return new ProductDto(
            product.getId(),
            product.getNombre(),
            product.getPrecio(),
            product.getTalla(),
            product.getFechaCreacion().toLocalDateTime(),
            product.getStock(),
            product.isActivo(),
            product.getDescripcion(),
            product.getMateriales(),
            product.getEnvio(),
            product.getUserID(),
            product.getValoracion(),
            product.getImagenes().stream().map(Image::getUrl).collect(Collectors.toList())
        );
    }

    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setNombre(dto.getNombre());
        product.setPrecio(dto.getPrecio());
        product.setTalla(dto.getTalla());
        product.setFechaCreacion(Timestamp.valueOf(dto.getFechaCreacion()));
        product.setStock(dto.getStock());
        product.setActivo(dto.isActivo());
        product.setDescripcion(dto.getDescripcion());
        product.setMateriales(dto.getMateriales());
        product.setEnvio(dto.getEnvio());
        product.setUserID(dto.getUserID());
        product.setValoracion(dto.getValoracion());
        // Las imágenes se agregan en el servicio addProduct
        return product;
    }
}
