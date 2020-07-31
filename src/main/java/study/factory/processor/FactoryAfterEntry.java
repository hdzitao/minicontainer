package study.factory.processor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.factory.configure.BeanConfigure;

/**
 * Created by taojinhou on 2020/7/30.
 */
@AllArgsConstructor
public class FactoryAfterEntry implements Comparable<FactoryAfterEntry> {
    @Getter
    private final BeanConfigure configure;

    @Getter
    private final Class<? extends FactoryAfterProcessor> processor;

    @Getter
    private final FactoryAfterPriority priority;

    @Override
    public int compareTo(FactoryAfterEntry others) {
        return this.priority.compareTo(others.priority);
    }
}
