package car;
	import com.baidu.aip.ocr.AipOcr;
	import org.json.JSONObject;

	import java.util.HashMap;
		/**
		 * ͼ��ʶ��sdk
		 */
	public class BaseModel001 {
		    //����APPID/AK/SK  �滻Ϊ�Լ��� ȥ"�ٶ�������"ע��
		    public static final String APP_ID = "454352324124";
		    public static final String API_KEY = "fdvcddsvd42343sdddw23sdwd";
		    public static final String SECRET_KEY = "4f2v89wedwv79frecssqws8080da";
		    public static String path = "D:\\6.jpg";   //��ʼ�����ѡһ��·��
		    // ��ʼ��һ��AipOcr
		    public static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);


		    public static void main(String[] args) {
		        client.setConnectionTimeoutInMillis(2000);//�������ӵĳ�ʱʱ�䣨���룩
		        client.setSocketTimeoutInMillis(60000);//ͨ���򿪵����Ӵ������ݵĳ�ʱʱ�䣨���룩
		        // ��ѡ������log4j��־�����ʽ���������ã���ʹ��Ĭ������
		        // Ҳ����ֱ��ͨ��jvm�����������ô˻�������
//		        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

		        System.out.println("���֤***********************************");
		        idCard("car//1.jpg");

		        System.out.println("����***********************************");
		        licensePlate("car/2.png");

		    }



		    public static String idCard(String path) {
		        // �����ѡ�������ýӿ�
		        HashMap<String, String> options = new HashMap<String, String>();
		        options.put("detect_direction", "true");
		        options.put("detect_risk", "false");

		        String idCardSide = "back";

		        // ����Ϊ����ͼƬ·��
//		        String image = "";
		        JSONObject res = client.idcard(path, idCardSide, options);
		        System.out.println(res.toString(2));
		        return res.toString(2);
		    }



		    public static String licensePlate( String path) {
		        // �����ѡ�������ýӿ�
		        HashMap<String, String> options = new HashMap<String, String>();
		        options.put("multi_detect", "true");//�Ƿ�����ų��ƣ�Ĭ��Ϊfalse������Ϊtrue��ʱ����Զ�һ��ͼƬ�ڵĶ��ų��ƽ���ʶ��

		        // ����Ϊ����ͼƬ·��
		        JSONObject res = client.plateLicense(path, options);
		        System.out.println(res.toString(2));
		        return res.toString(2);

		    }



		}