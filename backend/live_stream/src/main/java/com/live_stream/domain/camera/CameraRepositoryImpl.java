package com.live_stream.domain.camera;

import com.live_stream.domain.camera.dto.CameraDto;
import com.live_stream.domain.camera.dto.CameraSearchCondition;
import com.live_stream.domain.cameracategory.QCameraCategory;
import com.live_stream.domain.cameratype.QCameraType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CameraRepositoryImpl implements CameraRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CameraDto> search(CameraSearchCondition cond, Pageable pageable) {
        QCamera camera = QCamera.camera;

        QCameraCategory category = QCameraCategory.cameraCategory;
        QCameraCategory parent = new QCameraCategory("parent");
        QCameraCategory grandParent = new QCameraCategory("grandParent");

        QCameraType type = QCameraType.cameraType;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(camera.isDeleted.isFalse());

        if (cond.getName() != null && !cond.getName().isBlank()) {
            builder.and(camera.name.containsIgnoreCase(cond.getName()));
        }
        if (cond.getActive() != null) {
            builder.and(camera.isActive.eq(cond.getActive()));
        }
        if (cond.getStatus() != null) {
            builder.and(camera.status.eq(cond.getStatus()));
        }

        if (cond.getCategoryId() != null) {
            List<Long> categoryIds = findAllSubCategoryIds(cond.getCategoryId());
            if (!categoryIds.isEmpty()) {
                builder.and(camera.cameraCategory.id.in(categoryIds));
            }
        }

        List<Camera> cameras = queryFactory
                .selectFrom(camera)
                .leftJoin(camera.cameraCategory, category).fetchJoin()
                .leftJoin(category.parent, parent).fetchJoin()
                .leftJoin(parent.parent, grandParent).fetchJoin()
                .leftJoin(camera.cameraType, type).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(camera.createdDate.desc())
                .fetch();

        Long total = queryFactory
                .select(camera.count())
                .from(camera)
                .leftJoin(camera.cameraCategory, category)
                .leftJoin(category.parent, parent)
                .leftJoin(parent.parent, grandParent)
                .where(builder)
                .fetchOne();

        List<CameraDto> content = cameras.stream()
                .map(CameraDto::fromEntity)
                .toList();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    private List<Long> findAllSubCategoryIds(Long categoryId) {
        QCameraCategory cc = QCameraCategory.cameraCategory;
        QCameraCategory child = new QCameraCategory("child");
        QCameraCategory grandchild = new QCameraCategory("grandchild");

        // 카테고리 트리 전체에서 자식/손자 포함 ID 수집
        return queryFactory
                .select(cc.id)
                .from(cc)
                .leftJoin(cc.parent, child)
                .leftJoin(child.parent, grandchild)
                .where(
                        cc.id.eq(categoryId)
                                .or(cc.parent.id.eq(categoryId))
                                .or(cc.parent.parent.id.eq(categoryId))
                )
                .fetch();
    }
}