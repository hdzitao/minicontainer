package study_test.bean;


import lombok.Getter;
import lombok.SneakyThrows;
import study.factory.auto.Autowired;
import study.factory.auto.MiniComponent;
import study.factory.auto.Value;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by taojinhou on 2019/12/6.
 */
@MiniComponent
public class AskName extends FatherAskName {
    private ShowName name;

    @Autowired
    @Getter
    private ShowName name1;

    @Getter
    private ShowName name2;

    @Autowired
    public void setName2(ShowName name2) {
        this.name2 = name2;
    }

    @Getter
    private int askNum;

    @Value("[1,2,3,4,5]")
    private int[] intArray;

    @Value("[1,2,3,4,5]")
    private Integer[] intArray2;

    @Value("[1,2,3,4,5]")
    private List<Integer> intList;

    @Value("[1,2,3,4,5]")
    private Set<Integer> intSet;

    @Value("{'1':1, '2': 1}")
    private Map<String, Integer> map;

    @Autowired
    public AskName(ShowName name, @Value("${test2.0}") int askNum) {
        this.name = name;
        this.askNum = askNum;
    }

    public AskName() {
    }


    public ShowName getName() {
        return getName0();
    }

    @SneakyThrows
    ShowName getName0() {
        if (askNum-- == 0) {
            throw new Exception("Don't ask me again!");
        }
        return name;
    }
}
