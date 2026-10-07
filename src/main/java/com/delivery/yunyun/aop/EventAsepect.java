package com.delivery.yunyun.aop;

import com.delivery.yunyun.dto.request.event.JoinEventRequest;
import com.delivery.yunyun.error.CustomException;
import com.delivery.yunyun.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

@Aspect
@RequiredArgsConstructor
public class EventAsepect {
    private final RedissonClient redissonClient;

    @Around("@Annotation(EventAop)")
    public Object eventAspect(ProceedingJoinPoint jointPoint){
        Object[] orgs = jointPoint.getArgs();
        JoinEventRequest request = (JoinEventRequest) orgs[0];
        String eventId = String.valueOf(request.eventId());
        String key = "event:"+eventId;

        Object result;
        RLock rLock = redissonClient.getLock(key);
        try{
            Boolean lockable = rLock.tryLock(
                    10,
                    20,
                    TimeUnit.SECONDS
            );
            if(!lockable){
                throw new CustomException(ErrorCode.EVENT_LOCK_FAILED);
            }
            result = jointPoint.proceed();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            if(rLock.isHeldByCurrentThread()){
                rLock.unlock();
            }
        }

        return result;
    }
}
