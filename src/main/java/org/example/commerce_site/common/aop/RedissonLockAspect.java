package org.example.commerce_site.common.aop;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedissonLockAspect {
	private final RedissonClient redissonClient;

	@Around("@annotation(org.example.commerce_site.common.aop.RedissonLock)")
	public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		RedissonLock annotation = method.getAnnotation(RedissonLock.class);
		String lockKey = method.getName() + ":" + CustomSpringELParser.getDynamicValue(signature.getParameterNames(),
			joinPoint.getArgs(), annotation.value());
		log.info("lock key : {}", lockKey);

		RLock lock = redissonClient.getLock(lockKey);
		boolean lockable = false;

		try {
			lockable = lock.tryLock(annotation.waitTime(), annotation.leaseTime(), TimeUnit.SECONDS);
			if (!lockable) {
				log.info("Lock 획득 실패={}", lockKey);
				return false;
			}
			log.info("로직 수행");
			return joinPoint.proceed();
		} catch (InterruptedException e) {
			log.info("에러 발생");
			throw e;
		} finally {

			log.info("락 해제");
			lock.unlock();
		}
	}
}

