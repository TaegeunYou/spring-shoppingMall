package study.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Comment;
import study.shoppingmall.domain.Product;
import study.shoppingmall.dto.CommentDto;
import study.shoppingmall.repository.CommentRepository;
import study.shoppingmall.repository.MemberRepository;
import study.shoppingmall.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    /**
     * 리뷰 저장
     */
    @Transactional
    public void saveComment(CommentDto commentDto, Long memberId) {

        Product product = productRepository.findByName(commentDto.getProductName()).get(0);
        product.addComment(Integer.parseInt(commentDto.getRating()));
        Comment comment = new Comment(commentDto, product, memberId);
        commentRepository.save(comment);

    }

    /**
     * 해당 상품의 리뷰 가져오기
     */
    public List<CommentDto> getCommentList(Long id) {

        List<Comment> commentList = commentRepository.findByProduct(productRepository.findById(id).get());
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            String memberNickname = memberRepository.getById(comment.getMemberId()).getNickname();
            CommentDto commentDto = new CommentDto(memberNickname, Integer.toString(comment.getRating()), comment.getContent(), comment.getCreatedDate());

            commentDtoList.add(commentDto);
        }

        return commentDtoList;
    }




}
