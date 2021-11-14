package study.shoppingmall.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Comment;
import study.shoppingmall.dto.CommentDto;
import study.shoppingmall.repository.CommentRepository;
import study.shoppingmall.service.CommentService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class ProductTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    @Test
    public void 장바구니() throws Exception {

        //given
        Long memberId1 = new Long(1);

        //when
        CommentDto commentDto = new CommentDto("멋쟁이", "3", "너무 좋아요!", LocalDateTime.now());
        commentDto.setProductName("ANGERSBY 앙에르스뷔 2인용소파");
        commentService.saveComment(commentDto, memberId1);

        Long memberId2 = commentRepository.findByMemberId(memberId1).get(0).getMemberId();

        //then
        Assertions.assertThat(memberId1).isEqualTo(memberId2);



        List<Comment> comments = em.createQuery("select c from Comment c", Comment.class)
                .getResultList();

        for (Comment c : comments) {
            System.out.println("member.getId() = " + c.getId());
            System.out.println("member.getId() = " + c.getContent());
        }



    }

}