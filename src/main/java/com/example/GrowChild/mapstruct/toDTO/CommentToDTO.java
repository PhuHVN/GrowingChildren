package com.example.GrowChild.mapstruct.toDTO;

import com.example.GrowChild.dto.CommentDTO;
import com.example.GrowChild.dto.ConsultingDTO;
import com.example.GrowChild.entity.response.Comment;
import com.example.GrowChild.entity.response.Consulting;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface CommentToDTO {

    default CommentDTO toDTO(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .comment(comment.getComment())
                .date(comment.getDate())
                .blogId(comment.getBlogId().getBlogId())
                .build();
    }

    default List<CommentDTO> toDTOList(List<Comment> commentList) {
        return commentList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
