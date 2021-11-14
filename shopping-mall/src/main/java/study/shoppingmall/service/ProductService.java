package study.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import study.shoppingmall.domain.Product;
import study.shoppingmall.dto.ProductDto;
import study.shoppingmall.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 저장
     */
    @Transactional
    public void saveProduct(ProductDto productDto) {
        Product product = new Product(productDto);
        productRepository.save(product);
    }

    /**
     * 상품 수정 -> 변경 감지 이용 (transactional이 끝나면서 commit할 때 flush하면서 영속성 컨텍스트에 있는 것 중에 변경된 것을 찾아 그것에 대한 업데이트 쿼리를 알아서 만들어 보낸다. 따라서 persist 안해줘도 된다.)
     */
    @Transactional
    public void updateProduct(Long productId, String name, String category, String categoryDetail, String color, String material, String size, String country, String detail, String ASPhone, int price, int stock) {
        Product findProduct = productRepository.findById(productId).get();
        findProduct.changeProduct(name, category, categoryDetail, color, material, size, country, detail, ASPhone, price, stock);
    }

    /**
     * 전체 조회
     */
    public List<ProductDto> findAllProducts() {

        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            ProductDto productDto = new ProductDto(product);
            productDtos.add(productDto);
        }

        return productDtos;
    }

    /**
     * 한개 조회
     */
    public ProductDto findProduct(Long id) {
        Product product = productRepository.findById(id).get();

        ProductDto productDto = new ProductDto(product);

        return productDto;
    }

    /**
     * 검색으로 조회
     */
    public List<ProductDto> findProducts(String keyword, String sort) {
        List<Product> products = new ArrayList<>();
        if(sort.equals("Pop")) {
            products = productRepository.findByNameContainingOrCategoryContainingOrderByReviewDesc(keyword, keyword);
        } else if (sort.equals("Rec")) {
            products = productRepository.findByNameContainingOrCategoryContainingOrderByRatingDesc(keyword, keyword);
        } else if (sort.equals("Desc")) {
            products = productRepository.findByNameContainingOrCategoryContainingOrderByPriceDesc(keyword, keyword);
        } else if (sort.equals("Asc")){
            products = productRepository.findByNameContainingOrCategoryContainingOrderByPriceAsc(keyword, keyword);
        }

        List<ProductDto> productDtoList = new ArrayList<>();

        if (products.isEmpty()) return productDtoList;

        for (Product product : products) {
            productDtoList.add(new ProductDto(product));
        }

        return productDtoList;
    }

    /**
     * 카테고리로 조회
     */
    public List<ProductDto> findProductsByCategory(String keyword) {
        List<Product> products = productRepository.findByCategory(keyword);
        List<ProductDto> productDtoList = new ArrayList<>();

        if (products.isEmpty()) return productDtoList;

        for (Product product : products) {
            productDtoList.add(new ProductDto(product));
        }

        return productDtoList;
    }

    /**
     * 세부 카테고리로 조회
     */
    public List<ProductDto> findProductsByCategoryDetail(String keyword, String keywordDetail) {
        List<Product> products = productRepository.findByCategoryAndCategoryDetail(keyword, keywordDetail);
        List<ProductDto> productDtoList = new ArrayList<>();

        if (products.isEmpty()) return productDtoList;

        for (Product product : products) {
            productDtoList.add(new ProductDto(product));
        }

        return productDtoList;
    }

    /**
     *  사진 파일 업로드 (상품 저장)
     */
    public void saveFile(MultipartFile file, String name, int sequence){

        String UPLOAD_PATH = "C:\\Users\\dbxor\\Desktop\\쇼핑몰 프로젝트\\shopping-mall\\shopping-mall\\out\\production\\resources\\static\\assets\\img\\product";

        // 파일 이름 변경
        String saveName = name + sequence + ".png";

        // 저장할 File 객체를 생성(껍데기 파일)
        File saveFile = new File(UPLOAD_PATH,saveName); // 저장할 폴더 이름, 저장할 파일 이름

        try {
            file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
