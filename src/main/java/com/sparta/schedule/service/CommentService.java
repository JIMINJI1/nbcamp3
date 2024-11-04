package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.dto.CreateCommentRequestDto;
import com.sparta.schedule.dto.UpdateCommentRequestDto;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.repository.UserRepository;
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
    private final UserRepository userRepository;

    // 1. 댓글 등록
    public CommentResponseDto createComment(CreateCommentRequestDto requestDto, Long scheduleId, User user) {
        // dto -> entity 변환 및 일정 조회
        Comment comment = new Comment(
                userRepository.findById(user.getUserId()).orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다.")),
                requestDto.getComment(),
                scheduleRepository.findById(scheduleId).orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."))
        );

        // DB 저장
        Comment savedComment = commentRepository.save(comment);

        // entity -> responseDto
        CommentResponseDto commentResponseDto = new CommentResponseDto(savedComment);

        return commentResponseDto;

    }

    // 2. 댓글 조회
    public List<CommentResponseDto> getCommentsByScheduleId(Long scheduleId, User user) {
        return commentRepository.findBySchedule_ScheduleId(scheduleId).stream()
                .map(CommentResponseDto::new).collect(Collectors.toList());
    }


    // 3. 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto requestDto, User user) {
        // 해당 댓글  DB에 있는지 확인
        Comment comment = findComment(commentId);

        // 댓글 내용 업데이트
        comment.updateComment(requestDto.getComment());

        // 변경된 댓글 저장
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(updatedComment);
    }

    // 4. 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, User user) {
        // 해당 댓글  DB에 있는지 확인
        Comment comment = findComment(commentId);

        // 댓글 삭제
        commentRepository.delete(comment);
    }

    // 댓글 존재 확인 메소드
    public Comment findComment(Long commendId) {
        return commentRepository.findById(commendId).orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));
    }
}
