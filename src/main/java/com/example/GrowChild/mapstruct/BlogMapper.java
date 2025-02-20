package com.example.GrowChild.mapstruct;

import com.example.GrowChild.dto.BlogDTO;
import com.example.GrowChild.entity.Blog;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BlogMapper {

    default BlogDTO toDTO(Blog blog){
        return BlogDTO.builder()
                .blogId(blog.getBlogId())
                .title(blog.getTitle())
                .description(blog.getDescription())
                .content(blog.getContent())
                .parentId(blog.getParentId().getUser_id())
                .build();
    }
    default List<BlogDTO> toDTOList(List<Blog> blogList){
        return blogList.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
