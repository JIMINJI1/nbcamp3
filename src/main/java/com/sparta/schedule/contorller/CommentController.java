package com.sparta.schedule.contorller;

import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.dto.CreateCommentRequestDto;
import com.sparta.schedule.dto.UpdateCommentRequestDto;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/{scheduleId}/comment")
public class CommentController {
    private final CommentService commentService;

    // 1. 댓글 등록
    @PostMapping("")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long scheduleId,
                                                            @Valid @RequestBody CreateCommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(requestDto, scheduleId, userDetails.getUser()));
    }

    // 2. 댓글 조회
    @GetMapping("")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long scheduleId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.getCommentsByScheduleId(scheduleId, userDetails.getUser()));
    }

    // 3. 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.updateComment(commentId, requestDto, userDetails.getUser()));
    }

    // 4. 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

}
