package study.shoppingmall.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Board;
import study.shoppingmall.repository.BoardRepository;

@Service
@NoArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    /**
     * 게시물 목록
     */
    public Page<Board> findBoardList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize());
        return boardRepository.findAll(pageable);
    }

    /**
     * 게시물 생성 및 상세 조회
     */
    public Board findBoardByIdx(Long idx) {
        return boardRepository.findById(idx).orElse(new Board());
    }

    /**
     * 조회수
     */
    @Transactional
    public Board increaseHit(Board board) {
        board.setHit(board.getHit()+1);
        return board;
    }

}
