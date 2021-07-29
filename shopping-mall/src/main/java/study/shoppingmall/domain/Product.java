package study.shoppingmall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import study.shoppingmall.dto.ProductDto;
import study.shoppingmall.exception.NotEnoughStockException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private long id;            //상품번호

    @Column(name = "product_name")
    private String name;        //상품명

    @Column(name = "product_category")
    private String category;    //카테고리

    @Column(name = "product_color")
    private String color;       //색상 코드(RGB) ex) "#F0F8FF"

    @Column(name = "product_material")
    private String material;    //주요 소재 ex) "LPM, PB, 데코시트"               //다대다

    @Column(name = "product_size")
    private String size;        //사이즈

    @Column(name = "product_country")
    private String origin;     //원산지 ex) "한국"

    @Column(name = "product_detail")
    private String detail;      //설명

    @Column(name = "product_ASPhone")
    private String ASPhone;     //AS 전화번호

    @Column(name = "product_price")
    private int price;          //정가

    @Column(name = "product_stock")
    private int stock;          //재고

//    @OneToMany(mappedBy = "products")
//    private List<CategoryProduct> categories = new ArrayList<>();


    //==Setter==//
    protected void setName(String name) {
        this.name = name;
    }

    protected void setCategory(String category) {
        this.category = category;
    }

    protected void setColor(String color) {
        this.color = color;
    }

    protected void setMaterial(String material) {
        this.material = material;
    }

    protected void setSize(String size) {
        this.size = size;
    }

    protected void setOrigin(String origin) {
        this.origin = origin;
    }

    protected void setDetail(String detail) {
        this.detail = detail;
    }

    protected void setASPhone(String ASPhone) {
        this.ASPhone = ASPhone;
    }

    protected void setPrice(int price) {
        this.price = price;
    }

    protected void setStock(int stock) {
        this.stock = stock;
    }

    //==생성 or 수정 메서드==//
    public Product(ProductDto productDto) {
        this.name = productDto.getName();
        this.category = productDto.getCategory();
        this.color = productDto.getColor();
        this.material = productDto.getMaterial();
        this.size = productDto.getSize();
        this.origin = productDto.getOrigin();
        this.detail = productDto.getDetail();
        this.ASPhone = productDto.getASPhone();
        this.price = productDto.getPrice();
        this.stock = productDto.getStock();
    }

    //setter를 외부로 노출시키지 않기 위한 방법
    public void changeProduct(String name, String category, String color, String material, String size, String origin, String detail, String ASPhone, int price, int stock) {
        this.setName(name);
        this.setCategory(category);
        this.setColor(color);
        this.setMaterial(material);
        this.setSize(size);
        this.setOrigin(origin);
        this.setDetail(detail);
        this.setASPhone(ASPhone);
        this.setPrice(price);
        this.setStock(stock);

    }

    //==비즈니스 로직==//
    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stock += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stock = restStock;
    }


}
