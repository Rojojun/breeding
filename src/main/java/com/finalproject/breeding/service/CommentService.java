package com.finalproject.breeding.service;

import com.finalproject.breeding.dto.CommentRequestDto;
import com.finalproject.breeding.dto.MyDto;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.model.Comment;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.repository.BoardMainRepository;
import com.finalproject.breeding.repository.CommentMapping;
import com.finalproject.breeding.repository.CommentRepository;
import com.finalproject.breeding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final BoardMainRepository boardMainRepository;
    private final UserRepository userRepository;

    //댓글 작성
    @Transactional
    public ResponseEntity<MyDto> createComment( CommentRequestDto requestDto, Long boardMainId, String username) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다."));

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다."));

        Comment comment = new Comment(requestDto, boardMain, user);

        commentRepository.save(comment);
        boardMain.plusCommentCnt(boardMain);


        dto.setStatus(ErrorCode.OK);
        dto.setData("boardMainId :"+boardMainId);
        dto.setMessage("댓글 등록!");
        return new ResponseEntity<>(dto, header, HttpStatus.OK);


    }

    //댓글 불러오기
    @Transactional
    public Slice<CommentMapping> getAllCommnet(Long boardMainId, Long page) {
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 4, Sort.by(Sort.Direction.DESC, "Id"));
        return commentRepository.findByBoardMainId(pageRequest, boardMainId);
    }

    //댓글 수정
    @Transactional
    public void patchComment(CommentRequestDto requestDto, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("댓글이 존재하지 않습니다."));


        comment.updateComment(requestDto);

    }


}
