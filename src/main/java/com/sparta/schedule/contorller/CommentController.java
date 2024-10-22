package com.sparta.schedule.contorller;

import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.dto.CreateCommentRequestDto;
import com.sparta.schedule.dto.UpdateCommentRequestDto;
import com.sparta.schedule.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/{scheduleId}/comment")
public class CommentController {
    private final CommentService commentService;

    // 1. 댓글 등록
    @PostMapping("")
    public CommentResponseDto createComment(@Valid @RequestBody CreateCommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    // 2. 댓글 조회
    @GetMapping("")
    public List<CommentResponseDto> getComments(@PathVariable Long scheduleId) {
        return commentService.getCommentsByScheduleId(scheduleId);
    }

    // 3. 댓글 수정
    @PutMapping("/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequestDto requestDto) {
        return commentService.updateComment(commentId, requestDto);
    }

    // 4. 댓글 삭제
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

}
