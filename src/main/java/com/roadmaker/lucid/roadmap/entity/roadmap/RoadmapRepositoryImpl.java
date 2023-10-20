package com.roadmaker.lucid.roadmap.entity.roadmap;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roadmaker.lucid.roadmap.dto.RoadmapFindResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

//import static com.roadmaker.roadmap.entity.roadmap.QRoadmap.roadmap;
//import static com.roadmaker.like.entity.QLike.like;

//@Repository @Slf4j
//public class RoadmapRepositoryImpl extends QuerydslRepositorySupport implements RoadmapRepositoryCustom {
//    
//    @Autowired
//    private JPAQueryFactory queryFactory;
//    
//    public RoadmapRepositoryImpl() { super(Roadmap.class); }
//    
//    @Override
//    public RoadmapFindResponse findBySearchOption(PageRequest pageRequest, String keyword) {
//        JPQLQuery<Roadmap> query = queryFactory.selectFrom(roadmap)
//    }
    
//    @Override
//    public RoadmapFindResponse orderByLikes(PageRequest pageRequest) {
//        JPQLQuery<Roadmap> query = queryFactory.selectFrom()
//    }
//}
