package car;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import java.util.HashMap;	
public class BaseModel {
    //����APPID/AK/SK /*�滻Ϊ�Լ���*/  ȥ"�ٶ�������"ע��
    public static final String APP_ID = "454352324124";
    public static final String API_KEY = "fdvcddsvd42343sdddw23sdwd";
    public static final String SECRET_KEY = "4f2v89wedwv79frecssqws8080da";
	public static String path = "";   //��ʼ�����ѡһ��·��
				    
	// ��ʼ��һ��AipOcr		   
	public static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
	
	public static String licensePlate( String path) {
		  // �����ѡ�������ýӿ�		        
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("multi_detect", "true");//���ü�ֵ��
		// ����pathΪ����ͼƬ·��
		JSONObject res = client.plateLicense(path, options); 
		return(res.toString());
		}				
	}
	