package study.shoppingmall.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Board extends BaseEntity{

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "board_title")
    private String title;

    @Column(name = "board_content")
    private String content;

    @Column(name = "board_memberNickname")
    private String nickname;

    @Column
    private int hit;

    @Builder
    public Board(Long idx, String title, String content, String nickname) {
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.hit = 0;
    }

}
