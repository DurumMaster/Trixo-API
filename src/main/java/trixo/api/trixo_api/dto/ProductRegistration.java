package trixo.api.trixo_api.dto;

import java.util.List;

import trixo.api.trixo_api.entities.Product;

public class ProductRegistration {
    private Product product;
    private List<String> images;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
