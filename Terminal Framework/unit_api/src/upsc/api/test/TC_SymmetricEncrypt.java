package upsc.api.test;

import android.util.Log;
import upsc.framework.api.CipherText;
import upsc.framework.api.KeyLengthSymmetric;
import upsc.framework.api.KeySymmetricEncoded;
import upsc.framework.api.UPSCException;

public class TC_SymmetricEncrypt extends UPSCTestCase {
	
	private static final String LOG_TAG = "TC_SymmetricEncrypt";
	byte[] m_keyPIN = {0x20, 0x20, 0x20, 0x20};
	KeySymmetricEncoded m_Key;
	
	public void CreateSymmetricKey() throws Exception{
		m_Key = m_upsc.createSymmetricKey(
										KeyLengthSymmetric.GENERIC_192, 
										m_keyPIN
										);
		if( m_Key == null ){
			fail("Key Creation Fails");
		}else if( m_Key.getBytes() == null ){
			fail("Key Creation Fails");
		}
	}

    @Override
	public void setUp() throws Exception{
    	super.setUp();
    	CreateSymmetricKey();
	}
	
	@Override
	public void tearDown() throws Exception{
		super.tearDown();
	}

	public void testSymmetricEncryptSimple() throws Exception{
		byte[] data = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
		CipherText encryptedData = m_upsc.encrypt(m_Key, m_keyPIN, data);
		
		if( encryptedData == null ){
			fail("Sign Fails");
		}
		
		Log.i(LOG_TAG, "encryptedData : " + UTIL.toHex(encryptedData.getBytes()));
	}
	
	public void testSymmetricEncryptWithWrongPIN() throws Exception{
		byte[] data = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
		byte[] wrongPIN = {0x23, 0x23, 0x23, 0x23};
		
		try{
			CipherText encryptedData = m_upsc.encrypt(m_Key, wrongPIN, data);
			fail("Not Reached");
		}catch(UPSCException e){
			// Pass
		}
	}

}
