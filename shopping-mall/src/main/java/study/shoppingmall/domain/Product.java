package study.shoppingmall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shoppingmall.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private long id;                //상품번호

    @Column(name = "product_name")
    private String name;            //상품명

    @Column(name = "product_price")
    private int price;              //가격

    @Column(name = "product_stock")
    private int stock;              //수량(재고)

    @Column(name = "product_detail")
    private String detail;          //설명

    @Column(name = "product_date")
    private String date;            //등록날짜

    @OneToMany(mappedBy = "products")
    private List<CategoryProduct> categories = new ArrayList<>();

    protected void setName(String name) {
        this.name = name;
    }

    protected void setPrice(int price) {
        this.price = price;
    }

    protected void setStock(int stock) {
        this.stock = stock;
    }

    protected void setDetail(String detail) {
        this.detail = detail;
    }

    //setter를 외부로 노출시키지 않기 위한 방법
    public void changeProduct(String name, int price, int stock, String detail) {
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setDetail(detail);
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
