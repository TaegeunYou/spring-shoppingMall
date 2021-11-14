package study.shoppingmall.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private String rating;
    private String content;
    private String productName;

    private String memberNickname;
    private LocalDateTime createdDate;

    public CommentDto(String memberNickname, String rating, String content, LocalDateTime createdDate) {
        this.memberNickname = memberNickname;
        this.rating = rating;
        this.content = content;
        this.createdDate = createdDate;
    }

}
