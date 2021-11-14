package study.shoppingmall;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Address;
import study.shoppingmall.domain.Cart;
import study.shoppingmall.domain.Member;
import study.shoppingmall.domain.Product;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.dto.ProductDto;
import study.shoppingmall.repository.MemberRepository;
import study.shoppingmall.service.MemberService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitMember initMember;
    private final InitProduct initProduct;
    private final MemberRepository memberRepository;
    private MemberService memberService;

    @PostConstruct
    public void init() {
        initMember.initMember();
        initMember.initAdmin();
//        initProduct.initBed11();
//        initProduct.initBed22();
//        initProduct.initTable();

        initProduct.initBed1();
        initProduct.initBed2();
        initProduct.initBed3();
        initProduct.initBed4();
        initProduct.initSofa1();
        initProduct.initDesk1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitMember {

        private final EntityManager em;

        public void initMember() {
            String email = "abc@naver.com";
            String pw = "qwer123$";
            String name = "홍길동";
            String nickname = "멋쟁이";
            String gender = "male";
            String birthday = "20202020";
            String phone = "01012345678";
            String zipcode = "02721";
            String address = "서울 성북구 길음로9길 46";


            MemberDto memberDto = new MemberDto(email, pw, name, nickname, gender, birthday, phone, zipcode, address, null);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

            Member member = new Member(memberDto);
            em.persist(member);

        }

        public void initAdmin() {
            String email = "admin@example.com";
            String pw = "qwer123$";
            String name = "철수";
            String nickname = "관리자";
            String gender = "female";
            String birthday = "10101010";
            String phone = "01087654321";
            String zipcode = "12345";
            String address = "대전광역시";


            MemberDto memberDto = new MemberDto(email, pw, name, nickname, gender, birthday, phone, zipcode, address, null);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

            Member member = new Member(memberDto);
            em.persist(member);
        }

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitProduct {

        private final EntityManager em;

//        public void initBed11() {
//
//            String name = "짱짱 침대";
//            String category = "Bed";
//            String categoryDetail = "Super-Single";
//            String color = "white";
//            String material = "나무";
//            String size = "100x200 cm";
//            String origin = "한국";
//            String ASPhone = "01012344321";
//            String detail = "이 침대는 짱짱 튼튼 합니다.";
//            int price = 10000;
//            int stock = 5;
//
//            ProductDto productDto = new ProductDto(name, category, categoryDetail, color, material, size, origin, ASPhone, detail, price, stock);
//
//            Product product = new Product(productDto);
//            em.persist(product);
//        }
//
//        public void initBed22() {
//
//            String name = "푹신 침대";
//            String category = "Bed";
//            String categoryDetail = "Queen-King";
//            String color = "black";
//            String material = "고무";
//            String size = "30x50 cm";
//            String origin = "미국";
//            String ASPhone = "01032342154";
//            String detail = "이 침대는 푹신해요. 푹신";
//            int price = 5000;
//            int stock = 2;
//
//            ProductDto productDto = new ProductDto(name, category, categoryDetail, color, material, size, origin, ASPhone, detail, price, stock);
//
//            Product product = new Product(productDto);
//            em.persist(product);
//        }
//
//
//
//        public void initTable() {
//
//            String name = "으뜸 책상";
//            String category = "Mattress";
//            String categoryDetail = "Super-Single";
//            String color = "blue";
//            String material = "플라스틱";
//            String size = "90x200 cm";
//            String origin = "중국";
//            String ASPhone = "01011111111";
//            String detail = "공부할 때는 으뜸 책상";
//            int price = 999;
//            int stock = 3;
//
//
//            ProductDto productDto = new ProductDto(name, category, categoryDetail, color, material, size, origin, ASPhone, detail, price, stock);
//
//            Product product = new Product(productDto);
//            em.persist(product);
//        }


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        public void initBed1() {

            String name = "NORDLI 노르들리 침대";
            String category = "Bed";
            String categoryDetail = "Super-Single";
            String color = "white";
            String material = "소나무 원목";
            String size = "120x200 cm";
            String origin = "한국";
            String ASPhone = "01012345678";
            String detail = "NORDLI/노르들리 침대프레임은 그냥 침대가 아닙니다. 넉넉한 서랍 3개가 있는 수납유닛이기도 하죠. 작은 집에서도 옷, 이불 등의 수납과 편안한 숙면이라는 두 마리 토끼를 다 잡을 수 있는 실용적인 솔루션이죠.";
            int price =  299000;
            int stock = 100;

            ProductDto productDto = new ProductDto(name, category, categoryDetail, color, material, size, origin, ASPhone, detail, price, stock);

            Product product = new Product(productDto);
            em.persist(product);
        }

        public void initBed2() {

            String name = "SLATTUM 슬라툼 침대";
            String category = "Bed";
            String categoryDetail = "Queen-King";
            String color = "gray";
            String material = "폴리에스테르";
            String size = "150x200 cm";
            String origin = "중국";
            String ASPhone = "01012345678";
            String detail = "부드러운 직조 패브릭으로 씌워져 있어 침실에 아늑한 느낌을 더해줍니다. 침대헤드는 늦은 밤 독서를 즐길 때 편안한 등받이가 되어줍니다. 더 좋은 점은 모든 것이 단일 패키지로 제공된다는 점입니다.";
            int price = 179000;
            int stock = 100;

            ProductDto productDto = new ProductDto(name, category, categoryDetail, color, material, size, origin, ASPhone, detail, price, stock);

            Product product = new Product(productDto);
            em.persist(product);
        }

        public void initBed3() {

            String name = "IDANÄS 이다네스 침대";
            String category = "Bed";
            String categoryDetail = "Family";
            String color = "brown";
            String material = "소나무 원목";
            String size = "180x200 cm";
            String origin = "중국";
            String ASPhone = "01012345678";
            String detail = "튼튼하고 견고하게 제작되어 오래도록 쓸 수 있는 침대예요.";
            int price = 399000;
            int stock = 100;

            ProductDto productDto = new ProductDto(name, category, categoryDetail, color, material, size, origin, ASPhone, detail, price, stock);

            Product product = new Product(productDto);
            em.persist(product);
        }

        public void initBed4() {

            String name = "VITVAL 비트발 2층침대";
            String category = "Bed";
            String categoryDetail = "Bunk";
            String color = "white";
            String material = "스틸";
            String size = "90x200 cm";
            String origin = "중국";
            String ASPhone = "01012345678";
            String detail = "곡선 형태의 패브릭 안전가드입니다. 2층침대의 고유한 세부 정보입니다. 2개의 공간이 필요하지만 공간이 제한적일 때 적합합니다. MÖJLIGHET/뫼일릭헤트 시리즈의 액세서리로 포인트를 주세요.";
            int price = 269000;
            int stock = 100;

            ProductDto productDto = new ProductDto(name, category, categoryDetail, color, material, size, origin, ASPhone, detail, price, stock);

            Product product = new Product(productDto);
            em.persist(product);
        }

        public void initSofa1() {

            String name = "VALLENTUNA 발렌투나 소파";
            String category = "Sofa";
            String categoryDetail = "Bed";
            String color = "gray";
            String material = "원목";
            String size = "193x266 cm";
            String origin = "한국";
            String ASPhone = "01012345678";
            String detail = "무한한 가능성을 지닌 소파. 여분의 침대나 똑똑한 수납함, 아늑한 독서 공간이 필요하시다고요? 문제없어요. 마음에 드는 모듈을 간편하게 선택하거나 원하는 대로 조합해보세요. 필요할 때마다 마음껏 인테리어를 바꿀 수도 있답니다.";
            int price = 1700000;
            int stock = 100;

            ProductDto productDto = new ProductDto(name, category, categoryDetail, color, material, size, origin, ASPhone, detail, price, stock);

            Product product = new Product(productDto);
            em.persist(product);
        }

        public void initDesk1() {

            String name = "ANGERSBY 앙에르스뷔 2인용소파";
            String category = "Sofa";
            String categoryDetail = "2-3Person";
            String color = "gray";
            String material = "스틸";
            String size = "84x137 cm";
            String origin = "중국";
            String ASPhone = "01012345678";
            String detail = "소파는 공간을 효율적으로 활용할 수 있도록 포장되어 집까지 편하게 운반할 수 있습니다.";
            int price = 149000;
            int stock = 100;

            ProductDto productDto = new ProductDto(name, category, categoryDetail, color, material, size, origin, ASPhone, detail, price, stock);

            Product product = new Product(productDto);
            em.persist(product);
        }







    }

//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitCart {
//
//        private final EntityManager em;
//
//        public void initCart() {
//
//            int count = 5;
//
//
//            Cart cart = new Cart(count);
//
//            em.persist(cart);
//
//        }
//
//    }
}
