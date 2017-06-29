package tyxo.baseappframe.utils;

import java.util.Comparator;

/**
 * 作者: ${LY} on 2016/1/2719:21.
 * 注释: 订单查询List比较器
 */
public class BeanComparator<T extends Object> implements Comparator<T> {
    /*@Override
    public int compare(OrderQueryBean2.DataEntity lhs, OrderQueryBean2.DataEntity rhs) {
        //根据日期比较.
        int flag = rhs.getPurchaseDate().compareTo(lhs.getPurchaseDate());
        return flag;
    }*/

    @Override
    public int compare(T t, T t1) {
        return 0;
    }
}
