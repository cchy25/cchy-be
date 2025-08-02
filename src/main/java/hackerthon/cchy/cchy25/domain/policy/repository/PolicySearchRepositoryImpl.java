package hackerthon.cchy.cchy25.domain.policy.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hackerthon.cchy.cchy25.domain.bookmark.entity.QBookmark;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicySearchRequest;
import hackerthon.cchy.cchy25.domain.policy.entity.QPolicy;
import hackerthon.cchy.cchy25.domain.policy.entity.QUserPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PolicySearchRepositoryImpl implements PolicySearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PolicyResponse> search(Pageable pageable, PolicySearchRequest request) {
        return search(pageable, request, null);
    }

    @Override
    public Page<PolicyResponse> search(Pageable pageable, PolicySearchRequest request, Long userId) {
        QPolicy policy = QPolicy.policy;
        BooleanBuilder builder = createFilterBuilder(request, policy);

        // --- Accuracy Calculation --- //
        NumberExpression<Integer> score = Expressions.asNumber(0);
        int totalCategories = 0;

        if (!CollectionUtils.isEmpty(request.getRegions())) {
            totalCategories++;
            score = score.add(new CaseBuilder().when(policy.regions.any().in(request.getRegions())).then(1).otherwise(0));
        }
        if (!CollectionUtils.isEmpty(request.getSupportFields())) {
            totalCategories++;
            score = score.add(new CaseBuilder().when(policy.supportFields.any().in(request.getSupportFields())).then(1).otherwise(0));
        }
        if (!CollectionUtils.isEmpty(request.getSupportTargets())) {
            totalCategories++;
            score = score.add(new CaseBuilder().when(policy.targets.any().in(request.getSupportTargets())).then(1).otherwise(0));
        }
        if (!CollectionUtils.isEmpty(request.getSupportCategories())) {
            totalCategories++;
            score = score.add(new CaseBuilder().when(policy.supportCategories.any().in(request.getSupportCategories())).then(1).otherwise(0));
        }

        NumberExpression<Integer> accuracy = totalCategories > 0
                ? score.multiply(100).divide(totalCategories)
                : Expressions.asNumber(0);

        // --- Dynamic Select Projection --- //
        List<Expression<?>> constructorArgs = new ArrayList<>();
        constructorArgs.add(policy.title);
        constructorArgs.add(policy.summary);
        constructorArgs.add(policy.url);
        constructorArgs.add(policy.targetDetail);
        constructorArgs.add(policy.exTargetDetail);
        constructorArgs.add(policy.organization);
        constructorArgs.add(policy.startAt);
        constructorArgs.add(policy.endAt);
        constructorArgs.add(policy.applyStartAt);
        constructorArgs.add(policy.applyEndAt);
        constructorArgs.add(policy.minAmount);
        constructorArgs.add(policy.maxAmount);
        constructorArgs.add(policy.years);
        constructorArgs.add(policy.evaluationMethods);
        constructorArgs.add(policy.supportCategories);
        constructorArgs.add(policy.supportFields);
        constructorArgs.add(policy.supportTypes);
        constructorArgs.add(policy.targets);
        constructorArgs.add(policy.applyMethods);
        constructorArgs.add(accuracy.as("accuracy"));

        if (userId != null) {
            QBookmark bookmark = QBookmark.bookmark;
//            QUserPolicy userPolicy = QUserPolicy.userPolicy;
            
            BooleanExpression bookmarkedExpression = JPAExpressions.selectOne()
                    .from(bookmark)
                    .where(bookmark.policy.id.eq(policy.id).and(bookmark.user.id.eq(userId)))
                    .exists();
            constructorArgs.add(bookmarkedExpression.as("bookmarked"));

//            constructorArgs.add(JPAExpressions.select(userPolicy.status)
//                    .from(userPolicy)
//                    .where(userPolicy.policy.id.eq(policy.id).and(userPolicy.user.id.eq(userId))));
        } else {
            constructorArgs.add(Expressions.constant(false)); // bookmarked
//            constructorArgs.add(Expressions.nullExpression()); // policyStatus
        }

        JPAQuery<PolicyResponse> query = queryFactory
                .select(Projections.constructor(PolicyResponse.class, constructorArgs.toArray(new Expression[0])))
                .from(policy)
                .where(builder);

        List<PolicyResponse> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(accuracy.desc(), policy.createAt.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(policy.count())
                .from(policy)
                .where(builder);

        long total = countQuery.fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanBuilder createFilterBuilder(PolicySearchRequest request, QPolicy policy) {
        BooleanBuilder builder = new BooleanBuilder();

        if (request.getRegions() != null && !request.getRegions().isEmpty()) {
            builder.and(policy.regions.any().in(request.getRegions()));
        }
        if (request.getSupportFields() != null && !request.getSupportFields().isEmpty()) {
            builder.and(policy.supportFields.any().in(request.getSupportFields()));
        }
        if (request.getSupportTypes() != null && !request.getSupportTypes().isEmpty()) {
            builder.and(policy.supportTypes.any().in(request.getSupportTypes()));
        }
        if (request.getSupportCategories() != null && !request.getSupportCategories().isEmpty()) {
            builder.and(policy.supportCategories.any().in(request.getSupportCategories()));
        }
        if (request.getApplyEndAt() != null) {
            builder.and(policy.applyEndAt.goe(LocalDateTime.now()))
                    .and(policy.applyEndAt.loe(request.getApplyEndAt()));
        }
        if (request.getSupportTargets() != null && !request.getSupportTargets().isEmpty()) {
            builder.and(policy.targets.any().in(request.getSupportTargets()));
        }
        if (request.getEvaluationMethods() != null && !request.getEvaluationMethods().isEmpty()) {
            builder.and(policy.evaluationMethods.any().in(request.getEvaluationMethods()));
        }
        if (request.getQuery() != null && !request.getQuery().isBlank()) {
            builder.and(policy.title.containsIgnoreCase(request.getQuery())
                    .or(policy.summary.containsIgnoreCase(request.getQuery()))
                    .or(policy.conditionDetail.containsIgnoreCase(request.getQuery()))
                    .or(policy.targetDetail.containsIgnoreCase(request.getQuery())));
        }
        return builder;
    }
}
