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

    /**
     * 동적 쿼리를 사용해 정책을 검색하고, 결과를 PolicyResponse DTO의 Page 객체로 반환합니다.
     * 이 메서드는 북마크 여부를 확인하지 않습니다.
     *
     * @param pageable 페이징 및 정렬 정보를 담은 객체
     * @param request 검색 조건을 담은 DTO
     * @return 검색 조건에 맞는 PolicyResponse DTO의 Page 객체
     */
    @Override
    public Page<PolicyResponse> search(Pageable pageable, PolicySearchRequest request) {
        BooleanBuilder builder = createSearchConditions(request);

        List<Policy> policies = queryFactory
                .selectFrom(policy)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(policy.id.desc())
                .fetch();

        Long total = Optional.ofNullable(
                queryFactory.select(policy.count())
                        .from(policy)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        List<PolicyResponse> responses = policies.stream()
                .map(p -> PolicyResponse.from(p, false)) // 북마크 상태는 항상 false로 설정
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, total);
    }

    /**
     * 사용자 ID를 기반으로 정책을 검색하고, 북마크 상태를 포함하여 Page 객체로 반환합니다.
     *
     * @param pageable 페이징 및 정렬 정보를 담은 객체
     * @param request 검색 조건을 담은 DTO
     * @param userId 북마크 여부를 확인할 사용자 ID
     * @return 검색 조건에 맞는 PolicyResponse DTO의 Page 객체 (북마크 상태 포함)
     */
    @Override
    public Page<PolicyResponse> search(Pageable pageable, PolicySearchRequest request, Long userId) {
        BooleanBuilder builder = createSearchConditions(request);

        List<Policy> policies = queryFactory
                .selectFrom(policy)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(policy.id.desc())
                .fetch();

        Long total = Optional.ofNullable(
                queryFactory.select(policy.count())
                        .from(policy)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        // N+1 문제를 방지하기 위해 사용자가 북마크한 모든 정책 ID를 한 번에 가져옵니다.
        Set<Long> bookmarkedPolicyIds = bookmarkRepository.findPolicyIdsByUserId(userId);

        List<PolicyResponse> responses = policies.stream()
                .map(p -> {
                    boolean isBookmarked = bookmarkedPolicyIds.contains(p.getId());
                    return PolicyResponse.from(p, isBookmarked);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, total);
    }

    /**
     * PolicySearchRequest를 바탕으로 동적 쿼리 조건을 생성합니다.
     * (이하 생략 - 기존 로직과 동일)
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