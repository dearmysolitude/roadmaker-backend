package com.roadmaker.lucid.roadmap.entity.blogkeyword;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Table(name = "BLOG_KEYWORD")
public class BlogKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String keyword = "";
    
    @Builder
    public BlogKeyword(String keyword) {
        this.keyword = keyword;
    }
}
