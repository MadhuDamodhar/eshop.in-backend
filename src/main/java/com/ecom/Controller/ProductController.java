package com.ecom.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.Service.ProductService;
import com.ecom.Service.Imp.FileUploadImp;
import com.ecom.config.AppConstants;
import com.ecom.payload.ApiResponse;
import com.ecom.payload.ProductDto;
import com.ecom.payload.ProductResponse;

@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private FileUploadImp fileUpload;

    @Autowired
    private ProductService productService;

    @Value("${product.images.path}")
    private String imagePath;

    // Upload the product images
    @PostMapping("products/images/{productId}")
    public ResponseEntity<?> uploadImagesOfProduct(
            @PathVariable int productId,
            @RequestParam("product_images") List<MultipartFile> files) {

        System.out.println("Uploading images for product ID: " + productId);
        ProductDto product = this.productService.getProduct(productId);
        List<String> imageNames = product.getImageNames() != null ? product.getImageNames() : new ArrayList<>();

        try {
            // Upload each file and add the image name to the list
            List<String> uploadedImageNames = this.fileUpload.uploadFiles(imagePath, files);
            imageNames.addAll(uploadedImageNames); // Update image names with newly uploaded ones

            product.setImageNames(imageNames); // Update with all image names
            ProductDto updatedProduct = this.productService.updateProduct(productId, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.ACCEPTED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("message", "Files not uploaded to server"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("products/images/{imageName}")
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        String fullPath = imagePath + File.separator + imageName;
        File imageFile = new File(fullPath);

        if (!imageFile.exists()) {
            System.out.println("Image not found at path: " + fullPath);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
            return;
        }

        // Determine the file extension to set the appropriate content type
        String mimeType = Files.probeContentType(Paths.get(fullPath));
        if (mimeType == null) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Fallback to binary if unknown
        }

        System.out.println("Serving image from: " + fullPath);
        response.setContentType(mimeType);

        try (InputStream resource = new FileInputStream(fullPath);
             OutputStream outputStream = response.getOutputStream()) {
            StreamUtils.copy(resource, outputStream); // Copy the image stream to the response
        }
    }

    // Create product
    @PostMapping("categories/{categoryId}/product/")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, @PathVariable int categoryId) {
        ProductDto createdProduct = productService.createProduct(productDto, categoryId);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // View all products
    @GetMapping("viewAllProduct")
    public ProductResponse viewAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "100", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir) {

        return productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
    }

    // Get product by ID
    @GetMapping("products/{product_id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int product_id) {
        ProductDto productDto = productService.getProduct(product_id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    // Delete product
    @DeleteMapping("products/{product_id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable int product_id) {
        productService.deleteProduct(product_id);
        return new ResponseEntity<>(new ApiResponse("Item Deleted", true), HttpStatus.OK);
    }

    // Update product
    @PutMapping("product/{productId}")
    public ResponseEntity<ProductDto> update(@PathVariable int productId, @RequestBody ProductDto newproduct) {
        ProductDto updatedProduct = productService.updateProduct(productId, newproduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Find products by category
    @GetMapping("category/{categoryId}/product")
    public ProductResponse findByCategory(@PathVariable int categoryId,
                                          @RequestParam(value = "pageSize", defaultValue = "100") int pageSize,
                                          @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber) {
        return productService.getProductByCatgory(categoryId, pageSize, pageNumber);
    }

    // Search product by name
    @GetMapping("product/search/{name}")
    public ResponseEntity<List<ProductDto>> findbyName(@PathVariable String name) {
        List<ProductDto> findProduct = productService.findProduct(name);
        return new ResponseEntity<>(findProduct, HttpStatus.OK);
    }
}
