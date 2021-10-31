package com.ticho.core.mvc.util.valid;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

/**
 * 校验分组
 *
 * @author AdoroTutto
 * @date 2021-05-15 02:06
 */
public class ValidGroup {
    public static final List<Class<?>> ALL_GROUPS = new ArrayList<>();

    static {
        ALL_GROUPS.add(Add.class);
        ALL_GROUPS.add(Upd.class);
    }

    public interface Add {

    }

    public interface Upd {

    }

    @GroupSequence({Default.class, Add.class, Upd.class})
    public interface CheckSequence {

    }
}
