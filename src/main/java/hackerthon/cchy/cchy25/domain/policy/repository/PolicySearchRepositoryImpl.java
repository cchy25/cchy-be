package hackerthon.cchy.cchy25.domain.policy.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hackerthon.cchy.cchy25.domain.bookmark.repository.BookmarkRepository;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicySearchRequest;
import hackerthon.cchy.cchy25.domain.policy.entity.Policy;
import hackerthon.cchy.cchy25.domain.policy.entity.QPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PolicySearchRepositoryImpl implements PolicySearchRepository {

    private final JPAQueryFactory queryFactory;
    private final QPolicy policy = QPolicy.policy;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public Page<PolicyResponse> search(Pageable pageable, PolicySearchRequest request) {
        return calculateAndSortPolicies(pageable, request, null);
    }

    @Override
    public Page<PolicyResponse> search(Pageable pageable, PolicySearchRequest request, Long userId) {
        return calculateAndSortPolicies(pageable, request, userId);
    }

    private Page<PolicyResponse> calculateAndSortPolicies(Pageable pageable, PolicySearchRequest request, Long userId) {
        BooleanBuilder builder = createSearchConditions(request);

        List<Policy> policies = queryFactory
                .selectFrom(policy)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(
                queryFactory.select(policy.count())
                        .from(policy)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        Set<Long> bookmarkedPolicyIds = (userId != null) ? bookmarkRepository.findPolicyIdsByUserId(userId) : null;

        List<PolicyResponse> responses = policies.stream()
                .map(p -> {
                    Integer accuracy = calculateAccuracy(p, request);
                    boolean isBookmarked = (bookmarkedPolicyIds != null) && bookmarkedPolicyIds.contains(p.getId());
                    return PolicyResponse.from(p, accuracy, isBookmarked);
                })
                .sorted((a, b) -> b.getAccuracy().compareTo(a.getAccuracy()))
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, total);
    }

    /**
     * 정책과 요청을 기반으로 정확도를 계산하는 메서드 (항목별 비율의 평균)
     */
    private Integer calculateAccuracy(Policy p, PolicySearchRequest request) {
        double totalScore = 0.0;
        int totalCategories = 0;

        // regions
        if (!CollectionUtils.isEmpty(request.getRegions())) {
            totalCategories++;
            Set<Object> intersection = new HashSet<>(p.getRegions());
            intersection.retainAll(request.getRegions());
            totalScore += (double) intersection.size() / request.getRegions().size();
        }

        // supportFields
        if (!CollectionUtils.isEmpty(request.getSupportFields())) {
            totalCategories++;
            Set<Object> intersection = new HashSet<>(p.getSupportFields());
            intersection.retainAll(request.getSupportFields());
            totalScore += (double) intersection.size() / request.getSupportFields().size();
        }

        // supportTargets
        if (!CollectionUtils.isEmpty(request.getSupportTargets())) {
            totalCategories++;
            Set<Object> intersection = new HashSet<>(p.getTargets());
            intersection.retainAll(request.getSupportTargets());
            totalScore += (double) intersection.size() / request.getSupportTargets().size();
        }

        // supportCategories
        if (!CollectionUtils.isEmpty(request.getSupportCategories())) {
            totalCategories++;
            Set<Object> intersection = new HashSet<>(p.getSupportCategories());
            intersection.retainAll(request.getSupportCategories());
            totalScore += (double) intersection.size() / request.getSupportCategories().size();
        }

        if (totalCategories > 0) {
            return (int) Math.round((totalScore / totalCategories) * 100);
        }

        return 0;
    }

    /**
     * PolicySearchRequest를 바탕으로 동적 쿼리 조건을 생성합니다.
     */
    private BooleanBuilder createSearchConditions(PolicySearchRequest request) {
        BooleanBuilder builder = new BooleanBuilder();
        Optional.ofNullable(request.getApplyEndAt())
                .ifPresent(endAt -> builder.and(policy.applyEndAt.after(endAt)));
        addSetConditions(builder, request.getRegions(), policy.regions::contains);
        addSetConditions(builder, request.getSupportFields(), policy.supportFields::contains);
        addSetConditions(builder, request.getSupportCategories(), policy.supportCategories::contains);
        addSetConditions(builder, request.getSupportTargets(), policy.targets::contains);
        addSetConditions(builder, request.getSupportTypes(), policy.supportTypes::contains);
        addSetConditions(builder, request.getEvaluationMethods(), policy.evaluationMethods::contains);
        addBooleanCondition(builder, request.getHasItem(), policy.hasItem::eq);
        addBooleanCondition(builder, request.getHasTeam(), policy.hasTeam::eq);
        addBooleanCondition(builder, request.getHasMentor(), policy.hasMentor::eq);
        addBooleanCondition(builder, request.getHasModel(), policy.hasModel::eq);
        addBooleanCondition(builder, request.getHasCapital(), policy.hasCapital::eq);
        addBooleanCondition(builder, request.getHasSpace(), policy.hasSpace::eq);
        addBooleanCondition(builder, request.getIsRegistered(), policy.isRegistered::eq);
        addBooleanCondition(builder, request.getHasEdu(), policy.hasEdu::eq);
        addBooleanCondition(builder, request.getHasPlanner(), policy.hasPlanner::eq);
        Optional.ofNullable(request.getQuery())
                .filter(q -> !q.isBlank())
                .ifPresent(q -> builder.and(policy.title.containsIgnoreCase(q)
                        .or(policy.summary.containsIgnoreCase(q))
                        .or(policy.organization.containsIgnoreCase(q))));
        return builder;
    }

    /**
     * Set 타입 필드에 대한 동적 쿼리 조건을 추가합니다.
     */
    private <T> void addSetConditions(BooleanBuilder builder, Set<T> values, Function<T, BooleanExpression> expressionGenerator) {
        if (values != null && !values.isEmpty()) {
            BooleanExpression setExpression = values.stream()
                    .map(expressionGenerator)
                    .reduce(BooleanExpression::or)
                    .orElse(null);
            if (setExpression != null) {
                builder.and(setExpression);
            }
        }
    }

    /**
     * Boolean 타입 필드에 대한 동적 쿼리 조건을 추가합니다.
     */
    private void addBooleanCondition(BooleanBuilder builder, Boolean value, Function<Boolean, BooleanExpression> expressionGenerator) {
        Optional.ofNullable(value)
                .ifPresent(v -> builder.and(expressionGenerator.apply(v)));
    }
}