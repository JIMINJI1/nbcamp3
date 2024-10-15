package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.dto.CreateCommentRequestDto;
import com.sparta.schedule.dto.UpdateCommentRequestDto;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j

public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    // 1. 댓글 등록
    public CommentResponseDto createComment(CreateCommentRequestDto requestDto) {
        // dto -> entity
        Comment comment = new Comment(
                requestDto.getUsername(),
                requestDto.getComment(),
                scheduleRepository.findById(requestDto.getScheduleId()).orElseThrow(()->new EntityNotFoundException("일정이 존재하지 않습니다"))
        );

        // DB 저장
        Comment savedComment = commentRepository.save(comment);

        // entity -> responseDto
        CommentResponseDto commentResponseDto = new CommentResponseDto(savedComment);

        return commentResponseDto;

    }

    // 2. 댓글 조회
    public List<CommentResponseDto> getCommentsByScheduleId(Long scheduleId) {
        return commentRepository.findBySchedule_ScheduleId(scheduleId).stream()
                .map(CommentResponseDto::new).collect(Collectors.toList());
    }


    // 3. 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto requestDto) {
        // 해당 댓글  DB에 있는지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        comment.setComment(requestDto.getComment());

        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(updatedComment);
    }

    // 4. 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if(comment == null){
            throw new IllegalArgumentException("댓글이 존재하지 않습니다");
        }

        commentRepository.deleteById(commentId);
    }

}
