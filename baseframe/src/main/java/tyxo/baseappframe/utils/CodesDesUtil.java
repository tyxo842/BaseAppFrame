package tyxo.baseappframe.utils;

/**
 * 该类用于approveState、state,代表数字与描述互转
 * <p>
 * Created on 2016/1/19.
 */
public class CodesDesUtil {

    /**
     * 用于approveState的数字描述转换成文字描述
     *
     * @param code
     * @return
     */
    public static String stateCode2Des(int code) {
        String des = null;
        switch (code) {
            case -1:
                des = "--全部--";
                break;
            case 10:
                des = "启用";
                break;
            case 0:
                des = "禁用";
                break;
            default:
                break;
        }
        return des;
    }

    /**
     * 审核状态转化成描述
     *
     * @param code
     * @return
     */
    public static String approveStateCode2Des(int code) {
        /*全部=-1，
未送审=10，
已送审=20，
再次送审=30，
审核未通过=40，
审核通过=50，*/
        String des = null;
        switch (code) {
            case -1:
                des = "--全部--";
                break;
            case 10:
                des = "未送审";
                break;
            case 20:
                des = "已送审";
                break;
            case 30:
                des = "再次送审";
                break;
            case 40:
                des = "审核未通过";
                break;
            case 50:
                des = "审核通过";
                break;
            default:
                break;
        }
        return des;
    }
}
