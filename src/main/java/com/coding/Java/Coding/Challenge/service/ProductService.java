package com.coding.Java.Coding.Challenge.service;

import com.coding.Java.Coding.Challenge.entity.ApprovalQueue;
import com.coding.Java.Coding.Challenge.entity.Product;
import com.coding.Java.Coding.Challenge.repository.ApprovalQueueRepository;
import com.coding.Java.Coding.Challenge.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ApprovalQueueRepository approvalQueueRepository;

    public void createProduct(@RequestBody Product product) {
        try {
            if (product != null) {
                if (product.getPrice() > 10000)
                    throw new IllegalArgumentException("Product Price cannot be greater than $10000");
                product.setPostedDate(new Date());
                if (product.getStatus() == null)
                    product.setStatus("Active");
                productRepository.save(product);

                if (product.getPrice() > 5000) {
                    ApprovalQueue approvalQueue = new ApprovalQueue();
                    approvalQueue.setRequestDate(new Date());
                    approvalQueue.setDeleted(false);
                    approvalQueue.setApproved(false);
                    approvalQueue.setState("Pending");
                    approvalQueue.setProductId(product.getId());
                    approvalQueueRepository.save(approvalQueue);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public void updateProduct(Product product) {
        try {
            Product existingProduct = null;
            if (product != null) {
                existingProduct = fetchProductById(product.getId());

            }

            if (existingProduct != null) {
                ApprovalQueue approvalQueue = null;
                if (product.getPrice() > (existingProduct.getPrice() * 1.5)) {
                    approvalQueue = fetchApprovalQueueByProductId(product.getId());
                    if (approvalQueue == null)
                        approvalQueue = new ApprovalQueue();
                    approvalQueue.setRequestDate(new Date());
                    approvalQueue.setDeleted(false);
                    approvalQueue.setApproved(false);
                    approvalQueue.setState("Pending");
                    approvalQueue.setProductId(product.getId());
                    approvalQueueRepository.save(approvalQueue);
                }
                productRepository.save(product);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteProduct(@PathVariable Long productId) {
        try {
            // push to approval queue
            ApprovalQueue approvalQueue = new ApprovalQueue();
            approvalQueue.setRequestDate(new Date());
            approvalQueue.setDeleted(false);
            approvalQueue.setApproved(false);
            approvalQueue.setState("Pending");
            approvalQueue.setProductId(productId);
            approvalQueueRepository.save(approvalQueue);
            productRepository.deleteById(productId);

        } catch (Exception e) {
            throw e;
        }
    }

    public List<Product> fetchProducts() {
        List<Product> prodList = new ArrayList<>();
        try {
            List<Product> productList = productRepository.findAll(Sort.by(Sort.Direction.DESC, "postedDate"));
            for (Product product : productList) {
                if ("Active".equalsIgnoreCase(product.getStatus()))
                    prodList.add(product);
            }
        } catch (Exception e) {
            throw e;
        }
        return prodList;
    }

    public List<Product> fetchProductsInApprovalQueue() {
        List<Product> productList = new ArrayList<>();
        try {
            List<ApprovalQueue> approvalQueueList = approvalQueueRepository.findAll(Sort.by(Sort.Direction.ASC, "requestDate"));
            for (ApprovalQueue approvalQueue : approvalQueueList) {
                if (!approvalQueue.isDeleted())
                    productList.add(fetchProductById(approvalQueue.getProductId()));
            }
        } catch (Exception e) {
            throw e;
        }
        return productList;
    }

    public void approveProduct(Long approvalId) {
        try {
            ApprovalQueue approvalQueue = fetchApprovalQueueProductById(approvalId);
            if (approvalQueue != null) {
                approvalQueue.setApproved(true);
                approvalQueue.setDeleted(true);
                approvalQueue.setState("Approved");
                approvalQueueRepository.save(approvalQueue);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void rejectProduct(Long approvalId) {
        try {
            ApprovalQueue approvalQueue = fetchApprovalQueueProductById(approvalId);
            if (approvalQueue != null) {
                approvalQueue.setDeleted(true);
                approvalQueueRepository.save(approvalQueue);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Product> searchProducts(String productName, Double minPrice, Double maxPrice, Date minPostedDate, Date maxPostedDate) {
        try {
            return productRepository.findProductsBetweenDatesAndPrices(productName, minPostedDate, maxPostedDate, minPrice, maxPrice);
        } catch (Exception e) {
            throw e;
        }
    }

    private Product fetchProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElse(null);
    }

    private ApprovalQueue fetchApprovalQueueProductById(Long approvalId) {
        Optional<ApprovalQueue> optionalApprovalQueue = approvalQueueRepository.findById(approvalId);
        return optionalApprovalQueue.orElse(null);
    }

    private ApprovalQueue fetchApprovalQueueByProductId(Long productId) {
        Optional<ApprovalQueue> optionalApprovalQueue = approvalQueueRepository.findApprovalQueueByProductId(productId);
        return optionalApprovalQueue.orElse(null);
    }

}
