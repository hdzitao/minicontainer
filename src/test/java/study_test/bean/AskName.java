package study_test.bean;


import lombok.Getter;
import lombok.SneakyThrows;
import study.factory.auto.Autowired;
import study.factory.auto.MiniComponent;
import study.factory.auto.Value;

/**
 * Created by taojinhou on 2019/12/6.
 */
@MiniComponent
public class AskName {
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

    @Autowired
    public AskName(ShowName name, @Value("${test.value}") int askNum) {
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
