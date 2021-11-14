package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.shoppingmall.domain.Board;
import study.shoppingmall.repository.BoardRepository;
import study.shoppingmall.service.BoardService;
import study.shoppingmall.service.MemberService;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final BoardRepository boardRepository;

    /**
     * 게시글 목록
     */
    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("boardList", boardService.findBoardList(pageable));
        return "/board/list";
    }

    /**
     * 게시글 상세 및 등록 폼 호출
     */
    @GetMapping({"", "/"})
    public String board(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        Board board = boardService.findBoardByIdx(idx);
        String nickname = memberService.findNickname(memberService.findMyId());

        model.addAttribute("board", boardService.increaseHit(board));
        model.addAttribute("nickname", nickname);


        return "/board/form";
    }

    /**
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<?> postBoard(@RequestBody Board board) {

        board.setNickname(memberService.findNickname(memberService.findMyId()));
        boardRepository.save(board);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{idx}")
    public ResponseEntity<?> putBoard(@PathVariable Long idx, @RequestBody Board board) {

        Board updateBoard = boardRepository.findById(idx).get();
        updateBoard.setTitle(board.getTitle());
        updateBoard.setContent(board.getContent());
        boardRepository.save(updateBoard);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteBoard(@PathVariable("idx") Long idx) {
        boardRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
