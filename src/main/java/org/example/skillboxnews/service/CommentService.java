package org.example.skillboxnews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxnews.controller.request.CommentRequest;
import org.example.skillboxnews.controller.response.CommentResponse;
import org.example.skillboxnews.entity.Comment;
import org.example.skillboxnews.entity.News;
import org.example.skillboxnews.entity.User;
import org.example.skillboxnews.exception.NotFoundException;
import org.example.skillboxnews.mapper.CommentMapper;
import org.example.skillboxnews.repository.CommentRepository;
import org.example.skillboxnews.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @Transactional
    public CommentResponse saveComment(Long newsId, CommentRequest commentRequest, String token) {
        log.info("Saving comment {} to news with id {}", commentRequest, newsId);
        User user = userService.getByToken(token);
        News news = getNewsById(newsId);
        Comment comment = commentRepository.save(commentMapper.toComment(commentRequest, user, news));
        return commentMapper.toCommentResponse(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        log.info("Deleting comment by id {}", id);
        commentRepository.deleteById(id);
    }

    private News getNewsById(Long newsId) {
        return newsRepository.findById(newsId).orElseThrow(() -> new NotFoundException("News not found"));
    }

    @Transactional
    public CommentResponse updateComment(Long id, CommentRequest commentRequest) {
        log.info("Updating comment with id: {}. {}", id, commentRequest);
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        commentMapper.partialUpdate(commentRequest, comment);
        return commentMapper.toCommentResponse(comment);
    }
}
