package hackerthon.cchy.cchy25.domain.policy.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicySearchRequest;
import hackerthon.cchy.cchy25.domain.policy.entity.QPolicy;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PolicySearchRepositoryImpl implements PolicySearchRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PolicyResponse> search(
            Pageable pageable,
            PolicySearchRequest request
    ) {
        QPolicy policy = QPolicy.policy;
        BooleanBuilder builder = new BooleanBuilder();


        if (request.getHasModel() != null) {
            builder.and(policy.hasModel.eq(request.getHasModel()));
        }

        if (request.getHasCapital() != null) {
            builder.and(policy.hasCapital.eq(request.getHasCapital()));
        }

        if (request.getHasPlanner() != null) {
            builder.and(policy.hasPlanner.eq(request.getHasPlanner()));
        }

        if (request.getHasItem() != null) {
            builder.and(policy.hasItem.eq(request.getHasItem()));
        }

        if (request.getHasSpace() != null) {
            builder.and(policy.hasSpace.eq(request.getHasSpace()));
        }

        if (request.getHasEdu() != null) {
            builder.and(policy.hasEdu.eq(request.getHasEdu()));
        }

        if (request.getHasTeam() != null) {
            builder.and(policy.hasTeam.eq(request.getHasTeam()));
        }

        if (request.getHasMentor() != null) {
            builder.and(policy.hasMentor.eq(request.getHasMentor()));
        }

        if (request.getIsRegistered() != null) {
            builder.and(policy.isRegistered.eq(request.getIsRegistered()));
        }

        // 지역 필터
        if (request.getRegions() != null && !request.getRegions().isEmpty()) {
            builder.and(policy.regions.any().in(request.getRegions()));
        }

        // 지원 분야
        if (request.getSupportFields() != null && !request.getSupportFields().isEmpty()) {
            builder.and(policy.supportFields.any().in(request.getSupportFields()));
        }

        // 지원 종류
        if (request.getSupportCategories() != null && !request.getSupportCategories().isEmpty()) {
            builder.and(policy.supportCategories.any().in(request.getSupportCategories()));
        }

        // 신청 마감일 이전
        if (request.getApplyEndAt() != null) {
            builder.and(policy.applyEndAt.goe(LocalDateTime.now())
                    .and(policy.applyEndAt.loe(request.getApplyEndAt())));
        }

        // 지원 대상
        if (request.getSupportTargets() != null && !request.getSupportTargets().isEmpty()) {
            builder.and(policy.targets.any().in(request.getSupportTargets()));
        }

        // 심사 방법
        if (request.getEvaluationMethods() != null && !request.getEvaluationMethods().isEmpty()) {
            builder.and(policy.evaluationMethods.any().in(request.getEvaluationMethods()));
        }

        // 키워드 쿼리검색
        if (request.getQuery() != null && !request.getQuery().isBlank()) {
            builder.and(policy.title.containsIgnoreCase(request.getQuery())
                    .or(policy.summary.containsIgnoreCase(request.getQuery()))
                    .or(policy.conditionDetail.containsIgnoreCase(request.getQuery()))
                    .or(policy.targetDetail.containsIgnoreCase(request.getQuery())));
        }

        // 조회
        List<PolicyResponse> content = queryFactory
                /* QPolicyResponse 생성자 or Projections.fields() */
                .select(Projections.fields(
                        PolicyResponse.class,
                        policy.regions,
                        policy.supportFields,
                        policy.supportCategories,
                        policy.applyEndAt,
                        policy.targets,
                        policy.evaluationMethods
                ))
                .from(policy)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(policy.count())
                .from(policy)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<PolicyResponse> search(
            Pageable pageable,
            PolicySearchRequest request,
            Long userId
    ) {
        QPolicy policy = QPolicy.policy;
        BooleanBuilder builder = new BooleanBuilder();


        if (request.getHasModel() != null) {
            builder.and(policy.hasModel.eq(request.getHasModel()));
        }

        if (request.getHasCapital() != null) {
            builder.and(policy.hasCapital.eq(request.getHasCapital()));
        }

        if (request.getHasPlanner() != null) {
            builder.and(policy.hasPlanner.eq(request.getHasPlanner()));
        }

        if (request.getHasItem() != null) {
            builder.and(policy.hasItem.eq(request.getHasItem()));
        }

        if (request.getHasSpace() != null) {
            builder.and(policy.hasSpace.eq(request.getHasSpace()));
        }

        if (request.getHasEdu() != null) {
            builder.and(policy.hasEdu.eq(request.getHasEdu()));
        }

        if (request.getHasTeam() != null) {
            builder.and(policy.hasTeam.eq(request.getHasTeam()));
        }

        if (request.getHasMentor() != null) {
            builder.and(policy.hasMentor.eq(request.getHasMentor()));
        }

        if (request.getIsRegistered() != null) {
            builder.and(policy.isRegistered.eq(request.getIsRegistered()));
        }

        // 지역 필터
        if (request.getRegions() != null && !request.getRegions().isEmpty()) {
            builder.and(policy.regions.any().in(request.getRegions()));
        }

        // 지원 분야
        if (request.getSupportFields() != null && !request.getSupportFields().isEmpty()) {
            builder.and(policy.supportFields.any().in(request.getSupportFields()));
        }

        // 지원 종류
        if (request.getSupportCategories() != null && !request.getSupportCategories().isEmpty()) {
            builder.and(policy.supportCategories.any().in(request.getSupportCategories()));
        }

        // 신청 마감일 이전
        if (request.getApplyEndAt() != null) {
            builder.and(policy.applyEndAt.goe(LocalDateTime.now())
                    .and(policy.applyEndAt.loe(request.getApplyEndAt())));
        }

        // 지원 대상
        if (request.getSupportTargets() != null && !request.getSupportTargets().isEmpty()) {
            builder.and(policy.targets.any().in(request.getSupportTargets()));
        }

        // 심사 방법
        if (request.getEvaluationMethods() != null && !request.getEvaluationMethods().isEmpty()) {
            builder.and(policy.evaluationMethods.any().in(request.getEvaluationMethods()));
        }

        // 키워드 쿼리검색
        if (request.getQuery() != null && !request.getQuery().isBlank()) {
            builder.and(policy.title.containsIgnoreCase(request.getQuery())
                    .or(policy.summary.containsIgnoreCase(request.getQuery()))
                    .or(policy.conditionDetail.containsIgnoreCase(request.getQuery()))
                    .or(policy.targetDetail.containsIgnoreCase(request.getQuery())));
        }


        // 조회
        List<PolicyResponse> content = queryFactory
                /* QPolicyResponse 생성자 or Projections.fields() */
                .select(Projections.constructor(
                        PolicyResponse.class,
                        policy.regions,
                        policy.supportFields,
                        policy.supportCategories,
                        policy.applyEndAt,
                        policy.targets,
                        policy.evaluationMethods
                        // bookmarked
                        // policyStatus

                ))
                .from(policy)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(policy.count())
                .from(policy)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
