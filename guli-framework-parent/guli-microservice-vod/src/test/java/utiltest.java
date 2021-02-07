import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-30 20:54
 */
public class utiltest {
    @Test
    public void test(){
        String name = "视频文件.mp4";
        String substring = name.substring(0, name.lastIndexOf("."));
        System.out.println(substring);
    }

    @Test
    public void test2(){
        List list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.forEach(System.out::println);

        String join = StringUtils.join(list, ",");
        System.out.println(join);
    }
}
