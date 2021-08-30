package study.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Cart;
import study.shoppingmall.domain.Member;
import study.shoppingmall.domain.Product;
import study.shoppingmall.dto.CartDto;
import study.shoppingmall.repository.CartRepository;
import study.shoppingmall.repository.MemberRepository;
import study.shoppingmall.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    /**
     * 장바구니 담기
     * category 일단 생략.
     */
    @Transactional
    public void addCart(Long memberId, Long productId, String category, int count) {
        Member member = memberRepository.findById(memberId).get();
        Product product = productRepository.findById(productId).get();
        Cart cart = new Cart(member, product, count);
        cartRepository.save(cart);
    }

    /**
     * 장바구니 목록 가져오기
     * 취소 기능 포함
     */
    public List<Cart> findCartList(Long memberId) {
        List<Cart> list = cartRepository.findByMemberId(memberId);
        List<Cart> cartList = new ArrayList<>();

//        if (list.isEmpty()) return productDtoList;        //장바구니가 비어있을 경우

        for (Cart cart : list) {
            cartList.add(cart);
        }

        return cartList;
    }

    /**
     * Cart -> CartDto
     */
    public List<CartDto> cartToCartDto(List<Cart> cartList) {

        List<CartDto> cartDtoList = new ArrayList<>();

        for (Cart cart : cartList) {

            //상품명, 상품가격 가져오기
            String name = cart.getProduct().getName();
            int price = cart.getProduct().getPrice();

            CartDto cartDto = new CartDto(cart.getId(), name, price, cart.getCount());
            cartDtoList.add(cartDto);
        }

        return cartDtoList;
    }

    /**
     * 장바구니 삭제
     */
    @Transactional
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }


}
