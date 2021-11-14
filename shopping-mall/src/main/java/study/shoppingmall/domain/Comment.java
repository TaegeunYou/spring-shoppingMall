package study.shoppingmall.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.shoppingmall.dto.CommentDto;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_rating")
    private int rating;

    @Column(name = "comment_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "comment_member_id")
    private Long memberId;

    public Comment(CommentDto commentDto, Product product, Long memberId) {
        this.rating = Integer.parseInt(commentDto.getRating());
        this.content = commentDto.getContent();
        this.product = product;
        this.memberId = memberId;
    }
}
