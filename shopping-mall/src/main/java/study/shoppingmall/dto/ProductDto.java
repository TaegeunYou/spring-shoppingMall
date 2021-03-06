package study.shoppingmall.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.shoppingmall.domain.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
public class ProductDto {

    private long id;

    @NotBlank(message = "상품명을 입력해 주세요.")
    private String name;        //상품명

    @NotBlank(message = "카테고리를 입력해 주세요.")
    private String category;    //카테고리  -> 선택박스                //다대다

    @NotBlank(message = "색상을 입력해 주세요.")
    private String color;       //색상 코드(RGB) -> 그냥 적기 ex) #F0F8FF

    @NotBlank(message = "소재를 입력해 주세요.")
    private String material;    //주요 소재  -> 그냥 적기 ex) LPM, PB, 데코시트               //다대다

    @NotBlank(message = "사이즈을 입력해 주세요.")
    private String size;        //사이즈    -> 그냥 적기

    @NotBlank(message = "원산지을 입력해 주세요.")
    private String origin;     //원산지    -> 그냥 적기 ex) 한국

    @NotBlank(message = "전화번호를 입력해 주세요.")
    private String ASPhone;     //AS 전화번호 -> 그냥 적기

    @NotBlank(message = "설명을 입력해 주세요.")
    private String detail;      //설명

    @NotNull(message = "가격을 입력해 주세요.")
    private int price;          //정가    -> 숫자만 적을수있도록                 //다대다

    @NotNull(message = "재고를 입력해 주세요.")
    private int stock;          //재고    -> 숫자만 적을수있도록

    //==생성 메서드==//

    public ProductDto(String name, String category, String color, String material, String size, String origin, String ASPhone, String detail, int price, int stock) {
        this.name = name;
        this.category = category;
        this.color = color;
        this.material = material;
        this.size = size;
        this.origin = origin;
        this.ASPhone = ASPhone;
        this.detail = detail;
        this.price = price;
        this.stock = stock;
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.color = product.getColor();
        this.material = product.getMaterial();
        this.size = product.getSize();
        this.origin = product.getOrigin();
        this.ASPhone = product.getASPhone();
        this.detail = product.getDetail();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }
}
