package com.ticho.core.mvc.util.valid;

import com.ticho.core.mvc.exception.ServiceException;
import com.ticho.core.mvc.view.BaseResultCode;
import org.hibernate.validator.BaseHibernateValidatorConfiguration;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Validator 参数校验
 *
 * @author AdoroTutto
 * @date 2021-10-30 22:14
 */
public class ValidUtils {


    private static final Logger log = LoggerFactory.getLogger(ValidUtils.class);

    public static final Validator VALIDATOR_DEFAULT;

    private static final Validator VALIDATOR_FAIL_FAST;

    static {
        // @formatter:off
       VALIDATOR_DEFAULT = Validation.buildDefaultValidatorFactory().getValidator();
       VALIDATOR_FAIL_FAST = Validation
           .byProvider(HibernateValidator.class)
           .configure()
           .failFast(true)
           .buildValidatorFactory()
           .getValidator();
       // @formatter:on
    }

    private ValidUtils() {
    }

    /**
     * valid 参数校验
     *
     * @see Default 默认校验默认分组注解，比如@NotNull 没有写group分组，实际用的默认分组
     * @param obj 校验对象
     */
    public static void valid(Object obj) {
        Class<?>[] groups = getGroups(true);
        Set<ConstraintViolation<Object>> validate = VALIDATOR_DEFAULT.validate(preCheck(obj), groups);
        throwValidException(validate);
    }

    /**
     * valid 参数校验
     *
     * @see Default 默认校验默认分组注解，比如@NotNull 没有写group分组，实际用的默认分组
     * @param obj 校验对象
     * @param groups 校验分组
     */
    public static void valid(Object obj, Class<?>... groups) {
        Class<?>[] groupsNew = getGroups(true, groups);
        Set<ConstraintViolation<Object>> validate = VALIDATOR_DEFAULT.validate(preCheck(obj), groupsNew);
        throwValidException(validate);
    }

    /**
     * valid 参数校验
     *
     * @see Default 默认校验默认分组注解，比如@NotNull 没有写group分组，实际用的默认分组
     * @see BaseHibernateValidatorConfiguration#failFast(boolean) 默认快速校验,遇到第一错误就报异常
     * @param obj 校验对象
     */
    public static void validFast(Object obj) {
        Class<?>[] groups = getGroups(true);
        Set<ConstraintViolation<Object>> validate = VALIDATOR_FAIL_FAST.validate(preCheck(obj), groups);
        throwValidException(validate);
    }

    /**
     * valid 参数校验
     *
     * @see Default 默认校验默认分组注解，比如@NotNull 没有写group分组，实际用的默认分组
     * @see BaseHibernateValidatorConfiguration#failFast(boolean) 默认快速校验,遇到第一错误就报异常
     * @param obj 校验对象
     * @param groups 校验分组
     */
    public static void validFast(Object obj, Class<?>... groups) {
        Class<?>[] groupsNew = getGroups(true, groups);
        Set<ConstraintViolation<Object>> validate = VALIDATOR_FAIL_FAST.validate(preCheck(obj), groupsNew);
        throwValidException(validate);
    }

    /**
     * valid 参数校验
     *
     * @param obj 校验对象
     * @param failFast 是否快速检查 检验到错误就返回，而不是检验所有错误
     * @param checkDefaultGroup 是否检验默认分组
     * @param groups 校验分组
     */
    public static <T> void valid(T obj, boolean failFast, boolean checkDefaultGroup, Class<?>... groups) {
        Validator validtor = failFast ? VALIDATOR_FAIL_FAST : VALIDATOR_DEFAULT;
        Class<?>[] groupsNew = getGroups(checkDefaultGroup, groups);
        Set<ConstraintViolation<T>> validate = validtor.validate(preCheck(obj), groupsNew);
        throwValidException(validate);
    }

    // @formatter:off

    /**
     * valid 参数校验
     *
     * @param obj 校验对象
     * @param failFast 是否快速检查 检验到错误就返回，而不是检验所有错误
     * @param checkDefaultGroup 是否检验默认分组
     * @param groups 校验分组
     */
    public static <T> Set<ConstraintViolation<T>> validReturn(T obj, boolean failFast, boolean checkDefaultGroup, Class<?>... groups) {
        Validator validtor = failFast ? VALIDATOR_FAIL_FAST : VALIDATOR_DEFAULT;
        Class<?>[] groupsNew = getGroups(checkDefaultGroup, groups);
        return validtor.validate(obj, groupsNew);
    }

    // @formatter:on

    /**
     * 抛出ConstraintViolation的异常信息
     * <p>1.异常信息为空则什么都不操作</p>
     * <p>2.异常信息会按照message排序</p>
     *
     * @param validate 校验异常信息列表
     */
    private static <T> void throwValidException(Set<ConstraintViolation<T>> validate) {
        // @formatter:off
        int size;
        if (validate == null || (size = validate.size()) == 0) {
            return;
        }
        if (size == 1) {
            Iterator<ConstraintViolation<T>> violation = validate.iterator();
            ConstraintViolation<T> next = violation.next();
            String message = next.getMessage();
            String propertyPath = next.getPropertyPath().toString();
            log.warn("参数校验异常，property=【{}】,message=【{}】", propertyPath, message);
            throw new ServiceException(BaseResultCode.PARAM_ERROR, message);
        }
        ConstraintViolation<T> violation = validate
            .stream()
            .sorted(Comparator.comparing(ConstraintViolation::getMessage))
            .peek(next -> {
                String message = next.getMessage();
                String propertyPath = next.getPropertyPath().toString();
                log.warn("参数校验异常，property=【{}】,message=【{}】", propertyPath, message);
            }).findFirst().get();
        throw new ServiceException(BaseResultCode.PARAM_ERROR, violation.getMessage());
        // @formatter:on
    }

    /**
     * 获取校验分组, 是否添加默认分组
     *
     * <p>注意:</p>
     * <p>1.groups什么都不传也是空数组</p>
     * <p>2.返回空数组没有问题的，Validator也会校验Default分组</p>
     *
     * @param isCheckDefaultGroup 是否检查默认分组
     * @param groups 分组
     * @return 分组
     */
    private static Class<?>[] getGroups(boolean isCheckDefaultGroup, Class<?>... groups) {
        if (isCheckDefaultGroup) {
            int length = groups.length;
            groups = Arrays.copyOf(groups, length + 1);
            groups[length] = Default.class;
        } else {
            if (groups.length == 0) {
                throw new IllegalArgumentException("需要传入其它校验分组, 才能不校验默认分组");
            }
        }
        return groups;
    }

    /**
     * 预校验
     * <p>1.如果是非集合对象，判断是否为null</p>
     * <p>2.如果是集合对象，则注入ValidBean对象中进行校验</p>
     *
     * @see ValidBean
     * @param obj T
     * @return T
     */
    @SuppressWarnings("all")
    private static <T> T preCheck(T obj) {
        if (Objects.isNull(obj)) {
            throw new ServiceException(BaseResultCode.PARAM_ERROR, "数据不能为空");
        }
        if (obj instanceof Collection<?>) {
            Collection<?> objnew = (Collection<?>) obj;
            if (objnew.isEmpty()) {
                throw new ServiceException(BaseResultCode.PARAM_ERROR, "数据不能为空");
            }
            ValidBean<?> validBean = new ValidBean<>(objnew);
            return (T) validBean;
        }
        return obj;
    }
}
